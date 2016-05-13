package com.zl.app.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.R;
import com.zl.app.model.activity.YyMobileActivity;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;

import java.util.List;

/**
 * Created by fengxiang on 2016/5/13.
 */
public class ActivityAdapter extends BaseAdapter {

    List<YyMobileActivity> data;
    OnItemBtnClickListener listener;
    Context context;

    public ActivityAdapter(Context context, List<YyMobileActivity> data, OnItemBtnClickListener listener) {
        this.data = data;
        this.context = context;
        this.listener = listener;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_activity, null);
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
                    holder.text_status.setTextColor(context.getResources().getColor(R.color.red));
                    break;
                case 3:
                    statusStr = "往期活动";
                    holder.text_status.setTextColor(context.getResources().getColor(R.color.gray));
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
                    listener.onDelete(activity);
                }
            });
            holder.img_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEdit(activity);
                }
            });
        }

        return convertView;
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

    public interface OnItemBtnClickListener {
        void onEdit(YyMobileActivity activity);

        void onDelete(YyMobileActivity activity);
    }
}



