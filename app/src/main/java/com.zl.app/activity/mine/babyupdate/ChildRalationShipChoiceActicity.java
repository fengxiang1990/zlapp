package com.zl.app.activity.mine.babyupdate;

import android.os.Bundle;
import android.widget.RadioGroup;

import com.android.volley.VolleyError;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.mine.MineServiceImpl;
import com.zl.app.util.AppConfig;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

/**
 * 孩子关系选择
 * Created by fxa on 2016/6/7.
 */

public class ChildRalationShipChoiceActicity extends BaseActivityWithToolBar {

    RadioGroup rg;
    int type = 0;
    String uid;
    int sid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_relationship_choice);
        setTitle("修改宝贝与我的关系");
        setBtnLeft1Enable(true);
        uid = AppConfig.getUid(preference);
        sid = getIntent().getIntExtra("id", 0);
        type = getIntent().getIntExtra("type", 0);
        rg = (RadioGroup) findViewById(R.id.rg);
        int id = 0;
        switch (type){
            case 1:
                id = R.id.r1;
                break;
            case 2:
                id = R.id.r2;
                break;
            case 3:
                id = R.id.r3;
                break;
            case 4:
                id = R.id.r4;
                break;
            case 5:
                id = R.id.r5;
                break;
            case 6:
                id = R.id.r6;
                break;
        }
        rg.check(id);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.r1:
                        type = 1;
                        break;
                    case R.id.r2:
                        type = 2;
                        break;
                    case R.id.r3:
                        type = 3;
                        break;
                    case R.id.r4:
                        type = 4;
                        break;
                    case R.id.r5:
                        type = 5;
                        break;
                    case R.id.r6:
                        type = 6;
                        break;
                }
                new MineServiceImpl().updateStudent(uid, sid, null, null, null, null, type, new DefaultResponseListener<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        if (response != null && response.getStatus().equals(AppConfig.HTTP_OK)) {
                            finish();
                        }
                        ToastUtil.show(ChildRalationShipChoiceActicity.this, response.getMessage());
                    }

                    @Override
                    public void onError(VolleyError error) {
                    }
                });
            }
        });
    }
}
