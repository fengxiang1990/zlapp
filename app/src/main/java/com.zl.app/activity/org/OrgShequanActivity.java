package com.zl.app.activity.org;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.home.HomeService;
import com.zl.app.data.home.HomeServiceImpl;
import com.zl.app.data.model.customer.YyMobileCompanyComment;
import com.zl.app.util.AppConfig;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fxa on 2016/6/23.
 */

public class OrgShequanActivity extends BaseActivityWithToolBar {

    String uid;
    String cid;
    ListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_shequan);
        setTitle("机构社圈");
        setTextRight1Enable(true);
        setTextRight1Val("留言");
        setBtnLeft1Enable(true);
        listView = (ListView) findViewById(R.id.listView);
        uid = AppConfig.getUid(preference);
        cid = getIntent().getStringExtra("cid");
        homeService = new HomeServiceImpl();
        data = new ArrayList<>();
        adapter = new MyAdapter(data);
        listView.setAdapter(adapter);
    }

    HomeService homeService;
    List<YyMobileCompanyComment> data;
    MyAdapter adapter;
    @Override
    protected void onResume() {
        super.onResume();
        LoadingDialog.getInstance(this).show();
        homeService.getOrgMessages(uid, cid, new DefaultResponseListener<BaseResponse<List<YyMobileCompanyComment>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileCompanyComment>> response) {
                LoadingDialog.getInstance(OrgShequanActivity.this).dismiss();
                data.clear();
                data.addAll(response.getResult());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VolleyError error) {
                LoadingDialog.getInstance(OrgShequanActivity.this).dismiss();
            }
        });
    }

    @Override
    protected void onTextRight1Click() {
        super.onTextRight1Click();
        Intent intent= new Intent(OrgShequanActivity.this,OrgSendMsgActivity.class);
        intent.putExtra("cid",cid);
        startActivity(intent);
    }

    class MyAdapter extends BaseAdapter {
        List<YyMobileCompanyComment> data;

        public MyAdapter(List<YyMobileCompanyComment> data) {
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
                holder = new ViewHolder();
                convertView = LayoutInflater.from(OrgShequanActivity.this).inflate(R.layout.item_org_shequan, null);
                holder.text_user = (TextView) convertView.findViewById(R.id.text_user);
                holder.text_content = (TextView) convertView.findViewById(R.id.text_content);
                holder.text_update_time = (TextView) convertView.findViewById(R.id.text_update_time);
                holder.img_header = (SimpleDraweeView) convertView.findViewById(R.id.img_header);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            YyMobileCompanyComment YyMobileCompanyComment = data.get(position);
            holder.img_header.setImageURI(Uri.parse(YyMobileCompanyComment.getPicPath()));
            holder.text_update_time.setText(YyMobileCompanyComment.getCreateDate());
            holder.text_user.setText(YyMobileCompanyComment.getUsername());
            holder.text_content.setText(YyMobileCompanyComment.getContent());
            return convertView;
        }
    }

    class ViewHolder {
        SimpleDraweeView img_header;
        TextView text_update_time;
        TextView text_content;
        TextView text_user;

    }
}
