package com.zl.app.activity.org;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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

/**
 * Created by fxa on 2016/4/14.
 */
public class OrgYuYueActivity extends BaseActivityWithToolBar {

    EditText edit_name, edit_tel, edit_content;
    Button btn_send;

    String uid;
    String phone;
    String companyId;

    HomeService homeService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_yuyue);
        homeService = new HomeServiceImpl();
        setBtnLeft1Enable(true);
        setTitle("预约咨询");
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_tel = (EditText) findViewById(R.id.edit_tel);
        edit_content = (EditText) findViewById(R.id.edit_content);
        btn_send = (Button) findViewById(R.id.btn_send);
        companyId = getIntent().getStringExtra("companyId");
        phone = getIntent().getStringExtra("phone");
        if(!TextUtils.isEmpty(phone)){
            setBtnRight1Enable(true);
            setBtnRight1ImageResource(R.mipmap.phone_icon);
        }
        uid = AppConfig.getUid(preference);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(edit_name.getText());
                String tel = String.valueOf(edit_tel.getText());
                String content = String.valueOf(edit_content.getText());
                if (TextUtils.isEmpty(name)) {
                    ToastUtil.show(getApplicationContext(), "姓名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(tel)) {
                    ToastUtil.show(getApplicationContext(), "联系方式不能为空");
                    return;
                }
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.show(getApplicationContext(), "留言不能为空");
                    return;
                }
                homeService.postOrgYuyue(uid, companyId, name, tel, content, new DefaultResponseListener<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        if (response != null) {
                            if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                                finish();
                            }
                            ToastUtil.show(getApplicationContext(),response.getResult());
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
            }
        });
    }

    @Override
    protected void onBtnRight1Click() {
        super.onBtnRight1Click();
        Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
