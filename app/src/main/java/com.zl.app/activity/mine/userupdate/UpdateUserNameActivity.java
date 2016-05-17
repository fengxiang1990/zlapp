package com.zl.app.activity.mine.userupdate;

import android.os.Bundle;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.user.UserServiceImpl;
import com.zl.app.data.user.model.YyMobileUser;
import com.zl.app.util.AppConfig;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

/**
 * Created by fengxiang on 2016/4/25.
 */
public class UpdateUserNameActivity extends BaseActivityWithToolBar {

    EditText editText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nickename);
        editText = (EditText) findViewById(R.id.editText);
        setTitle("修改昵称");
        setBtnLeft1Enable(true);
        setTextRight1Enable(true);
        setTextRight1Val("保存");
    }

    @Override
    protected void onTextRight1Click() {
        super.onTextRight1Click();
        new UserServiceImpl().updateUserInfo(AppConfig.getUid(preference), null,
                String.valueOf(editText.getText()),
                null, null, null, null, null, null, null, null, null,null, new DefaultResponseListener<BaseResponse>() {

                    @Override
                    public void onSuccess(BaseResponse response) {
                        if(response!=null && response.getStatus().equals(AppConfig.HTTP_OK)){
                            YyMobileUser user = AppConfig.getUserInfo(preference);
                            user.setNickName(String.valueOf(editText.getText()));
                            AppConfig.saveUpdateInfo(preference,user);
                            ToastUtil.show(UpdateUserNameActivity.this,response.getMessage());
                            finish();
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {
                        ToastUtil.show(UpdateUserNameActivity.this,error.getMessage());
                    }
                }
        );
    }
}
