package com.zl.app.activity;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zl.app.BaseActivity;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.util.AppConfig;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.ViewUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.login_layout)
public class LoginActivity extends BaseActivityWithToolBar {

    @ViewById(R.id.forgetPassword)
    TextView wjmm;

    @ViewById(R.id.myzhdjzc)
    TextView myzhdjzc;

    @ViewById(R.id.savePassWord)
    CheckBox jzmm;

    @ViewById(R.id.passwordView)
    EditText passwordView;

    @ViewById(R.id.button1)
    Button loginBtn;

    @ViewById(R.id.usernameView)
    EditText userView;


    @AfterViews
    void afterViews() {
        setBtnLeft1Enable(true);
        setTitle(getResources().getString(R.string.login_title));
        myzhdjzc.setText(Html.fromHtml(getResources().getString(R.string.register_text)));
    }


    @Click(R.id.forgetPassword)
    void wjmmClick() {
        Intent intent = new Intent(LoginActivity.this, GetPasswordActivity_.class);
        startActivity(intent);
    }

    @CheckedChange(R.id.savePassWord)
    void savePassWordChecked(CompoundButton arg0, boolean ischecked) {
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

    @Click(R.id.myzhdjzc)
    void registNewUser() {
        Intent intent = new Intent(LoginActivity.this, RegistActivity_.class);
        startActivity(intent);
    }

    @Click(R.id.button1)
    void loginClick() {
        ToastUtil.show(this, "login click");
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
