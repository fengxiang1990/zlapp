package com.zl.app.fragment;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.BaseFragment;
import com.zl.app.R;
import com.zl.app.data.ActivityService;
import com.zl.app.model.activity.YyMobileActivity;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.DateUtil;
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
public class FragmentActivities extends BaseFragment {


    String TAG = "FragmentActivities";

    @ViewById(R.id.listview)
    XListView listView;

    List<YyMobileActivity> data;
    MyAdapter adapter;

    String uid;
    @AfterViews
    void afterViews() {
        data = new ArrayList<YyMobileActivity>();
        adapter = new MyAdapter(data);
        listView.setAdapter(adapter);
        uid= AppConfig.getUid(AppManager.getPreferences());
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
    class MyAdapter extends BaseAdapter {

        List<YyMobileActivity> data;

        public MyAdapter(List<YyMobileActivity> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_activity, null);
                holder = new ViewHolder();
                holder.simpleDraweeView = (SimpleDraweeView) convertView.findViewById(R.id.simpleDraweeView);
                holder.text_title = (TextView) convertView.findViewById(R.id.text_title);
                holder.text_time = (TextView) convertView.findViewById(R.id.text_time);
                holder.text_status = (TextView) convertView.findViewById(R.id.text_status);
                holder.text_location = (TextView) convertView.findViewById(R.id.text_location);
                holder.ll_edit = (LinearLayout) convertView.findViewById(R.id.ll_edit);
                holder.img_delete = (ImageView) convertView.findViewById(R.id.img_delete);
                holder.img_edit = (ImageView) convertView.findViewById(R.id.img_edit);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final YyMobileActivity activity = data.get(position);
            if (activity != null) {
                Uri uri = Uri.parse(activity.getPicPath() == null ? "" : activity.getPicPath());
                holder.simpleDraweeView.setImageURI(uri);
                holder.text_title.setText(activity.getHeadline());
                holder.text_location.setText(activity.getAddress());
                holder.text_time.setText(activity.getHdDate());
                String statusStr = "";
                switch (activity.getIsover()) {
                    case 2:
                        statusStr = "进行中";
                        holder.text_status.setTextColor(getResources().getColor(R.color.red));
                        break;
                    case 3:
                        statusStr = "往期活动";
                        holder.text_status.setTextColor(getResources().getColor(R.color.gray));
                        break;
                }
                if (activity.getUserId() == AppConfig.getUserId(AppManager.getPreferences())) {
                    holder.ll_edit.setVisibility(View.VISIBLE);
                } else {
                    holder.ll_edit.setVisibility(View.GONE);
                }
                holder.text_status.setText(statusStr);
                holder.img_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new ActivityService().delete(uid, activity.getActivityId() + "", new DefaultResponseListener<BaseResponse>() {
                            @Override
                            public void onSuccess(BaseResponse response) {
                                if (response!=null) {
                                    ToastUtil.show(getActivity(),response.getMessage());
                                    loadData();
                                }
                            }

                            @Override
                            public void onError(VolleyError error) {

                            }
                        });
                    }
                });
            }

            return convertView;
        }

    }

    class ViewHolder {
        SimpleDraweeView simpleDraweeView;
        TextView text_title;
        TextView text_status;
        TextView text_location;
        TextView text_time;
        ImageView img_edit;
        ImageView img_delete;
        LinearLayout ll_edit;
    }

}
