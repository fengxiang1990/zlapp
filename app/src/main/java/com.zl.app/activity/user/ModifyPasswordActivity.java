package com.zl.app.activity.user;

import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.user.UserService;
import com.zl.app.data.user.UserServiceImpl;
import com.zl.app.util.AppConfig;
import com.zl.app.util.StringUtil;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by admin on 2016/1/22.
 */
@EActivity(R.layout.activity_modify_password)
public class ModifyPasswordActivity extends BaseActivityWithToolBar {

    String TAG = "ModifyPasswordActivity";
    @ViewById
    EditText edit_old_password;

    @ViewById
    EditText edit_new_password;

    @ViewById
    EditText edit_new_password2;

    UserService userService;

    @AfterViews
    public void afterViews() {
        setTitle("修改密码");
        setBtnLeft1Enable(true);
        userService = new UserServiceImpl();
    }

    @Click(R.id.complete_btn)
    void btnCompleteClick() {
        String passOld = String.valueOf(edit_old_password.getText());
        String passNew = String.valueOf(edit_new_password.getText());
        String passNew2 = String.valueOf(edit_new_password2.getText());
        if (StringUtil.isEmpty(passOld)) {
            ToastUtil.show(getApplicationContext(), "密码不能为空");
            return;
        }
        if (StringUtil.isEmpty(passNew)) {
            ToastUtil.show(getApplicationContext(), "密码不能为空");
            return;
        }
        if (StringUtil.isEmpty(passNew2)) {
            ToastUtil.show(getApplicationContext(), "密码不能为空");
            return;
        }
        userService.modifyPassword(AppConfig.getUid(preference), passOld, passNew, passNew2,
                new DefaultResponseListener<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse response) {
                ToastUtil.show(getApplicationContext(), response.getMessage());
                Intent intent = new Intent(ModifyPasswordActivity.this, LoginActivity_.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void onError(VolleyError error) {
                Log.i(TAG, error.getMessage());
                ToastUtil.show(getApplicationContext(), "修改密码失败");
            }
        });
    }

}
