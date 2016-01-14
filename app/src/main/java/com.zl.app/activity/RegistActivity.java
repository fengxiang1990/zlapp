package com.zl.app.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zl.app.BaseActivity;
import com.zl.app.R;
import com.zl.app.util.ToastUtil;


public class RegistActivity extends BaseActivity {

    private String TAG = RegistActivity.class.getName();
    EditText telView;
    EditText passwordText;
    EditText repasswordText;
    EditText validateCodeText;
    Button completeBtn;
    TextView closeView;
    Button nextBtn;
    LinearLayout step1, step2;
    LinearLayout reget;
    TextView regetTextView;
    TextView sendResponseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
    }


    public void initView() {
        regetTextView = (TextView) this.findViewById(R.id.reget_text);
        reget = (LinearLayout) this.findViewById(R.id.reget);
        telView = (EditText) this.findViewById(R.id.telView);
        passwordText = (EditText) this.findViewById(R.id.passwordView);
        completeBtn = (Button) this.findViewById(R.id.complete_btn);
        nextBtn = (Button) this.findViewById(R.id.next_btn);
        closeView = (TextView) this.findViewById(R.id.closeView);
        validateCodeText = (EditText) this.findViewById(R.id.validateCodeView);
        step1 = (LinearLayout) this.findViewById(R.id.step1);
        step2 = (LinearLayout) this.findViewById(R.id.step2);
        repasswordText = (EditText) this.findViewById(R.id.repasswordView);
        sendResponseView = (TextView) this.findViewById(R.id.sendResponseView);
    }


    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    ToastUtil.show(getApplicationContext(), "注册成功");
                    RegistActivity.this.finish();
                    break;
                case 1:
                    ToastUtil.show(getApplicationContext(), "号码已经注册");
                    break;
                case 2:
                    step1.setVisibility(View.GONE);
                    step2.setVisibility(View.VISIBLE);
                    sendResponseView.setVisibility(View.VISIBLE);
                    String response = getResources().getString(R.string.register_msg_response);
                    String tel = telView.getText().toString();
                    tel = tel.substring(0, tel.length() - (tel.substring(3)).length()) + "****" + tel.substring(7);
                    response = response.replace("TEL", tel);
                    sendResponseView.setText(response);
                    //重新获取不可点击 开始计时
                    reget.setClickable(false);
                    handler.postDelayed(new RegetCodeRunnable(), 1000);
                    seconds = 59;
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

    };

    int seconds = 59;

    class RegetCodeRunnable implements Runnable {

        @Override
        public void run() {
            seconds = seconds - 1;
            if (seconds == 0) {
                seconds = 59;
                String regetMsg = getResources().getString(R.string.reget_text);
                reget.setClickable(true);
                regetTextView.setText(regetMsg);
            } else {
                handler.postDelayed(this, 1000);
                regetTextView.setText(seconds + "秒后重新获取");
            }
        }

    }

    public void initEvent() {
        telView.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // TODO Auto-generated method stub
                if (editable.length() > 0) {
                    nextBtn.setBackgroundResource(R.drawable.login_btn_selector);
                } else {
                    nextBtn.setBackgroundResource(R.drawable.login_btn_shape_gray);
                }
            }
        });

        reget.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

            }
        });

        nextBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

            }
        });

        completeBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

            }
        });

        closeView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                RegistActivity.this.finish();
            }
        });
    }


}
