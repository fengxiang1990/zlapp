package com.zl.app.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;

/**
 * Created by CQ on 2016/5/6 0006.
 */
public class ResetPasswordActivity extends BaseActivityWithToolBar implements View.OnClickListener{

    private EditText editText1;
    private EditText editText2;
    private Button requestVerificationCode;
    private Button nextAndSummit;

    private int currentLevel;
    private static final int LEVEL_VERIFICATION = 1;
    private static final int LEVEL_PASSWORD_RESETION = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        setBtnLeft1Enable(true);
        setTitle(R.string.reset_password);

        editText1 = (EditText)findViewById(R.id.edit_text1);
        editText2 = (EditText)findViewById(R.id.edit_text2);
        requestVerificationCode = (Button)findViewById(R.id.request_verification_code);
        nextAndSummit = (Button)findViewById(R.id.next_and_summit);
        currentLevel = LEVEL_VERIFICATION;

        requestVerificationCode.setOnClickListener(this);
        nextAndSummit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

    }
}
