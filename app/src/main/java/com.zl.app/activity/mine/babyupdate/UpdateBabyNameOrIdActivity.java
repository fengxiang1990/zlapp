package com.zl.app.activity.mine.babyupdate;

import android.os.Bundle;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.zl.app.R;
import com.zl.app.activity.mine.EditChildActivity;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.mine.MineServiceImpl;
import com.zl.app.util.AppConfig;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

/**
 * Created by fxa on 2016/5/28.
 */
public class UpdateBabyNameOrIdActivity extends BaseActivityWithToolBar {

    EditText editText;
    int type;
    String name;
    String idCard;
    String uid;
    int sid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nickename);
        editText = (EditText) findViewById(R.id.editText);
        type = getIntent().getIntExtra("type", 0);
        editText.setText(getIntent().getStringExtra("text"));
        uid = AppConfig.getUid(preference);
        sid = getIntent().getIntExtra("id", 0);
        String title = "";
        switch (type) {
            case EditChildActivity.UpdateStatus.TYPE_UP_NANME:
                title = "修改姓名";
                break;
            case EditChildActivity.UpdateStatus.TYPE_UP_IDCARD:
                title = "修改身份证";
                break;
        }
        setTitle(title);
        setBtnLeft1Enable(true);
        setTextRight1Enable(true);
        setTextRight1Val("保存");
    }

    @Override
    protected void onTextRight1Click() {
        super.onTextRight1Click();
        switch (type) {
            case EditChildActivity.UpdateStatus.TYPE_UP_NANME:
                name = String.valueOf(editText.getText());
                break;
            case EditChildActivity.UpdateStatus.TYPE_UP_IDCARD:
                idCard = String.valueOf(editText.getText());
                break;
        }
        new MineServiceImpl().updateStudent(uid, sid, null, name, null, idCard, 0, new DefaultResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                if (response != null && response.getStatus().equals(AppConfig.HTTP_OK)) {
                    finish();
                }
                ToastUtil.show(UpdateBabyNameOrIdActivity.this, response.getMessage());
            }

            @Override
            public void onError(VolleyError error) {
            }
        });
    }
}
