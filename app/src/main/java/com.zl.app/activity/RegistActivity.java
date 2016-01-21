package com.zl.app.activity;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.user.UserService;
import com.zl.app.data.user.UserServiceImpl;
import com.zl.app.util.AppConfig;
import com.zl.app.util.StringUtil;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.ViewUtil;
import com.zl.app.util.net.BaseResponse;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_register)
public class RegistActivity extends BaseActivityWithToolBar {

    private String TAG = RegistActivity.class.getName();

    @ViewById(R.id.telView)
    EditText telView;

    @ViewById(R.id.nickNameView)
    EditText nickNameView;

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

    UserService userService;

    @AfterViews
    void afterViews() {
        userService = new UserServiceImpl();
        setTitle(getResources().getString(R.string.register_quick));
        setBtnLeft1Enable(true);
        setSupportActionBar(toolbar);
        initEvent();
    }

    @Click(R.id.reget)
    void regetCode() {
        regetCodeInUI();
    }

    String mobile = null;

    @Click(R.id.next_btn)
    void nextBtnClick() {
        mobile = String.valueOf(telView.getText());
        checkMobile(mobile);
    }

    @Background
    public void checkMobile(String mobile) {
        BaseResponse response = null;
        try {
            response = userService.registCheck(mobile);
            Log.i(TAG, response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkMobileUIThread(response);
    }

    @UiThread
    public void checkMobileUIThread(BaseResponse response) {
        if (response != null) {
            if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                ViewUtil.hide(step1);
                ViewUtil.show(step2);
                //发送验证码
                sendCode();
            } else {
                ToastUtil.show(getApplicationContext(), response.getMessage());
            }
        } else {
            ToastUtil.show(getApplicationContext(), "请求失败");
        }
    }

    @Background
    public void sendCode() {
        BaseResponse response = null;
        try {
            response = userService.registSendCode(mobile);
            Log.i(TAG, response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendCodeUIThread(response);
    }

    @UiThread
    public void sendCodeUIThread(BaseResponse response) {
        if (response != null) {
            ToastUtil.show(getApplicationContext(), response.getMessage());
        } else {
            ToastUtil.show(getApplicationContext(), "请求失败");
        }
    }

    @Background
    public void regist(String mobile, String password, String passWordTwo, String nickName, String remark) {
        BaseResponse response = null;
        try {
            response = userService.regist(mobile, password, passWordTwo, nickName, remark);
            Log.i(TAG, response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        registUIThread(response);
    }

    @UiThread
    public void registUIThread(BaseResponse response) {
        if (response != null) {
            if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RegistActivity.this.finish();
                    }
                }, 1000);
            }
            ToastUtil.show(getApplicationContext(), response.getMessage());
        } else {
            ToastUtil.show(getApplicationContext(), "请求失败");
        }
    }

    @Click(R.id.complete_btn)
    void completeBtnClick() {
        //注册
        String password = String.valueOf(passwordText.getText());
        String password2 = String.valueOf(repasswordText.getText());
        String nickName = String.valueOf(nickNameView.getText());
        String code = String.valueOf(validateCodeText.getText());
        if (StringUtil.isEmpty(code)) {
            ToastUtil.show(getApplicationContext(), "验证码不能为空");
            return;
        }
        if (StringUtil.isEmpty(password) ||
                StringUtil.isEmpty(password2)) {
            ToastUtil.show(getApplicationContext(), "密码不能为空");
            return;
        }
        if (StringUtil.isEmpty(nickName)) {
            ToastUtil.show(getApplicationContext(), "用户昵称不能为空");
            return;
        }
        regist(mobile, password, password2, nickName, code);
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
