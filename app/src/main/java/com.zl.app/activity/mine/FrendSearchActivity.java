package com.zl.app.activity.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
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
import com.zl.app.activity.activities.HisActivitiesActivity;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.mine.MineService;
import com.zl.app.data.mine.MineServiceImpl;
import com.zl.app.data.model.user.YyMobileUserFans;
import com.zl.app.util.AppConfig;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fxa on 2016/5/30.
 */
public class FrendSearchActivity extends BaseActivityWithToolBar {

    ListView listView;
    MineService mineService;
    String uid;
    MyAdapter adapter;
    List<YyMobileUserFans> data;
    String keyword = " ";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frends);
        setTitle("搜索结果");
        setBtnLeft1Enable(true);
        setSearchTitleViewEnable(true);
        editSearch.setHint("搜索朋友");
        listView = (ListView) findViewById(R.id.listView);
        mineService = new MineServiceImpl();
        data = new ArrayList<YyMobileUserFans>();
        adapter = new MyAdapter(data);
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
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
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
                YyMobileUserFans fans = data.get(position);
                Intent intent = new Intent(FrendSearchActivity.this, HisActivitiesActivity.class);
                intent.putExtra("userId", fans.getPersonId());
                intent.putExtra("userName", fans.getPersonName());
                startActivity(intent);
            }
        });

    }

    void loadData() {
        mineService.searchFrends(uid,keyword, new DefaultResponseListener<BaseResponse<YyMobileUserFans>>() {
            @Override
            public void onSuccess(BaseResponse<YyMobileUserFans> response) {
                if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                    data.clear();
                    data.add(response.getResult());
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtil.show(FrendSearchActivity.this, response.getMessage());
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
