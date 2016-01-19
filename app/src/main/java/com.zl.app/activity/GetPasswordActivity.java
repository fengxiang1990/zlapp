package com.zl.app.activity;


import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zl.app.BaseActivity;
import com.zl.app.R;
import com.zl.app.util.ViewUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.get_password_activity)
public class GetPasswordActivity extends BaseActivity {

    String TAG = GetPasswordActivity.class.getName();

    @ViewById(R.id.msgText)
    TextView msgTextView;

    @ViewById(R.id.btn_get_pass)
    Button zhbtn;

    @ViewById(R.id.phoneText)
    EditText telView;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById
    TextView titleView;

    @ViewById(R.id.leftBtn1)
    ImageView leftBtn1;


    @AfterViews
    void afterViews() {
        toolbar.setTitle("");
        titleView.setText(getResources().getString(R.string.register_quick));
        setSupportActionBar(toolbar);
        ViewUtil.show(leftBtn1);
    }

    @Click(R.id.leftBtn1)
    void leftBtn1Click() {
        GetPasswordActivity.this.finish();
    }

    @Click(R.id.btn_get_pass)
    void btnGetPassClick() {

    }
}
