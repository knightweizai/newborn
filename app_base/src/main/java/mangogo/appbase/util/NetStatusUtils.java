package mangogo.appbase.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;


public class NetStatusUtils {

    public static final int NETWORK_STATE_NONE = 0;
    public static final int NETWORK_STATE_WIFI = 1;
    public static final int NETWORK_STATE_2G = 2; // 2G
    public static final int NETWORK_STATE_3G = 3; // 3G
    public static final int NETWORK_STATE_4G = 4; // 4G
    public static final int NETWORK_STATE_MOBILE = 5;


    public static final int UNKNOWN_OPERATOR = 0; //未知的运营商
    public static final int CHINA_MOBILE = 1;     //中国移动
    public static final int CHINA_TELECOM = 2;    //中国电信
    public static final int CHINA_UNICOM = 3;     //中国联通,
    public static final int OTHER_OPERATOR = 99;  //其他运营商

    /**
     * 0：无网络
     * 1：wifi
     * 2：移动网络
     */
    public static int getNetworkState(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return NETWORK_STATE_NONE;
        }

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return NETWORK_STATE_NONE;
        }

        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return NETWORK_STATE_WIFI;
        }

        if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return NETWORK_STATE_MOBILE;
        }
        return NETWORK_STATE_NONE;
    }

    public static int getApiNetworkState(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return NETWORK_STATE_NONE;
        }

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return NETWORK_STATE_NONE;
        }

        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return 5;
        }

        if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                int networkType = telephonyManager.getNetworkType();
                switch (networkType) {
                    // 2G网络
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return NETWORK_STATE_2G;
                    // 3G网络
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        return NETWORK_STATE_3G;
                    // 4G网络
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return NETWORK_STATE_4G;
                    default:
                        return NETWORK_STATE_4G;
                }
            } catch (Exception e) {
                return NETWORK_STATE_4G;
            }
        }
        return NETWORK_STATE_NONE;
    }

    public static int getNetworkStateDetail(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return NETWORK_STATE_NONE;
        }

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return NETWORK_STATE_NONE;
        }

        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return NETWORK_STATE_WIFI;
        }

        if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                int networkType = telephonyManager.getNetworkType();
                switch (networkType) {
                    // 2G网络
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return NETWORK_STATE_2G;
                    // 3G网络
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        return NETWORK_STATE_3G;
                    // 4G网络
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return NETWORK_STATE_4G;
                    default:
                        return NETWORK_STATE_4G;
                }
            } catch (Exception e) {
                return NETWORK_STATE_4G;
            }
        }
        return NETWORK_STATE_NONE;
    }

    public static boolean isConnected(Context context) {
        try {
            return getNetworkState(context) != NETWORK_STATE_NONE;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean isWifi(Context context) {
        return getNetworkState(context) == NETWORK_STATE_WIFI;
    }

    public static boolean isMobile(Context context) {
        return getNetworkState(context) == NETWORK_STATE_MOBILE;
    }

    //判断指定wifi名是否存在
    public static boolean isWifiExsits(String ssid, Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> list = wifiManager.getScanResults();
        if (list == null) {
            return false;
        }
        for (ScanResult scanResult : list) {
            if (scanResult.SSID.equals(ssid)) {//存在
                return true;
            }
        }
        return false;
    }

//    public static String getWifiMacAddress(Context context) {
//        WifiManager wifiManager = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        if (wifiManager != null) {
//            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//            if (wifiInfo != null) {
//                return wifiInfo.getMacAddress();
//            }
//        }
//        return "";
//    }

    public static String getWifiIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
            if (dhcpInfo != null) {
                return Formatter.formatIpAddress(dhcpInfo.ipAddress);
            }
        }
        return "";
    }

    public static String getWifiMacAddress(Context context) {
        String mac = getWifiMacAddress21(context);
        if (TextUtils.isEmpty(mac) || mac.equals("02:00:00:00:00:00")) {
            return getWifiMacAddress22();
        }
        return "";
    }

    private static String getWifiMacAddress21(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
            if (wifiManager != null) {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                if (wifiInfo == null) {
                    return wifiInfo.getMacAddress();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getWifiMacAddress22() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder builder = new StringBuilder();
                for (byte b : macBytes) {
                    builder.append(String.format("%02X:", b));
                }

                if (builder.length() > 0) {
                    builder.deleteCharAt(builder.length() - 1);
                }
                return builder.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取本机IPv4地址
     *
     * @param context
     * @return 本机IPv4地址；null：无网络连接
     */
    public static String getIpAddress(Context context) {
        // 获取WiFi服务
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        // 判断WiFi是否开启
        if (wifiManager.isWifiEnabled()) {
            // 已经开启了WiFi
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            String ip = intToIp(ipAddress);
            return ip;
        } else {
            // 未开启WiFi
            return getIpAddress();
        }
    }

    private static String intToIp(int ipAddress) {
        return (ipAddress & 0xFF) + "." +
                ((ipAddress >> 8) & 0xFF) + "." +
                ((ipAddress >> 16) & 0xFF) + "." +
                (ipAddress >> 24 & 0xFF);
    }

    /**
     * 获取本机IPv4地址
     *
     * @return 本机IPv4地址；null：无网络连接
     */
    public static String getIpAddress() {
        try {
            NetworkInterface networkInterface;
            InetAddress inetAddress;
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                networkInterface = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = networkInterface.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
            return null;
        } catch (SocketException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 获取运营商类型
     */
    public static int getOperatorType(Context context) {
        if (PermissionUtils.checkReadPhoneStatePermission()) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String IMSI = telephonyManager.getSubscriberId();
            if (IMSI != null) {
                if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
                    return CHINA_MOBILE;
                } else if (IMSI.startsWith("46001") || IMSI.startsWith("46006")) {
                    return CHINA_UNICOM;
                } else if (IMSI.startsWith("46003")) {
                    return CHINA_TELECOM;
                }
                return OTHER_OPERATOR;
            } else {
                return UNKNOWN_OPERATOR;
            }
        }
        return UNKNOWN_OPERATOR;
    }
}
