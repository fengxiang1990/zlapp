package com.zl.app.activity.mine;

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
import com.zl.app.data.mine.MineServiceImpl;
import com.zl.app.data.model.user.YyMobileStudent;
import com.zl.app.util.AppConfig;
import com.zl.app.util.GsonUtil;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengxiang on 2016/4/18.
 */
public class MyChildrenActivity extends BaseActivityWithToolBar {

    ListView listView;
    List<YyMobileStudent> data;
    MyAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_children);
        setTitle("我的宝贝");
        setBtnLeft1Enable(true);
        setBtnRight1Enable(true);
        setBtnRight1ImageResource(R.mipmap.addchild_icon);
        listView = (ListView) findViewById(R.id.listView2);
        data = new ArrayList<YyMobileStudent>();
        adapter = new MyAdapter(data);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                YyMobileStudent student = data.get(position);
                Intent intent = new Intent(MyChildrenActivity.this, EditChildActivity.class);
                intent.putExtra("child", GsonUtil.gson.toJson(student));
                startActivity(intent);
            }
        });
        new MineServiceImpl().getBabies(AppConfig.getUid(preference), new DefaultResponseListener<BaseResponse<List<YyMobileStudent>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileStudent>> response) {
                if (response != null) {
                    if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                        data.addAll(response.getResult() == null ? new ArrayList<YyMobileStudent>() : response.getResult());
                        adapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.show(getApplicationContext(), response.getMessage());
                    }
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });

    }

    @Override
    protected void onBtnRight1Click() {
        super.onBtnRight1Click();
        Intent intent = new Intent(MyChildrenActivity.this, AddChildActivity.class);
        startActivity(intent);
    }

    class MyAdapter extends BaseAdapter {

        List<YyMobileStudent> data;

        public MyAdapter(List<YyMobileStudent> data) {
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
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_my_baby, null);
                holder = new ViewHolder();
                holder.img_child = (SimpleDraweeView) convertView.findViewById(R.id.img_child);
                holder.text_name = (TextView) convertView.findViewById(R.id.text_name);
                holder.text_id_number = (TextView) convertView.findViewById(R.id.text_id_number);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final YyMobileStudent student = data.get(position);
            if (student != null) {
                Uri uri = Uri.parse(student.getPhoto() == null ? "" : student.getPhoto());
                holder.img_child.setImageURI(uri);
                holder.text_name.setText(student.getName());
                holder.text_id_number.setText(student.getIdCard());
            }
            return convertView;
        }

    }

    class ViewHolder {
        SimpleDraweeView img_child;
        TextView text_name;
        TextView text_id_number;
    }
}
