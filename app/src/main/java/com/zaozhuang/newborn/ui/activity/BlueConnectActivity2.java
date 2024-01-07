package com.zaozhuang.newborn.ui.activity;

import static mangogo.appbase.util.AppUtils.getAppVersion;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.inuker.bluetooth.library.Code;
import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.inuker.bluetooth.library.utils.BluetoothUtils;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.veepoo.protocol.VPOperateManager;
import com.veepoo.protocol.listener.base.IABleConnectStatusListener;
import com.veepoo.protocol.listener.base.IABluetoothStateListener;
import com.veepoo.protocol.listener.base.IConnectResponse;
import com.veepoo.protocol.listener.base.INotifyResponse;
import com.veepoo.protocol.util.VPLogger;
import com.zaozhuang.newborn.GlobalApplication;
import com.zaozhuang.newborn.R;
import com.zaozhuang.newborn.adapter.BleScanViewAdapter;
import com.zaozhuang.newborn.adapter.OnRecycleViewClickCallback;
import com.zaozhuang.newborn.bluetooth.DeviceCompare;
import com.zaozhuang.newborn.dialog.CommonDialog;
import com.zaozhuang.newborn.ui.base.BaseActivity;
import com.zaozhuang.newborn.view.MyLayout;

import java.util.ArrayList;
import java.util.List;

import mangogo.appbase.util.ToastUtil;

public class BlueConnectActivity2 extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, OnRecycleViewClickCallback {



    View mBackView;
    TextView mTitleView;
    TextView mRightText;
    private TextView tv_connecting;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    private final int REQUEST_CODE = 1;
    List<SearchResult> mListData = new ArrayList<>();
    List<String> mListAddress = new ArrayList<>();
    Handler mHandler = new Handler();
    private BluetoothManager mBManager;
    private BluetoothAdapter mBAdapter;
    private BluetoothLeScanner mBScanner;
    final static int MY_PERMISSIONS_REQUEST_BLUETOOTH = 0x55;

    private boolean mIsOadModel;
//    BluetoothLeScannerCompat mScanner;
    private RecyclerView mRecyclerView;
    private BleScanViewAdapter bleConnectAdatpter;

    VPOperateManager mVpoperateManager;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_blue_connect2;
    }

    @Override
    protected void initView() {

        mBackView = findViewById(R.id.common_title_bar_back_image);
        mTitleView = findViewById(R.id.common_title_bar_title_text);
        mRightText = findViewById(R.id.mine_text);
//        tv_connecting = findViewById(R.id.tv_connecting);


        mTitleView.setText("手环连接");
        mRightText.setVisibility(View.VISIBLE);
        mRightText.setText("扫描");



        mVpoperateManager = VPOperateManager.getMangerInstance(GlobalApplication.getGlobalContext());
//        VPOperateManager.getInstance().setAutoConnectBTBySdk(false);
        VPLogger.setDebug(true);
        initRecyleView();
        checkPermission();
        registerBluetoothStateListener();

    }


    private void initRecyleView() {
        mSwipeRefreshLayout = findViewById(R.id.mian_swipeRefreshLayout);
        mRecyclerView = findViewById(R.id.main_recylerlist);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bleConnectAdatpter = new BleScanViewAdapter(this, mListData);
        mRecyclerView.setAdapter(bleConnectAdatpter);
        bleConnectAdatpter.setBleItemOnclick(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);

    }

    private void checkPermission() {
        Log.i(TAG,"Build.VERSION.SDK_INT =" + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT <= 22) {
            initBLE();
            return;
        }

        int permissionCheck = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG,"checkPermission,PERMISSION_GRANTED");
            initBLE();
        } else if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            requestPermission();
            Log.i(TAG,"checkPermission,PERMISSION_DENIED");
        }
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(BlueConnectActivity2.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Log.i(TAG,"requestPermission,shouldShowRequestPermissionRationale");
                //
                CommonDialog.showDialog("提示", "手环连接，需要打开蓝牙权限", "确定", new OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        ActivityCompat.requestPermissions(BlueConnectActivity2.this,
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                        /*Manifest.permission.BLUETOOTH_SCAN,Manifest.permission.BLUETOOTH_CONNECT,*/Manifest.permission.BLUETOOTH_PRIVILEGED},
                                MY_PERMISSIONS_REQUEST_BLUETOOTH);
                    }
                });

            } else {
                Log.i(TAG,"requestPermission,shouldShowRequestPermissionRationale else");
                ActivityCompat.requestPermissions(BlueConnectActivity2.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                /*Manifest.permission.BLUETOOTH_SCAN,Manifest.permission.BLUETOOTH_CONNECT,*/Manifest.permission.BLUETOOTH_PRIVILEGED},
                        MY_PERMISSIONS_REQUEST_BLUETOOTH);
            }
        } else {
            Log.i(TAG,"requestPermission,shouldShowRequestPermissionRationale hehe");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_BLUETOOTH: {
                Log.i(TAG, "onRequestPermissionsResult,MY_PERMISSIONS_REQUEST_BLUETOOTH ");
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initBLE();
                } else {
                }
                return;
            }
        }
    }

    private boolean scanDevice() {
        if (!mListAddress.isEmpty()) {
            mListAddress.clear();
        }
        if (!mListData.isEmpty()) {
            mListData.clear();
            bleConnectAdatpter.notifyDataSetChanged();
        }

        if (!BluetoothUtils.isBluetoothEnabled()) {
            Toast.makeText(mContext, "蓝牙没有开启", Toast.LENGTH_SHORT).show();
            return true;
        }
//        startScan();
        mVpoperateManager.startScanDevice(mSearchResponse);
        return false;
    }


    /**
     * 蓝牙打开or关闭状态
     */
    private void registerBluetoothStateListener() {
        mVpoperateManager.registerBluetoothStateListener(mBluetoothStateListener);

    }

    /**
     * 监听系统蓝牙的打开和关闭的回调状态
     */
    private final IABleConnectStatusListener mBleConnectStatusListener = new IABleConnectStatusListener() {

        @Override
        public void onConnectStatusChanged(String mac, int status) {
            if (status == Constants.STATUS_CONNECTED) {
                Log.i(TAG,"STATUS_CONNECTED");
            } else if (status == Constants.STATUS_DISCONNECTED) {
                Log.i(TAG,"STATUS_DISCONNECTED");
            }
        }
    };

    /**
     * 监听蓝牙与设备间的回调状态
     */
    private final IABluetoothStateListener mBluetoothStateListener = new IABluetoothStateListener() {
        @Override
        public void onBluetoothStateChanged(boolean openOrClosed) {
            Log.i(TAG,"open=" + openOrClosed);
        }
    };


    /**
     * 扫描的回调
     */
    private final SearchResponse mSearchResponse = new SearchResponse() {
        @Override
        public void onSearchStarted() {
            Log.i(TAG,"onSearchStarted");
        }

        @Override
        public void onDeviceFounded(final SearchResult device) {
            Log.i(TAG,String.format("device for %s-%s-%d", device.getName(), device.getAddress(), device.rssi));

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!mListAddress.contains(device.getAddress()) /*&& isShowDevice(device.scanRecord)*/) {
                        mListData.add(device);
                        mListAddress.add(device.getAddress());
                    }
                    mListData.sort(new DeviceCompare());
                    bleConnectAdatpter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public void onSearchStopped() {
            refreshStop();
            Log.i(TAG,"onSearchStopped");
        }

        @Override
        public void onSearchCanceled() {
            refreshStop();
            Log.i(TAG,"onSearchCanceled");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (BluetoothUtils.isBluetoothEnabled()) {
                scanDevice();
            } else {
                refreshStop();
            }
        }
    }

    @Override
    public void onRefresh() {
        if (checkBLE()) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG,"onRefresh");
                    scanDevice();
                }
            }, 3000);
        }
    }

    private void initBLE() {
        mBManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        if (null != mBManager) {
            mBAdapter = mBManager.getAdapter();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBScanner = mBAdapter.getBluetoothLeScanner();
        }
        checkBLE();
    }

    /**
     * 检测蓝牙设备是否开启
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    private boolean checkBLE() {
        if (!BluetoothUtils.isBluetoothEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_CODE);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 结束刷新
     */
    void refreshStop() {
        Log.i(TAG,"refreshComlete");
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void OnRecycleViewClick(int position) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showMessage("正在连接，请稍等...");
//                Toast.makeText(mContext, "正在连接，请稍等...", Toast.LENGTH_SHORT).show();
            }
        });
        SearchResult searchResult = mListData.get(position);
        connectDevice(searchResult.getAddress(), searchResult.getName());
    }


    private void connectDevice(final String mac, final String deviceName) {

        mVpoperateManager.registerConnectStatusListener(mac, mBleConnectStatusListener);

        mVpoperateManager.connectDevice(mac, deviceName, new IConnectResponse() {

            @Override
            public void connectState(int code, BleGattProfile profile, boolean isoadModel) {
                if (code == Code.REQUEST_SUCCESS) {
                    //蓝牙与设备的连接状态
                    Log.i(TAG,"连接成功");
                    Log.i(TAG,"是否是固件升级模式=" + isoadModel);
                    mIsOadModel = isoadModel;
                } else {
                    Log.i(TAG,"连接失败");
                }
            }
        }, new INotifyResponse() {
            @Override
            public void notifyState(int state) {
                if (state == Code.REQUEST_SUCCESS) {
                    //蓝牙与设备的连接状态
                    Log.i(TAG,"监听成功-可进行其他操作");
                    ToastUtil.showMessage("连接成功");
//                    Intent intent = new Intent(mContext, OperaterActivity.class);
//                    intent.putExtra("isoadmodel", mIsOadModel);
//                    intent.putExtra("deviceaddress", mac);
//                    startActivity(intent);



//                    VPOperateManager.getInstance().confirmDevicePwd(new IBleWriteResponse() {
//                        @Override
//                        public void onResponse(int code) {
//
//                        }
//                    }, new IPwdDataListener() {
//                        @Override
//                        public void onPwdDataChange(PwdData pwdData) {
//                            String message = "PwdData:\n" + pwdData.toString();
//                            Log.i(TAG,message);
//                            int deviceNumber = pwdData.getDeviceNumber();
//                            String deviceVersion = pwdData.getDeviceVersion();
//                            String deviceTestVersion = pwdData.getDeviceTestVersion();
//                            Logger.t(TAG).e("设备号：" + deviceNumber + ",版本号：" + deviceVersion + ",\n测试版本号：" + deviceTestVersion);
//                        }
//                    }, new IDeviceFuctionDataListener() {
//                        @Override
//                        public void onFunctionSupportDataChange(FunctionDeviceSupportData functionSupport) {
//                            String message = "FunctionDeviceSupportData:\n" + functionSupport.toString();
//                            Log.i(TAG,message);
//                        }
//                    }, new ISocialMsgDataListener() {
//                        @Override
//                        public void onSocialMsgSupportDataChange(FunctionSocailMsgData socailMsgData) {
//
//                        }
//
//                        @Override
//                        public void onSocialMsgSupportDataChange2(FunctionSocailMsgData socailMsgData) {
//
//                        }
//                    }, new ICustomSettingDataListener() {
//                        @Override
//                        public void OnSettingDataChange(CustomSettingData customSettingData) {
//                            String message = "CustomSettingData:\n" + customSettingData.toString();
//                            Log.i(TAG,message);
//                        }
//                    }, "0000", true);

                } else {
                    Log.i(TAG,"监听失败，重新连接");
                }
            }
        });
    }


    /**
     * 1. 打开蓝牙
     * 2. 开始扫描
     * 3. 扫描成功之后的操作
     *
     */


    @Override
    protected void initListener() {
        mBackView.setOnClickListener(v -> finish());
        mRightText.setOnClickListener(v -> {
            scanDevice();
        });


//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG,"btn1 onclick ////");
//            }
//        });




//        tv_connecting.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.i(TAG,"ontouch  "+event.getAction());
//                if(event.getAction() == MotionEvent.ACTION_DOWN){
//                    return true;
//                }
//                return false;
//            }
//        });
    }

}
