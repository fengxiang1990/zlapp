package com.zl.app.activity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zl.app.BaseActivity;
import com.zl.app.R;


public class GetPasswordActivity extends BaseActivity {

    String TAG = RegistActivity.class.getName();

    TextView closeView, msgTextView;
    Button zhbtn;
    EditText telView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setContentView(R.layout.get_password_activity);
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
    }

    public void initView() {
        // TODO Auto-generated method stub
        zhbtn = (Button) this.findViewById(R.id.btn_get_pass);
        closeView = (TextView) this.findViewById(R.id.closeView);
        msgTextView = (TextView) this.findViewById(R.id.msgText);
        telView = (EditText) this.findViewById(R.id.phoneText);
    }


    public void initEvent() {
        // TODO Auto-generated method stub
        zhbtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0){

            }
        });


        closeView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                GetPasswordActivity.this.finish();
            }
        });
    }


    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    msgTextView.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

    };
}
