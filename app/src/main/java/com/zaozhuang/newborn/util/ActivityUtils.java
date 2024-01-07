package com.zaozhuang.newborn.util;

import android.app.Activity;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Stack;


public class ActivityUtils {

    private static Stack<Activity> mActivityStack;
    private static int chatRoomIndex = -1;
    private static int chatTeamRoomIndex = -1;

    /**
     * 添加一个Activity到堆栈中
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        if (null == mActivityStack) {
            mActivityStack = new Stack<>();
        }
        String name = activity.getClass().getName();
        MyLogs.d("ActivityUtils", "addActivity name:" + name);

        if (TextUtils.equals(name, "com.ctb.opencar.ui.activity.chatroom.ChatRoomActivity")) {
            chatRoom();
            mActivityStack.add(activity);
            chatRoomIndex = mActivityStack.size() - 1;
        } else if (TextUtils.equals(name, "com.ctb.opencar.ui.activity.chatroom.TeamFleetRoomActivity")) {
            chatTeamRoom();
            mActivityStack.add(activity);
            chatRoomIndex = mActivityStack.size() - 1;
        } else {
            mActivityStack.add(activity);
        }
        MyLogs.d("ActivityUtils", "12121 chatRoomIndex:" + chatRoomIndex);
    }

    /**
     * 从堆栈中移除指定的Activity
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        if (activity != null) {
            if (TextUtils.equals(activity.getClass().getName(), "com.ctb.opencar.ui.activity.chatroom.ChatRoomActivity")) {
                chatRoomIndex = -1;
            } else if (TextUtils.equals(activity.getClass().getName(), "com.ctb.opencar.ui.activity.chatroom.TeamFleetRoomActivity")) {
                chatRoomIndex = -1;
            }
            String name = activity.getClass().getName();
            MyLogs.d("ActivityUtils", "removeActivity name:" + name);
            mActivityStack.remove(activity);
        }
    }

    public static void chatRoom() {
        MyLogs.d("ActivityUtils", "chatRoom chatRoomIndex:" + chatRoomIndex);
        if (chatRoomIndex > 0 && chatRoomIndex < mActivityStack.size()) {
            mActivityStack.get(chatRoomIndex).finish();
            MyLogs.d("ActivityUtils", "删除成功 :" + mActivityStack.toString());
        }
    }

    public static void chatTeamRoom() {
        MyLogs.d("ActivityUtils", "chatTeamRoomIndex chatTeamRoomIndex:" + chatTeamRoomIndex);
        if (chatTeamRoomIndex > 0 && chatTeamRoomIndex < mActivityStack.size()) {
            mActivityStack.get(chatTeamRoomIndex).finish();
            MyLogs.d("ActivityUtils", "删除成功 :" + mActivityStack.toString());
        }
    }

    /**
     * 获取顶部的Activity
     *
     * @return
     */
    public static Activity getTopActivity() {
        if (mActivityStack.isEmpty()) {
            return null;
        } else {
            return mActivityStack.get(mActivityStack.size() - 1);
        }
    }

    /**
     * 结束所有的Activity，退出应用
     */
    public static void removeAllActivity() {
        if (mActivityStack != null && mActivityStack.size() > 0) {
            for (Activity activity : mActivityStack) {
                activity.finish();
            }
        }
    }

    public static void finishOtherActivities(@NonNull final Class<? extends Activity> clz) {
        if (mActivityStack != null && mActivityStack.size() > 0) {
            for (Activity activity : mActivityStack) {
                if (!activity.getClass().equals(clz)) {
                    activity.finish();
                }
            }
        }
    }

    /**
     * 将一个Fragment添加到Activity中
     *
     * @param fragmentManager fragment管理器
     * @param fragment        需要添加的fragment
     * @param frameId         布局FrameLayout的Id
     */
    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameId) {
        if (null != fragmentManager && null != fragment) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(frameId, fragment);
            transaction.commit();
        }
    }

    /**
     * 将一个Fragment添加到Activity中,并添加tag标识
     *
     * @param fragmentManager fragment管理器
     * @param fragment        需要添加的fragment
     * @param frameId         布局FrameLayout的Id
     * @param tag             fragment的唯一tag标识
     * @param addToBackStack  是否添加到栈中，可通过返回键进行切换fragment
     */
    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameId, String tag, boolean addToBackStack) {
        if (null != fragmentManager && null != fragment) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(frameId, fragment, tag);
            if (addToBackStack) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
        }
    }

    /**
     * 对Fragment进行显示隐藏的切换，减少fragment的重复创建
     *
     * @param fragmentManager fragment管理器
     * @param hideFragment    需要隐藏的Fragment
     * @param showFragment    需要显示的Fragment
     * @param frameId         布局FrameLayout的Id
     * @param tag             fragment的唯一tag标识
     */
    public static void switchFragment(FragmentManager fragmentManager, Fragment hideFragment, Fragment showFragment, int frameId, String tag) {
        if (fragmentManager != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!showFragment.isAdded()) {
                transaction.hide(hideFragment).add(frameId, showFragment, tag).commit();
            } else {
                transaction.hide(hideFragment).show(showFragment).commit();
            }
        }
    }

    /**
     * 替换Activity中的Fragment
     *
     * @param fragmentManager fragment管理器
     * @param fragment        需要替换到Activity的Fragment
     * @param frameId         布局FrameLayout的Id
     */
    public static void replaceFragmentFromActivity(FragmentManager fragmentManager, Fragment fragment, int frameId) {
        if (null != fragmentManager && null != fragment) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(frameId, fragment);
            transaction.commit();
        }
    }
}
