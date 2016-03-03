package com.zl.app.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.BaseFragment;
import com.zl.app.R;
import com.zl.app.data.news.model.YyMobileUserComment;
import com.zl.app.fragment.FragmentSite;
import com.zl.app.util.RequestURL;
import com.zl.app.util.StringUtil;

import java.util.List;

/**
 * Created by fengxiang on 2016/2/17.
 */
public class SiteMessageAdapter extends RecyclerView.Adapter<SiteMessageAdapter.ViewHolder> {

    List<YyMobileUserComment> data;
    BaseFragment fragment;

    public SiteMessageAdapter(BaseFragment fragment, List<YyMobileUserComment> data) {
        this.fragment = fragment;
        this.data = data;
    }

    @Override
    public SiteMessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_site_message, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(SiteMessageAdapter.ViewHolder holder, int position) {
        final YyMobileUserComment comment = data.get(position);
        Uri uri = Uri.parse(RequestURL.SERVER + comment.getPicPath());
        holder.draweeView.setImageURI(uri);
        holder.text_user_name.setText(comment.getFormusername());
        holder.text_create_time.setText(comment.getCreateDate());
        holder.text_message.setText(comment.getContent());
        if (!StringUtil.isEmpty(comment.getYycontent())) {
            holder.yyll.setVisibility(View.VISIBLE);
            holder.text_yy_user_name.setText(comment.getYyusername());
            holder.text_yy_create_time.setText(comment.getYydate());
            holder.text_yy_message.setText(comment.getYycontent());
        } else {
            holder.yyll.setVisibility(View.GONE);
        }
        holder.btn_yinyong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentSite)fragment).openYyPanel(comment);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView draweeView;
        public TextView text_user_name;
        public TextView text_create_time;
        public TextView btn_yinyong;
        public TextView text_message;

        //引用内容
        public LinearLayout yyll;
        public TextView text_yy_user_name;
        public TextView text_yy_create_time;
        public TextView text_yy_message;

        public ViewHolder(View view) {
            super(view);
            draweeView = (SimpleDraweeView) view.findViewById(R.id.img_user_header);
            text_user_name = (TextView) view.findViewById(R.id.text_user_name);
            text_create_time = (TextView) view.findViewById(R.id.text_create_time);
            btn_yinyong = (TextView) view.findViewById(R.id.btn_yinyong);
            text_message = (TextView) view.findViewById(R.id.text_message);

            text_yy_user_name = (TextView) view.findViewById(R.id.text_yy_user_name);
            text_yy_create_time = (TextView) view.findViewById(R.id.text_yy_create_time);
            text_yy_message = (TextView) view.findViewById(R.id.text_yy_message);
            yyll = (LinearLayout) view.findViewById(R.id.yyll);

        }
    }
}
