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
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.mine.MineService;
import com.zl.app.data.mine.MineServiceImpl;
import com.zl.app.data.model.customer.YyMobileContract;
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
public class OrderActivity extends BaseActivityWithToolBar {

    ListView listView;
    DownloadManager manager;
    MineService mineService;
    String uid;
    MyAdapter adapter;
    List<YyMobileContract> data;
    DownloadCompleteReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        listView = (ListView) findViewById(R.id.listView);
        setBtnLeft1Enable(true);
        setTitle("我的订单");
        manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        receiver = new DownloadCompleteReceiver();
        mineService = new MineServiceImpl();
        data = new ArrayList<YyMobileContract>();
        adapter = new MyAdapter(data);
        listView.setAdapter(adapter);
        uid = AppConfig.getUid(preference);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                YyMobileContract yyMobileContract = data.get(position);
                DownloadManager.Request down = new DownloadManager.Request(Uri.parse(RequestURL.SERVER + yyMobileContract.getFilePath()));
                down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                down.setShowRunningNotification(true);
                down.setVisibleInDownloadsUi(false);
                down.setDestinationInExternalFilesDir(OrderActivity.this, null, yyMobileContract.getHeadline() + ".docx");
                manager.enqueue(down);
            }
        });
        mineService.getOrders(uid, new DefaultResponseListener<BaseResponse<List<YyMobileContract>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileContract>> response) {
                if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                    data.addAll(response.getResult());
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtil.show(OrderActivity.this, response.getMessage());
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    //接受下载完成后的intent
    class DownloadCompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                ToastUtil.show(context, "下载完成");
            }
        }
    }

    @Override
    protected void onResume() {
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (receiver != null) unregisterReceiver(receiver);
        super.onDestroy();
    }

    class MyAdapter extends BaseAdapter {

        List<YyMobileContract> data;

        public MyAdapter(List<YyMobileContract> data) {
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
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_order, null);
                holder = new ViewHolder();
                holder.text_class_name = (TextView) convertView.findViewById(R.id.text_class_name);
                holder.text_time = (TextView) convertView.findViewById(R.id.text_time);
                holder.text_sum = (TextView) convertView.findViewById(R.id.text_sum);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final YyMobileContract order = data.get(position);
            if (order != null) {
                holder.text_class_name.setText(order.getHeadline());
                holder.text_time.setText(order.getCreateDate());
                holder.text_sum.setText("0");
            }
            return convertView;
        }

    }

    class ViewHolder {
        TextView text_class_name;
        TextView text_time;
        TextView text_sum;

    }
}
