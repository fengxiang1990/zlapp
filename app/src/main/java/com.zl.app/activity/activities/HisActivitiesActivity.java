package com.zl.app.activity.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.VolleyError;
import com.zl.app.R;
import com.zl.app.adapter.ActivityAdapter;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.ActivityService;
import com.zl.app.model.activity.YyMobileActivity;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.DateUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.maxwin.view.XListView;

/**
 * 他参与的活动
 * Created by fengxiang on 2016/5/13.
 */
public class HisActivitiesActivity extends BaseActivityWithToolBar implements ActivityAdapter.OnItemBtnClickListener {

    String tag = "HisActivitiesActivity";
    XListView listView;
    List<YyMobileActivity> data;
    ActivityAdapter adapter;
    String uid;
    int userId;
    String userName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activities);
        userId = getIntent().getIntExtra("userId", 0);
        userName = getIntent().getStringExtra("userName");
        setTitle(userName + "的活动");
        setBtnLeft1Enable(true);
        listView = (XListView) findViewById(R.id.listview);
        listView.setPullLoadEnable(false);
        data = new ArrayList<YyMobileActivity>();
        adapter = new ActivityAdapter(this, data, this);
        listView.setAdapter(adapter);
        uid = AppConfig.getUid(AppManager.getPreferences());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position - 1 < data.size()) {
                    YyMobileActivity yyMobileActivity = data.get(position - 1);
                    Intent intent = new Intent(HisActivitiesActivity.this, DetailActivity.class);
                    intent.putExtra("activityId", yyMobileActivity.getActivityId());
                    intent.putExtra("title", yyMobileActivity.getHeadline());
                    startActivity(intent);
                }
            }
        });
        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                listView.setRefreshTime(DateUtil.DateToString(new Date(), DateUtil.DateStyle.YYYY_MM_DD_HH_MM));
                loadData();
            }

            @Override
            public void onLoadMore() {

            }
        });
        loadData();
    }


    public void loadData() {
        new ActivityService().getHisActivities(AppConfig.getUid(AppManager.getPreferences()), userId, new DefaultResponseListener<BaseResponse<List<YyMobileActivity>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileActivity>> response) {
                listView.stopRefresh();
                if (response != null && response.getResult() != null) {
                    data.clear();
                    data.addAll(response.getResult());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(VolleyError error) {
                listView.stopRefresh();
            }
        });
    }

    @Override
    public void onEdit(YyMobileActivity activity) {

    }

    @Override
    public void onDelete(YyMobileActivity activity) {

    }
}
