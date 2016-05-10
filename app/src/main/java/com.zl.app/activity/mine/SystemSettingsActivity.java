package com.zl.app.activity.mine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.broadcastReceiver.LogoutBroadcastReceiver;

/**
 * Created by CQ on 2016/5/6 0006.
 */
public class SystemSettingsActivity extends BaseActivityWithToolBar implements View.OnClickListener {

    private LinearLayout usingFeedback;
    private LinearLayout functionOutline;
    private LinearLayout aboutUs;
    private LinearLayout serviceProtocal;
    private LinearLayout resetPassword;
    private LinearLayout gradeMark;
    private Button logout;
    private IntentFilter intentFilter;
    private LogoutBroadcastReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_settings);
        setBtnLeft1Enable(true);
        setTitle(R.string.system_settings);

        usingFeedback = (LinearLayout)findViewById(R.id.using_feedback);
        functionOutline = (LinearLayout)findViewById(R.id.function_outline);
        aboutUs = (LinearLayout)findViewById(R.id.about_us);
        serviceProtocal = (LinearLayout)findViewById(R.id.service_protocal);
        resetPassword = (LinearLayout)findViewById(R.id.reset_password);
        gradeMark = (LinearLayout)findViewById(R.id.grade_mark);
        logout = (Button)findViewById(R.id.log_out);
        intentFilter = new IntentFilter();
        receiver = new LogoutBroadcastReceiver();

        usingFeedback.setOnClickListener(this);
        functionOutline.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
        serviceProtocal.setOnClickListener(this);
        resetPassword.setOnClickListener(this);
        gradeMark.setOnClickListener(this);
        logout.setOnClickListener(this);

        /**
         *register broadcast that can be received.
         */
        intentFilter.addAction("com.zl.app.LOGOUT_BROADCAST");
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.using_feedback:
                Intent using_feedback_intent = new Intent(SystemSettingsActivity.this,
                        UsingFeedbackActivity.class);
                startActivity(using_feedback_intent);
                break;
            case R.id.function_outline:
                break;
            case R.id.about_us:
                break;
            case R.id.service_protocal:
                break;
            case R.id.reset_password:
                Intent reset_password_intent = new Intent(SystemSettingsActivity.this,
                        ResetPasswordActivity.class);
                startActivity(reset_password_intent);
                break;
            case R.id.grade_mark:
                break;
            case R.id.log_out:
                logout();
                break;
            default:
                break;
        }
    }

    private void logout() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getApplicationContext());
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
                Intent intent = new Intent("com.zl.app.LOGOUT_BROADCAST");
                sendBroadcast(intent);
            }
        });
    }

}
