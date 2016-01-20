package com.zl.app.activity;

import android.os.Handler;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zl.app.BaseActivity;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.util.ViewUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_register)
public class RegistActivity extends BaseActivityWithToolBar {

    private String TAG = RegistActivity.class.getName();

    @ViewById(R.id.telView)
    EditText telView;

    @ViewById(R.id.passwordView)
    EditText passwordText;

    @ViewById(R.id.repasswordView)
    EditText repasswordText;

    @ViewById(R.id.validateCodeView)
    EditText validateCodeText;

    @ViewById(R.id.complete_btn)
    Button completeBtn;

    @ViewById(R.id.next_btn)
    Button nextBtn;

    @ViewById(R.id.step1)
    LinearLayout step1;

    @ViewById(R.id.step2)
    LinearLayout step2;

    @ViewById(R.id.reget)
    LinearLayout reget;

    @ViewById(R.id.reget_text)
    TextView regetTextView;

    @ViewById(R.id.sendResponseView)
    TextView sendResponseView;


    @AfterViews
    void afterViews() {
        setTitle(getResources().getString(R.string.register_quick));
        setBtnLeft1Enable(true);
        setSupportActionBar(toolbar);
        initEvent();
    }

    @Click(R.id.reget)
    void regetCode() {
        regetCodeInUI();
    }

    @Click(R.id.next_btn)
    void nextBtnClick() {
        ViewUtil.hide(step1);
        ViewUtil.show(step2);
    }

    @Click(R.id.complete_btn)
    void completeBtnClick() {

    }

    @Background
    void regetCodeInBack() {
        regetCodeInUI();
    }

    @UiThread
    void regetCodeInUI() {
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
    }


    Handler handler = new Handler();

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

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    nextBtn.setBackgroundResource(R.drawable.login_btn_selector);
                } else {
                    nextBtn.setBackgroundResource(R.drawable.login_btn_shape_gray);
                }
            }
        });
    }


}
