package com.zl.app.activity.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.ActivityService;
import com.zl.app.model.activity.YyMobileActivity;
import com.zl.app.model.activity.YyMobileActivityUser;
import com.zl.app.model.customer.YyMobileCompany;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengxiang on 2016/5/11.
 */
public class JoinMembersActivity extends BaseActivityWithToolBar {

    String tag = "JoinMembersActivity";

    ListView listView;
    List<YyMobileActivityUser> data;
    MyAdapter adapter;
    String uid;
    String activityId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinac_members);
        listView = (ListView) findViewById(R.id.listView6);
        data = new ArrayList<YyMobileActivityUser>();
        adapter = new MyAdapter(data);
        listView.setAdapter(adapter);
        uid = AppConfig.getUid(preference);
        activityId = getIntent().getStringExtra("id");
        setBtnLeft1Enable(true);
        setTitle("参与人员");
        loadData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                YyMobileActivityUser user = data.get(position);
                Intent intent = new Intent(JoinMembersActivity.this,HisActivitiesActivity.class);
                intent.putExtra("userId",user.getUserId());
                intent.putExtra("userName",user.getUsername());
                startActivity(intent);

            }
        });
    }

    public void loadData() {
        LoadingDialog.getInstance(JoinMembersActivity.this).show();
        new ActivityService().getActivityMembers(uid, activityId, new DefaultResponseListener<BaseResponse<List<YyMobileActivityUser>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileActivityUser>> response) {
                LoadingDialog.getInstance(JoinMembersActivity.this).dismiss();
                if (response != null && response.getResult() != null) {
                    data.addAll(response.getResult());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(VolleyError error) {
                LoadingDialog.getInstance(JoinMembersActivity.this).dismiss();
            }
        });
    }

    class MyAdapter extends BaseAdapter {

        List<YyMobileActivityUser> data;

        public MyAdapter(List<YyMobileActivityUser> data) {
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
                convertView = LayoutInflater.from(JoinMembersActivity.this).inflate(R.layout.item_ac_join_member, null);
                holder = new ViewHolder();
                holder.img_header = (SimpleDraweeView) convertView.findViewById(R.id.img_header);
                holder.text_name = (TextView) convertView.findViewById(R.id.text_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            YyMobileActivityUser user = data.get(position);
            holder.img_header.setImageURI(Uri.parse(user.getPicPath()));
            holder.text_name.setText(user.getUsername());
            return convertView;
        }

    }

    class ViewHolder {
        SimpleDraweeView img_header;
        TextView text_name;
    }
}
