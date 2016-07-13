package com.zl.app.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zl.app.BaseFragment;
import com.zl.app.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;

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
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void loadMore() {

    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolde> {


        @Override
        public MyViewHolde onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflate.inflate(R.layout.item_initiation, null);
            MyViewHolde holder = new MyViewHolde(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolde holder, int position) {
            holder.text_title.setText("title" + position);
            holder.text_content.setText("content" + position);
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        class MyViewHolde extends RecyclerView.ViewHolder {

            TextView text_title, text_content;

            public MyViewHolde(View itemView) {
                super(itemView);
                text_title = (TextView) itemView.findViewById(R.id.text_title);
                text_content = (TextView) itemView.findViewById(R.id.text_content);
            }
        }
    }
}
