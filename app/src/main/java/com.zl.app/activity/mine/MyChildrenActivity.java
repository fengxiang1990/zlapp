package com.zl.app.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;

/**
 * Created by fengxiang on 2016/4/18.
 */
public class MyChildrenActivity extends BaseActivityWithToolBar {

    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_children);
        setTitle("我的宝贝");
        setBtnLeft1Enable(true);
        setBtnRight1Enable(true);
        setBtnRight1ImageResource(R.mipmap.addchild_icon);
        listView = (ListView) findViewById(R.id.listView2);
    }

    @Override
    protected void onBtnRight1Click() {
        super.onBtnRight1Click();
        Intent intent = new Intent(MyChildrenActivity.this, AddChildActivity.class);
        startActivity(intent);
    }
}
