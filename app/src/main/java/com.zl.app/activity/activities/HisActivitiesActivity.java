package com.zl.app.activity.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.VolleyError;
import com.zl.app.R;
import com.zl.app.activity.chart.ChartActivity;
import com.zl.app.activity.mine.FrendsActivity;
import com.zl.app.adapter.ActivityAdapter;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.ActivityService;
import com.zl.app.data.model.activity.YyMobileActivity;
import com.zl.app.data.user.UserServiceImpl;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.DateUtil;
import com.zl.app.util.ToastUtil;
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
    int mUserId;
    boolean isFrend = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activities);
        uid = AppConfig.getUid(AppManager.getPreferences());
        mUserId = AppConfig.getUserId(preference);
        userId = getIntent().getIntExtra("userId", 0);
        userName = getIntent().getStringExtra("userName");
        setTitle(userName + "的活动");
        setBtnLeft1Enable(true);
        if (userId == mUserId) {
            setBtnRight1Enable(false);
        }
        new UserServiceImpl().checkFrend(uid, userId, new DefaultResponseListener<BaseResponse<String>>() {
            @Override
            public void onSuccess(BaseResponse<String> response) {
                   if(response!=null && response.getStatus().equals(AppConfig.HTTP_OK)){
                       String result  = response.getMessage();
                       if(result.equals("2")){
                           isFrend = true;
                           setBtnRight1Enable(true);
                           setBtnRight1ImageResource(R.mipmap.ac_chart);
                       }
                       if(result.equals("1") && userId != mUserId){
                           isFrend = false;
                           setBtnRight1Enable(true);
                           setBtnRight1ImageResource(R.mipmap.addchild_icon);
                       }
                   }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
       // setBtnRight1ImageResource(R.mipmap.ac_chart);
        listView = (XListView) findViewById(R.id.listview);
        listView.setPullLoadEnable(false);
        data = new ArrayList<YyMobileActivity>();
        adapter = new ActivityAdapter(this, data, this);
        listView.setAdapter(adapter);
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

    @Override
    protected void onBtnRight1Click() {
        if(isFrend){
            Intent intent = new Intent(HisActivitiesActivity.this, ChartActivity.class);
            intent.putExtra("userId",userId);
            intent.putExtra("userName",userName);
            startActivity(intent);
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(HisActivitiesActivity.this)
                    .setTitle("系统提示")
                    .setMessage("是否添加对方为好友?")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            new UserServiceImpl().addFrend(uid,userId+"",2, new DefaultResponseListener<BaseResponse>() {
                                @Override
                                public void onSuccess(BaseResponse response) {
                                    if (response != null && response.getStatus().equals(AppConfig.HTTP_OK)) {
                                        ToastUtil.show(HisActivitiesActivity.this, "已添加好友");
                                        Intent intent = new Intent(HisActivitiesActivity.this,FrendsActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onError(VolleyError error) {

                                }
                            });
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                        }
                    });
            builder.create().show();
        }

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
