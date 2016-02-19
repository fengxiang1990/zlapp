package com.zl.app.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zl.app.BaseFragment;
import com.zl.app.R;
import com.zl.app.activity.news.NewsDetailActivity_;
import com.zl.app.data.news.model.YyMobileNews;
import com.zl.app.data.news.model.YyMobileNewsComment;

import java.util.List;

/**
 * Created by fengxiang on 2016/2/17.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    List<YyMobileNewsComment> data;
    Activity activity;
    public CommentAdapter(Activity activity, List<YyMobileNewsComment> data) {
        this.activity = activity;
        this.data = data;
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, int position) {
        final YyMobileNewsComment comment = data.get(position);
        holder.mTextView.setText(comment.getContent());
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
        public TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.text_news_title);
        }
    }
}
