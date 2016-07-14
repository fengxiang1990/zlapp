package com.zl.app.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.BaseFragment;
import com.zl.app.R;
import com.zl.app.activity.org.WebDetailActivity;
import com.zl.app.data.InitiationService;
import com.zl.app.data.model.initiation.YyMobileZiluNews;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.loadingviewfinal.HeaderAndFooterRecyclerViewAdapter;
import cn.finalteam.loadingviewfinal.OnLoadMoreListener;
import cn.finalteam.loadingviewfinal.RecyclerViewFinal;
import cn.finalteam.loadingviewfinal.SwipeRefreshLayoutFinal;

/**
 * 启蒙
 * Created by fengxiang on 2016/3/28.
 */
@EFragment(R.layout.fragment_initiation)
public class FragmentInitiation extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener
        , OnLoadMoreListener {

    String tag = "FragmentInitiation";

    @ViewById
    SwipeRefreshLayoutFinal swipe;

    @ViewById
    RecyclerViewFinal recyclerView;

    @SystemService
    LayoutInflater inflate;

    LinearLayoutManager layoutManager;
    MyAdapter adapter;

    @AfterViews
    void afterViews() {
        swipe.setOnRefreshListener(this);
        recyclerView.setOnLoadMoreListener(this);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasLoadMore(true);
//        recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL
//        ,10,getResources().getColor(R.color.black)));
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
        uid = AppConfig.getUid(AppManager.getPreferences());
        initiationService = new InitiationService();
        loadData();
        recyclerView.setOnItemClickListener(new HeaderAndFooterRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {
                YyMobileZiluNews ziluNews = data.get(position);
                Intent intent = new Intent(getActivity(), WebDetailActivity.class);
                intent.putExtra("url", ziluNews.getUrl());
                intent.putExtra("title", ziluNews.getHeadline());
                startActivity(intent);
            }
        });
    }

    int pageNo = 1;
    int pageSize = 20;
    InitiationService initiationService;
    String uid;
    boolean isLoadMore = false;
    List<YyMobileZiluNews> data = new ArrayList<>();

    @Override
    public void onRefresh() {
        pageNo = 1;
        isLoadMore = false;
        loadData();
    }

    @Override
    public void loadMore() {
        pageNo += 1;
        isLoadMore = true;
        loadData();
    }

    void loadData() {
        swipe.setRefreshing(true);
        initiationService.getNewsList(pageNo, pageSize, uid, new DefaultResponseListener<BaseResponse<List<YyMobileZiluNews>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileZiluNews>> response) {
                swipe.onRefreshComplete();
                recyclerView.onLoadMoreComplete();
                if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                    if (!isLoadMore) {
                        data.clear();
                    }
                    data.addAll(response.getResult());
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtil.show(getActivity(), response.getMessage());
                }

            }

            @Override
            public void onError(VolleyError error) {
                swipe.onRefreshComplete();
                recyclerView.onLoadMoreComplete();
            }
        });
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolde> {


        @Override
        public MyViewHolde onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflate.inflate(R.layout.item_initiation, parent, false);
            MyViewHolde holder = new MyViewHolde(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolde holder, int position) {
            YyMobileZiluNews ziluNews = data.get(position);
            holder.text_title.setText(ziluNews.getHeadline());
            holder.text_content.setText(ziluNews.getZhaiyao());
            holder.text_see_count.setText(ziluNews.getClickNo() + "");
            holder.text_time.setText(ziluNews.getCreateDateFormat());
            holder.simpleDraweeView.setImageURI(Uri.parse(ziluNews.getPicPath()));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolde extends RecyclerView.ViewHolder {

            TextView text_title, text_content, text_see_count, text_time;
            SimpleDraweeView simpleDraweeView;

            public MyViewHolde(View itemView) {
                super(itemView);
                text_title = (TextView) itemView.findViewById(R.id.text_title);
                text_content = (TextView) itemView.findViewById(R.id.text_content);
                text_see_count = (TextView) itemView.findViewById(R.id.text_see_count);
                text_time = (TextView) itemView.findViewById(R.id.text_time);
                simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.simpleDraweeView);
            }
        }
    }
}
