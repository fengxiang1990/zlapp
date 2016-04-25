package com.zl.app.activity.mine;

import android.os.Bundle;

import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;

/**
 * Created by fengxiang on 2016/4/25.
 */
public class UpdateUserNameActivity extends BaseActivityWithToolBar {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nickename);
        setTitle("修改昵称");
        setBtnLeft1Enable(true);
        setTextRight1Enable(true);
        setTextRight1Val("保存");
    }

    @Override
    protected void onTextRight1Click() {
        super.onTextRight1Click();
    }
}
