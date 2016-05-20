package com.zl.app.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.VolleyError;
import com.zl.app.BaseFragment;
import com.zl.app.R;
import com.zl.app.activity.activities.DetailActivity;
import com.zl.app.activity.activities.PublishActivity;
import com.zl.app.adapter.ActivityAdapter;
import com.zl.app.data.ActivityService;
import com.zl.app.data.model.activity.YyMobileActivity;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.DateUtil;
import com.zl.app.util.GsonUtil;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.maxwin.view.XListView;

/**
 * Created by admin on 2015/12/28.
 */

@EFragment(R.layout.fragment_activities)
public class FragmentActivities extends BaseFragment implements ActivityAdapter.OnItemBtnClickListener{


    String TAG = "FragmentActivities";

    @ViewById(R.id.listview)
    XListView listView;

    List<YyMobileActivity> data;
    ActivityAdapter adapter;

    String uid;
    @AfterViews
    void afterViews() {
        data = new ArrayList<YyMobileActivity>();
        adapter = new ActivityAdapter(getActivity(),data,this);
        listView.setAdapter(adapter);
        uid= AppConfig.getUid(AppManager.getPreferences());
        listView.setPullLoadEnable(false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position - 1 < data.size()) {
                    YyMobileActivity yyMobileActivity = data.get(position - 1);
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
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

    }

    @Override
    public void onResume(){
        super.onResume();
        loadData();
    }

    public void loadData(){
        new ActivityService().getActivities(AppConfig.getUid(AppManager.getPreferences()), new DefaultResponseListener<BaseResponse<List<YyMobileActivity>>>() {
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
          Intent intent = new Intent(getActivity(), PublishActivity.class);
          intent.putExtra("activity", GsonUtil.gson.toJson(activity));
          startActivity(intent);
    }

    @Override
    public void onDelete(YyMobileActivity activity) {
        new ActivityService().delete(uid, activity.getActivityId() + "", new DefaultResponseListener<BaseResponse>() {
            @Override public void onSuccess(BaseResponse response) {
                if (response!=null) {
                    ToastUtil.show(getActivity(),response.getMessage());
                    loadData();
                }
            }

            @Override public void onError(VolleyError error) {

            }
        });
    }
}
