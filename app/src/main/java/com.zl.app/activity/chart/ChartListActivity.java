package com.zl.app.activity.chart;

import android.content.Intent;
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
import com.zl.app.data.ChartService;
import com.zl.app.data.model.user.YyMobileUserLetter;
import com.zl.app.util.AppConfig;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息列表
 * Created by fxa on 2016/6/4.
 */

public class ChartListActivity extends BaseActivityWithToolBar {

    String tag = "ChartListActivity";

    ListView listView;
    ChartListAdapter adapter;
    List<YyMobileUserLetter> data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_list);
        setTitle("聊天记录");
        setBtnLeft1Enable(true);
        listView = (ListView) findViewById(R.id.listView);
        data = new ArrayList<>();
        adapter = new ChartListAdapter(data);
        listView.setAdapter(adapter);
        loadData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                YyMobileUserLetter letter = data.get(position);
                Intent intent = new Intent(ChartListActivity.this,ChartActivity.class);
                intent.putExtra("userId",letter.getPersonId());
                intent.putExtra("userName",letter.getPersonName());
                startActivity(intent);
            }
        });
    }

    public void loadData() {
        LoadingDialog.getInstance(ChartListActivity.this).show();
        getChartList();
    }

    public void getChartList() {
        new ChartService().getChartList(AppConfig.getUid(preference), 1, 1000, new DefaultResponseListener<BaseResponse<List<YyMobileUserLetter>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileUserLetter>> response) {
                LoadingDialog.getInstance(ChartListActivity.this).dismiss();
                if (response != null && response.getResult() != null) {
                    data.clear();
                    data.addAll(response.getResult());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(VolleyError error) {
                LoadingDialog.getInstance(ChartListActivity.this).dismiss();
            }
        });
    }

    class ChartListAdapter extends BaseAdapter {
        List<YyMobileUserLetter> data;

        public ChartListAdapter(List<YyMobileUserLetter> data) {
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
                convertView = LayoutInflater.from(ChartListActivity.this).inflate(R.layout.item_chart_list, null);
                holder.text_content = (TextView) convertView.findViewById(R.id.text_content);
                holder.text_update_time = (TextView) convertView.findViewById(R.id.text_update_time);
                holder.img_header = (SimpleDraweeView) convertView.findViewById(R.id.img_header);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            YyMobileUserLetter yyMobileUserLetter = data.get(position);
            holder.img_header.setImageURI(Uri.parse(yyMobileUserLetter.getPicPath()));
            holder.text_update_time.setText(yyMobileUserLetter.getUpdateDate());
            holder.text_content.setText(yyMobileUserLetter.getContent());
            return convertView;
        }
    }

    class ViewHolder {
        SimpleDraweeView img_header;
        TextView text_update_time;
        TextView text_content;

    }

}
