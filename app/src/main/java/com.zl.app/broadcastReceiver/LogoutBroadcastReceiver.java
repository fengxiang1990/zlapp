package com.zl.app.broadcastReceiver;

/**
 * Created by CQ on 2016/5/6 0006.
 */

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;

import com.zl.app.activity.user.LoginActivity_;
import com.zl.app.util.AppManager;

/**
 * 用于处理退出返回登录界面广播的broadcastReceiver.
 */
public class LogoutBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setMessage("确定要退出登录吗？");
        dialogBuilder.setCancelable(false);
        dialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AppManager.finishAll();
                Intent logoutIntent = new Intent(context, LoginActivity_.class);
                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(logoutIntent);
            }
        });
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.show();
    }
}