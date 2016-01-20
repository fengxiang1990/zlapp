package com.zl.app.activity;


import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.util.ToastUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.get_password_activity)
public class GetPasswordActivity extends BaseActivityWithToolBar {

    String TAG = GetPasswordActivity.class.getName();

    @ViewById(R.id.msgText)
    TextView msgTextView;

    @ViewById(R.id.btn_get_pass)
    Button zhbtn;

    @ViewById(R.id.phoneText)
    EditText telView;


    @AfterViews
    void afterViews() {
        setTitle(getResources().getString(R.string.btn_get_pass));
        setBtnLeft1Enable(true);
    }

    @Override
    protected void onBtnLeft1Click() {
        GetPasswordActivity.this.finish();
    }

    @Click(R.id.btn_get_pass)
    void btnGetPassClick() {

    }
}
