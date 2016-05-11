package com.zl.app.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_settings);
        setBtnLeft1Enable(true);
        setTitle("系统设置");

        usingFeedback = (LinearLayout)findViewById(R.id.using_feedback);
        functionOutline = (LinearLayout)findViewById(R.id.function_outline);
        aboutUs = (LinearLayout)findViewById(R.id.about_us);
        serviceProtocal = (LinearLayout)findViewById(R.id.service_protocal);
        resetPassword = (LinearLayout)findViewById(R.id.reset_password);
        gradeMark = (LinearLayout)findViewById(R.id.grade_mark);
        logout = (Button)findViewById(R.id.log_out);

        usingFeedback.setOnClickListener(this);
        functionOutline.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
        serviceProtocal.setOnClickListener(this);
        resetPassword.setOnClickListener(this);
        gradeMark.setOnClickListener(this);
        logout.setOnClickListener(this);
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

    /**
     * Send a broadcast to logout.
     */
    private void logout() {
        Intent intent = new Intent("com.zl.app.LOGOUT_BROADCAST");
        sendBroadcast(intent);
    }

}
