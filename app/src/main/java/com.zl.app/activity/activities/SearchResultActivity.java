package com.zl.app.activity.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.VolleyError;
import com.zl.app.R;
import com.zl.app.adapter.ActivityAdapter;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.ActivityService;
import com.zl.app.data.model.activity.YyMobileActivity;
import com.zl.app.util.AppConfig;
import com.zl.app.util.DateUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.maxwin.view.XListView;

/**
 * Created by fengxiang on 2016/4/28.
 */
public class SearchResultActivity extends BaseActivityWithToolBar implements ActivityAdapter.OnItemBtnClickListener {

    XListView listView;
    List<YyMobileActivity> data;
    ActivityAdapter adapter;
    String uid;
    String keyword = " ";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_activity_result);
        setTitle("搜索结果");
        setBtnLeft1Enable(true);
        setSearchTitleViewEnable(true);
        editSearch.setHint("搜索活动");
        listView = (XListView) findViewById(R.id.listview);
        listView.setPullLoadEnable(false);
        data = new ArrayList<YyMobileActivity>();
        adapter = new ActivityAdapter(SearchResultActivity.this,data,this);
        //keyword = getIntent().getStringExtra("keyword");
        listView.setAdapter(adapter);
        uid = AppConfig.getUid(preference);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword = String.valueOf(editSearch.getText());
                if (!TextUtils.isEmpty(keyword)) {
                    loadData();
                }
            }
        });
        editSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    keyword = String.valueOf(editSearch.getText());
                    if (!TextUtils.isEmpty(keyword)) {
                        loadData();
                    }
                }
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position - 1 < data.size()) {
                    YyMobileActivity yyMobileActivity = data.get(position - 1);
                    Intent intent = new Intent(SearchResultActivity.this, DetailActivity.class);
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
        new ActivityService().searchActivities(uid, keyword, new DefaultResponseListener<BaseResponse<List<YyMobileActivity>>>() {
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
