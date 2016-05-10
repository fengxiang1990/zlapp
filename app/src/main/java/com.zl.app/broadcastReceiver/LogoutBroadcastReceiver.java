package com.zl.app.broadcastReceiver;

/**
 * Created by CQ on 2016/5/6 0006.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zl.app.activity.user.LoginActivity;
import com.zl.app.util.AppManager;

/**
 * 用于处理退出返回登录界面广播的broadcastReceiver.
 */
public class LogoutBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AppManager.finishAll();
        Intent logoutIntent = new Intent(context, LoginActivity.class);
        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}