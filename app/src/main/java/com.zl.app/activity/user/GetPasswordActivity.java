package com.zl.app.activity.user;


import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.user.UserService;
import com.zl.app.data.user.UserServiceImpl;
import com.zl.app.util.StringUtil;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.ViewUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

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

    @ViewById(R.id.validateCodeView)
    EditText validateCodeText;

    @ViewById(R.id.reget)
    LinearLayout reget;

    @ViewById(R.id.sendResponseView)
    TextView sendResponseView;

    @ViewById(R.id.reget_text)
    TextView regetTextView;

    UserService userService;
    Handler handler = new Handler();
    int seconds = 59;

    @AfterViews
    void afterViews() {
        userService = new UserServiceImpl();
        setTitle(getResources().getString(R.string.btn_get_pass));
        setBtnLeft1Enable(true);
    }

    @Click(R.id.reget)
    void getSmsCode() {
        String tel = String.valueOf(telView.getText());
        if (StringUtil.isEmpty(tel)) {
            ToastUtil.show(getApplicationContext(), "手机号不能为空");
            return;
        }
        userService.getPasswordCode(tel, new DefaultResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                ViewUtil.show(sendResponseView);
                ToastUtil.show(getApplicationContext(), response.getMessage());
                /*发出的验证码请求响应成功后，提示变为含有手机号（中间四位隐去）的信息*/
                String responseStr = getResources().getString(R.string.register_msg_response);
                String tel = telView.getText().toString();
                tel = tel.substring(0, tel.length() - (tel.substring(3)).length()) + "****" + tel.substring(7);
                responseStr = responseStr.replace("TEL", tel);
                sendResponseView.setText(responseStr);
                //重新获取不可点击 开始计时
                reget.setClickable(false);
                handler.postDelayed(new RegetCodeRunnable(), 1000);
                seconds = 59;
            }

            @Override
            public void onError(VolleyError error) {
                ToastUtil.show(getApplicationContext(), error.getMessage());
            }
        });
    }

    @Click(R.id.btn_get_pass)
    void btnGetPassClick() {
        String tel = String.valueOf(telView.getText());
        String smsCode = String.valueOf(validateCodeText.getText());
        if (StringUtil.isEmpty(tel)) {
            ToastUtil.show(getApplicationContext(), "手机号不能为空");
            return;
        }
        if (StringUtil.isEmpty(smsCode)) {
            ToastUtil.show(getApplicationContext(), "验证码不能为空");
            return;
        }

        userService.getNewPassword(tel, smsCode, new DefaultResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                ToastUtil.show(getApplicationContext(), response.getMessage());
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

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
}
