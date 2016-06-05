package com.zl.app.activity.mine;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.zl.app.data.model.customer.YyMobileSi;
import com.zl.app.util.AppConfig;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统通知
 * Created by fxa on 2016/6/5.
 */
public class SystemNoticeActivity extends BaseActivityWithToolBar {

    String tag = "SystemNoticeActivity";
    ListView listView;
    MyAdapter adapter;
    List<YyMobileSi> data;

    int pageNo = 1;
    int pageSize= 1000000;
    String uid;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_notice);
        setTitle("系统通知");
        setBtnLeft1Enable(true);
        uid = AppConfig.getUid(preference);
        listView = (ListView) findViewById(R.id.listView);
        data = new ArrayList<>();
        adapter = new MyAdapter(data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               final  YyMobileSi yyMobileSi = data.get(position);
                if(yyMobileSi.getType() == 1){
                    new AlertDialog.Builder(SystemNoticeActivity.this)
                            .setTitle("系统通知")
                            .setMessage("有机构邀请您成为老师")
                            .setPositiveButton("接受", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(final DialogInterface dialog, int which) {
                                    new MineServiceImpl().acceptTeacher(uid,yyMobileSi.getMainId(),2, new DefaultResponseListener<BaseResponse>() {
                                        @Override
                                        public void onSuccess(BaseResponse response) {
                                            if (response != null) {
                                                ToastUtil.show(SystemNoticeActivity.this, response.getMessage());
                                                dialog.dismiss();
                                            }
                                        }

                                        @Override
                                        public void onError(VolleyError error) {
                                            ToastUtil.show(SystemNoticeActivity.this, error.getMessage());
                                        }
                                    });
                                }
                            }).setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {
                            new MineServiceImpl().acceptTeacher(uid,yyMobileSi.getMainId(),3, new DefaultResponseListener<BaseResponse>() {
                                @Override
                                public void onSuccess(BaseResponse response) {
                                    if (response != null) {
                                        ToastUtil.show(SystemNoticeActivity.this, response.getMessage());
                                        dialog.dismiss();
                                    }
                                }

                                @Override
                                public void onError(VolleyError error) {
                                    ToastUtil.show(SystemNoticeActivity.this, error.getMessage());
                                }
                            });
                        }
                    })
                            .show();
                }
            }
        });
        new MineServiceImpl().getSystemNotice(AppConfig.getUid(preference), pageNo, pageSize, new DefaultResponseListener<BaseResponse<List<YyMobileSi>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileSi>> response) {
                if(response!=null && response.getResult()!=null){
                    data.clear();
                    data.addAll(response.getResult());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }


    class MyAdapter extends BaseAdapter {
        List<YyMobileSi> data;

        public MyAdapter(List<YyMobileSi> data) {
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
                convertView = LayoutInflater.from(SystemNoticeActivity.this).inflate(R.layout.item_system_notice, null);
                holder.text_content = (TextView) convertView.findViewById(R.id.text_content);
                holder.text_update_time = (TextView) convertView.findViewById(R.id.text_update_time);
                holder.img_header = (SimpleDraweeView) convertView.findViewById(R.id.img_header);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            YyMobileSi yyMobileSi = data.get(position);
            holder.text_update_time.setText(yyMobileSi.getCreateDate());
            holder.text_content.setText(yyMobileSi.getContent());
            return convertView;
        }
    }


    class ViewHolder {
        SimpleDraweeView img_header;
        TextView text_update_time;
        TextView text_content;

    }
}
