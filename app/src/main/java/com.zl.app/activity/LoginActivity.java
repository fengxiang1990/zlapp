package com.zl.app.activity;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zl.app.BaseActivity;
import com.zl.app.R;
import com.zl.app.util.AppConfig;
import com.zl.app.util.ToastUtil;

public class LoginActivity extends BaseActivity {

    TextView wjmm;
    TextView myzhdjzc;
    CheckBox jzmm;
    EditText passwordView;
    Button loginBtn;

    EditText userView;
    ImageButton backBtn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.login_layout);
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
        initData();
    }

    public void initView() {
        userView = (EditText) this.findViewById(R.id.usernameView);
        passwordView = (EditText) this.findViewById(R.id.passwordView);
        loginBtn = (Button) this.findViewById(R.id.button1);
        myzhdjzc = (TextView) this.findViewById(R.id.myzhdjzc);
        jzmm = (CheckBox) this.findViewById(R.id.savePassWord);
        wjmm = (TextView) this.findViewById(R.id.forgetPassword);
        backBtn = (ImageButton) this.findViewById(R.id.backBtn);
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    ToastUtil.show(getApplicationContext(), "登陆成功");
                    LoginActivity.this.finish();
                    break;
                case 1:
                    ToastUtil.show(getApplicationContext(), "用户不存在");
                    break;
                case 2:
                    ToastUtil.show(getApplicationContext(), "密码错误");
                    break;
                case 3:
                    ToastUtil.show(getApplicationContext(), "账号停用");
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

    };


    public void initEvent() {
        backBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                LoginActivity.this.finish();
            }
        });
        wjmm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(LoginActivity.this, GetPasswordActivity.class);
                startActivity(intent);
            }
        });
        jzmm.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean ischecked) {
                if (ischecked) {
                    Editor editor = preference.edit();
                    editor.putBoolean(AppConfig.IS_REMEMBER_PASSWORD, true);
                    editor.commit();
                } else {
                    Editor editor = preference.edit();
                    editor.putBoolean(AppConfig.IS_REMEMBER_PASSWORD, false);
                    editor.commit();
                }
            }
        });
        myzhdjzc.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(intent);

            }
        });

        loginBtn.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {

            }
        });
    }


    public void initData() {
        // TODO Auto-generated method stub
        myzhdjzc.setText(Html.fromHtml(getResources().getString(R.string.register_text)));
    }

    @Override
    protected void onResume() {
        boolean isRemember = preference.getBoolean(AppConfig.IS_REMEMBER_PASSWORD, false);
        if (isRemember) {
            userView.setText(preference.getString(AppConfig.TEL_PHONE, ""));
            passwordView.setText(preference.getString(AppConfig.PASS_WORD, ""));
            jzmm.setChecked(true);
        }
        super.onResume();
    }


}
