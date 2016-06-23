package com.zl.app.activity.org;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.home.HomeService;
import com.zl.app.data.home.HomeServiceImpl;
import com.zl.app.util.AppConfig;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.view.LoadingDialog;

/**
 * Created by fxa on 2016/6/23.
 */

public class OrgSendMsgActivity  extends BaseActivityWithToolBar {
    EditText text_content;
    String uid;
    String cid;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_shequan_send);
        setTitle("请您留言");
        setTextRight1Enable(true);
        setTextRight1Val("发送");
        setBtnLeft1Enable(true);
        uid = AppConfig.getUid(preference);
        cid = getIntent().getStringExtra("cid");
        text_content = (EditText) findViewById(R.id.text_content);
        homeService = new HomeServiceImpl();
    }
    HomeService homeService;
    @Override
    protected void onTextRight1Click() {
        super.onTextRight1Click();
        if(TextUtils.isEmpty(String.valueOf(text_content.getText()))){
            return;
        }
        LoadingDialog.getInstance(OrgSendMsgActivity.this).show();
        homeService.sendOrgMessage(uid, cid, null, String.valueOf(text_content.getText()), AppConfig.getUserHeadImg(preference), new DefaultResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                LoadingDialog.getInstance(OrgSendMsgActivity.this).dismiss();
                if(response.getStatus().equals(AppConfig.HTTP_OK)){
                    finish();
                }
                ToastUtil.show(OrgSendMsgActivity.this,response.getMessage());
            }

            @Override
            public void onError(VolleyError error) {
                LoadingDialog.getInstance(OrgSendMsgActivity.this).dismiss();
                ToastUtil.show(OrgSendMsgActivity.this,error.getMessage());
            }
        });
    }
}
