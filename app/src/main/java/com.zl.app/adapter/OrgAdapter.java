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
import com.zl.app.activity.news.NewsDetailActivity_;
import com.zl.app.data.news.model.YyMobileNews;
import com.zl.app.fragment.FragmentFind;
import com.zl.app.fragment.FragmentHome;
import com.zl.app.model.customer.YyMobileCompany;
import com.zl.app.util.RequestURL;
import com.zl.app.util.StringUtil;

import java.util.List;

/**
 * Created by fengxiang on 2016/2/17.
 */
public class OrgAdapter extends RecyclerView.Adapter<OrgAdapter.ViewHolder> {

    List<YyMobileCompany> data;
    BaseFragment fragment;

    public OrgAdapter(BaseFragment fragment, List<YyMobileCompany> data) {
        this.fragment = fragment;
        this.data = data;
    }

    @Override
    public OrgAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_org, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(OrgAdapter.ViewHolder holder, int position) {
        final YyMobileCompany org = data.get(position);
        if(StringUtil.isEmpty(org.getPicPath())){
            holder.img_org.setVisibility(View.GONE);
        }else {
            holder.img_org.setVisibility(View.VISIBLE);
            Uri uri = Uri.parse(RequestURL.SERVER + org.getPicPath());
            holder.img_org.setImageURI(uri);
        }
        holder.text_org_name.setText(org.getCompanyname());
        holder.text_score.setText(org.getGrade()+"");
        holder.text_type.setText(org.getTypeName());
        holder.text_area1.setText(org.getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //滚动到底部自动加载

        if(fragment instanceof FragmentFind){
            /**
            FragmentFind fragmentHome = (FragmentFind) fragment;
           if(position == data.size()-1 && data.size() >= fragmentHome.pageSize) {
                   fragmentHome.isLoadMore = true;
               fragmentHome.pageNo+=1;
               if(fragmentHome.isSearchNews){
                   fragmentHome.searchNews(fragmentHome.pageNo, fragmentHome.pageSize, fragmentHome.code, fragmentHome.value);
               }else {
                   fragmentHome.loadNews();
               }
           }
             **/
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
        public TextView text_org_name;
        public TextView text_score;
        public SimpleDraweeView img_org;
        public TextView text_type;
        public TextView text_area1;
        public TextView text_area2;

        public ViewHolder(View view) {
            super(view);
            text_org_name = (TextView) view.findViewById(R.id.text_org_name);
            text_score = (TextView) view.findViewById(R.id.text_score);
            img_org = (SimpleDraweeView) view.findViewById(R.id.img_org);
            text_type = (TextView) view.findViewById(R.id.text_type);
            text_area1 = (TextView) view.findViewById(R.id.text_area1);
            text_area2 = (TextView) view.findViewById(R.id.text_area2);
        }
    }
}
