package com.zl.app.activity.mine;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.zl.app.R;
import com.zl.app.activity.user.LoginActivity_;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.user.UserService;
import com.zl.app.data.user.UserServiceImpl;
import com.zl.app.util.AppConfig;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

/**
 * Created by CQ on 2016/5/6 0006.
 */

public class ResetPasswordActivity extends BaseActivityWithToolBar implements View.OnClickListener{

    String tag  = "ResetPasswordActivity";

    private LinearLayout firstStep;
    private LinearLayout secondStep;
    private EditText inputNumber;
    private EditText inputCode;
    private EditText inputOldPassword;
    private EditText inputNewPassword;
    private EditText InputNewPasswordAgain;
    private Button requestCodeBtn;
    private Button nextStepBtn;
    private Button summitBtn;

    private UserService userService;
    private Handler handler;
    private int seconds;

    private static final int ONE_MINUTE = 1000;
    private static final int START_SECONDS = 59;

    private boolean isRightCode = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        setBtnLeft1Enable(true);
        setTitle("重置密码");
        userService = new UserServiceImpl();
        handler = new Handler();
        seconds = START_SECONDS;

        initView();
        initEvent();
    }

    private void initView() {
        firstStep = (LinearLayout)findViewById(R.id.first_step);
        secondStep = (LinearLayout)findViewById(R.id.second_step);
        inputNumber = (EditText)findViewById(R.id.edit_number);
        inputCode = (EditText)findViewById(R.id.edit_code);
        inputOldPassword = (EditText)findViewById(R.id.edit_old_password);
        inputNewPassword = (EditText)findViewById(R.id.edit_new_password);
        InputNewPasswordAgain = (EditText)findViewById(R.id.edit_new_password_again);
        requestCodeBtn = (Button)findViewById(R.id.request_code);
        nextStepBtn = (Button)findViewById(R.id.next_step);
        summitBtn = (Button)findViewById(R.id.btn_for_sure);
    }

    private void initEvent() {
        requestCodeBtn.setOnClickListener(this);
        nextStepBtn.setOnClickListener(this);
        summitBtn.setOnClickListener(this);

        inputCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0){
                    nextStepBtn.setBackgroundResource(R.drawable.login_btn_selector);
                }else {
                    nextStepBtn.setBackgroundResource(R.drawable.login_btn_shape_gray);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        String tel = String.valueOf(inputNumber.getText());
        String code = String.valueOf(inputCode.getText());
        switch (v.getId()){
            case R.id.request_code:
                if (TextUtils.isEmpty(tel)){
                    ToastUtil.show(getApplicationContext(), "号码不能为空！");
                    return;
                }else {
                    userService.getPasswordCode(tel, new DefaultResponseListener<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse response) {
                            ToastUtil.show(getApplicationContext(), response.getMessage());

                            requestCodeBtn.setClickable(false);
                            handler.postDelayed(new BackCountTimerRunnable(), ONE_MINUTE);
                        }

                        @Override
                        public void onError(VolleyError error) {
                            ToastUtil.show(getApplicationContext(), error.getMessage());
                        }
                    });
                }
                break;
            case R.id.next_step:
                if (TextUtils.isEmpty(code)){
                    ToastUtil.show(getApplicationContext(), "验证码不能为空！");
                    return;
                }else {
                    userService.checkSmsCode(tel, code, new DefaultResponseListener<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse response) {
                            if(response!=null && response.getStatus().equals(AppConfig.HTTP_OK)){
                                firstStep.setVisibility(View.GONE);
                                secondStep.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onError(VolleyError error) {
                            Log.e(tag,String.valueOf(error.getMessage()));
                        }
                    });

                }
                break;
            case R.id.btn_for_sure:
                String smsCode = String.valueOf(inputCode.getText());
               // String oldPassword = String.valueOf(inputOldPassword.getText());
                String newPassword = String.valueOf(inputNewPassword.getText());
                String newPasswordAgain = String.valueOf(InputNewPasswordAgain);
                if (TextUtils.isEmpty(newPassword)){
                    ToastUtil.show(getApplicationContext(), "新密码不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(newPasswordAgain)){
                    ToastUtil.show(getApplicationContext(), "新密码不能为空！！");
                    inputNewPassword.setText("");
                    InputNewPasswordAgain.setText("");
                    return;
                }
                userService.modifyPassword(tel, smsCode, newPassword, newPasswordAgain,
                        new DefaultResponseListener<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        ToastUtil.show(getApplicationContext(), response.getMessage());
                        ComponentName componentName = new ComponentName(ResetPasswordActivity.this, LoginActivity_.class);
                        Intent intent = new Intent();
                        intent.setComponent(componentName);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(VolleyError error) {
                        ToastUtil.show(getApplicationContext(), "密码修改失败！");
                    }
                });
        }
    }

    public class BackCountTimerRunnable implements Runnable{
        @Override
        public void run() {
            seconds = seconds - 1;
            if (seconds == 0){
                seconds = START_SECONDS;
                requestCodeBtn.setClickable(true);
                requestCodeBtn.setText("重新获取");
            }else {
                requestCodeBtn.setText(String.format("%1$s后重新获取", seconds+"秒"));
                handler.postDelayed(this, ONE_MINUTE);
            }
        }
    }
}
