package com.zl.app.activity.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.R;
import com.zl.app.activity.org.OrgWeiSiteActivity;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.mine.MineService;
import com.zl.app.data.mine.MineServiceImpl;
import com.zl.app.model.customer.YyMobileCompanyGrade;
import com.zl.app.model.customer.YyMobileReservation;
import com.zl.app.util.AppConfig;
import com.zl.app.util.RequestURL;
import com.zl.app.util.StringUtil;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import org.androidannotations.annotations.App;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengxiang on 2016/4/15.
 */
public class MyYuyueActivity extends BaseActivityWithToolBar{

    ListView listView;

    MineService mineService;
    String uid;
    int pageNo = 1;
    int pageSize = 100;
    MyAdapter adapter;
    List<YyMobileReservation> data;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_yuyue);
        listView = (ListView) findViewById(R.id.listView);
        setBtnLeft1Enable(true);
        setTitle("我的预约");
        mineService = new MineServiceImpl();
        data = new ArrayList<YyMobileReservation>();
        adapter = new MyAdapter(data);
        listView.setAdapter(adapter);
        uid = AppConfig.getUid(preference);
        mineService.getMyYuyue(uid, pageNo, pageSize, new DefaultResponseListener<BaseResponse<List<YyMobileReservation>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileReservation>> response) {
                if(response.getStatus().equals(AppConfig.HTTP_OK)){
                   data.addAll(response.getResult());
                    adapter.notifyDataSetChanged();
                }else{
                    ToastUtil.show(MyYuyueActivity.this,response.getMessage());
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }


    class MyAdapter extends BaseAdapter {

        List<YyMobileReservation> data;

        public MyAdapter(List<YyMobileReservation> data) {
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
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_my_yuyue, null);
                holder = new ViewHolder();
                holder.img_org = (SimpleDraweeView) convertView.findViewById(R.id.img_org);
                holder.text_org_name = (TextView) convertView.findViewById(R.id.text_org_name);
                holder.text_content = (TextView) convertView.findViewById(R.id.text_content);
                holder.text_yuyue_time = (TextView) convertView.findViewById(R.id.text_yuyue_time);
                holder.text_reply = (TextView) convertView.findViewById(R.id.text_reply);
                holder.text_reply_time = (TextView) convertView.findViewById(R.id.text_reply_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final YyMobileReservation reservation = data.get(position);
            if (reservation != null) {
                if (StringUtil.isEmpty(reservation.getPicPath())) {
                    holder.img_org.setVisibility(View.GONE);
                } else {
                    holder.img_org.setVisibility(View.VISIBLE);
                    Uri uri = Uri.parse(reservation.getPicPath());
                    holder.img_org.setImageURI(uri);
                }
                holder.text_org_name.setText(reservation.getCompanyname());
                holder.text_content.setText(reservation.getContent());
                holder.text_yuyue_time.setText(reservation.getCreateDate());
                holder.text_reply.setText(reservation.getReply());
                holder.text_reply_time.setText(reservation.getModifyDate());

                holder.text_org_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Intent intent =new Intent(MyYuyueActivity.this, OrgWeiSiteActivity.class);
                        ///intent.putExtra("companyId",reservation.get)

                    }
                });
            }
            return convertView;
        }

    }

    class ViewHolder {
        SimpleDraweeView img_org;
        TextView text_org_name;
        TextView text_content;
        TextView text_yuyue_time;
        TextView text_reply;
        TextView text_reply_time;
    }
}
