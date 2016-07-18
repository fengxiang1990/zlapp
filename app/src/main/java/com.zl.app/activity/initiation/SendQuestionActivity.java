package com.zl.app.activity.initiation;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.InitiationService;
import com.zl.app.util.AppConfig;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by fxa on 2016/7/17.
 */
@EActivity(R.layout.activity_send_question)
public class SendQuestionActivity extends BaseActivityWithToolBar {

    String tag = "SendQuestionActivity";
    Context context;


    @ViewById
    EditText edit_content;

    @AfterViews
    void afterViews() {
        context = this;
        setTitle("发布提问");
        setBtnLeft1Enable(true);
    }


    @Click(R.id.btn_send)
    void send() {
        final String content = edit_content.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.show(context, "提问内容不能为空");
            return;
        }
        new InitiationService().ask(content, AppConfig.getUid(preference), new DefaultResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                if (response != null) {
                    if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                        finish();
                    }
                    ToastUtil.show(context, response.getMessage());
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }
}
