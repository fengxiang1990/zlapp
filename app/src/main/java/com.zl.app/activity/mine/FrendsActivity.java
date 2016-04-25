package com.zl.app.activity.mine;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.mine.MineService;
import com.zl.app.data.mine.MineServiceImpl;
import com.zl.app.model.customer.YyMobileContract;
import com.zl.app.model.user.YyMobileUserFans;
import com.zl.app.util.AppConfig;
import com.zl.app.util.RequestURL;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengxiang on 2016/4/25.
 */
public class FrendsActivity extends BaseActivityWithToolBar {

    ListView listView;
    MineService mineService;
    String uid;
    MyAdapter adapter;
    List<YyMobileUserFans> data;
    int pageNo = 1;
    int pageSize = 1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frends);
        listView = (ListView) findViewById(R.id.listView);
        setBtnLeft1Enable(true);
        setTitle("我的朋友");
        mineService = new MineServiceImpl();
        data = new ArrayList<YyMobileUserFans>();
        adapter = new MyAdapter(data);
        listView.setAdapter(adapter);
        uid = AppConfig.getUid(preference);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        mineService.getFrends(uid, pageNo, pageSize, new DefaultResponseListener<BaseResponse<List<YyMobileUserFans>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileUserFans>> response) {
                if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                    data.addAll(response.getResult());
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtil.show(FrendsActivity.this, response.getMessage());
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }


    class MyAdapter extends BaseAdapter {

        List<YyMobileUserFans> data;

        public MyAdapter(List<YyMobileUserFans> data) {
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
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_frend, null);
                holder = new ViewHolder();
                holder.img_header = (SimpleDraweeView) convertView.findViewById(R.id.img_header);
                holder.text_name = (TextView) convertView.findViewById(R.id.text_name);
                holder.text_activity = (TextView) convertView.findViewById(R.id.text_activity);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final YyMobileUserFans fans = data.get(position);
            if (fans != null) {
                holder.img_header.setImageURI(Uri.parse(fans.getPersonPic()));
                holder.text_name.setText(fans.getPersonName());
                holder.text_activity.setText(fans.getHdheadline());
            }
            return convertView;
        }

    }

    class ViewHolder {
        SimpleDraweeView img_header;
        TextView text_name;
        TextView text_activity;
    }
}
