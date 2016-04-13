package com.zl.app.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.BaseFragment;
import com.zl.app.R;
import com.zl.app.activity.news.NewsDetailActivityWeb_;
import com.zl.app.activity.news.NewsDetailActivity_;
import com.zl.app.data.news.model.YyMobileNews;
import com.zl.app.fragment.FragmentHome;
import com.zl.app.util.RequestURL;
import com.zl.app.util.StringUtil;

import java.util.List;

/**
 * Created by fengxiang on 2016/2/17.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    List<YyMobileNews> data;
    BaseFragment fragment;

    public NewsAdapter(BaseFragment fragment, List<YyMobileNews> data) {
        this.fragment = fragment;
        this.data = data;
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
        final YyMobileNews news = data.get(position);
        if(StringUtil.isEmpty(news.getPicPath())){
            holder.draweeView.setVisibility(View.GONE);
        }else {
            holder.draweeView.setVisibility(View.VISIBLE);
            Uri uri = Uri.parse(RequestURL.SERVER + news.getPicPath());
            holder.draweeView.setImageURI(uri);
        }
        holder.text_title.setText(news.getHeadline());
        holder.text_zhaiyao.setText(news.getZhaiyao());
        holder.text_read_count.setText(String.valueOf(news.getClickNo()));
        holder.text_comment_count.setText(String.valueOf(news.getCommentNo()));
        holder.text_post_time.setText(String.valueOf(news.getCreateDateFormat()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fragment.getActivity(), NewsDetailActivity_.class);
                intent.putExtra("NEWS_ID", news.getUrl());
                fragment.getActivity().startActivity(intent);
            }
        });

        //滚动到底部自动加载

        if(fragment instanceof FragmentHome){
            FragmentHome fragmentHome = (FragmentHome) fragment;
           if(position == data.size()-1 && data.size() >= fragmentHome.pageSize) {
                   fragmentHome.isLoadMore = true;
               fragmentHome.pageNo+=1;
               if(fragmentHome.isSearchNews){
                   fragmentHome.searchNews(fragmentHome.pageNo, fragmentHome.pageSize, fragmentHome.code, fragmentHome.value);
               }else {
                   fragmentHome.loadNews();
               }
           }
        }
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
        public TextView text_title;
        public TextView text_zhaiyao;
        public SimpleDraweeView draweeView;
        public TextView text_read_count;
        public TextView text_comment_count;
        public TextView text_post_time;

        public ViewHolder(View view) {
            super(view);
            text_title = (TextView) view.findViewById(R.id.text_news_title);
            text_zhaiyao = (TextView) view.findViewById(R.id.text_news_zhaiyao);
            draweeView = (SimpleDraweeView) view.findViewById(R.id.pic_news);
            text_read_count = (TextView) view.findViewById(R.id.text_read_count);
            text_comment_count = (TextView) view.findViewById(R.id.text_comment_count);
            text_post_time = (TextView) view.findViewById(R.id.text_post_time);
        }
    }
}
