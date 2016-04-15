package com.zl.app.activity.mine;

import android.os.Bundle;
import android.widget.ListView;

import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;

/**
 * Created by fengxiang on 2016/4/15.
 */
public class MyYuyueActivity extends BaseActivityWithToolBar {

    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_yuyue);
        listView = (ListView) findViewById(R.id.listView);
        setBtnLeft1Enable(true);
        setTitle("我的预约");
    }
}
