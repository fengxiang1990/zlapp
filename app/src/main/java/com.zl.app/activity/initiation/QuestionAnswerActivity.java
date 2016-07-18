package com.zl.app.activity.initiation;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.zl.app.R;
import com.zl.app.activity.org.WebDetailActivity;
import com.zl.app.activity.user.LoginActivity_;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.InitiationService;
import com.zl.app.data.model.initiation.YyMobileQuestion;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.loadingviewfinal.HeaderAndFooterRecyclerViewAdapter;
import cn.finalteam.loadingviewfinal.OnLoadMoreListener;
import cn.finalteam.loadingviewfinal.RecyclerViewFinal;
import cn.finalteam.loadingviewfinal.SwipeRefreshLayoutFinal;

/**
 * Created by fxa on 2016/7/16.
 */

@EActivity(R.layout.fragment_initiation)
public class QuestionAnswerActivity extends BaseActivityWithToolBar implements SwipeRefreshLayout.OnRefreshListener
        , OnLoadMoreListener {

    String tag = "QuestionAnswerActivity";

    @ViewById
    SwipeRefreshLayoutFinal swipe;

    @ViewById
    RecyclerViewFinal recyclerView;

    @SystemService
    LayoutInflater inflate;

    LinearLayoutManager layoutManager;
    QuestionAnswerActivity.MyAdapter adapter;

    @AfterViews
    void afterViews() {
        setTitle("咨路问答");
        setBtnLeft1Enable(true);
        setBtnRight1Enable(true);
        setBtnRight1ImageResource(R.mipmap.ic_send_question);
        swipe.setOnRefreshListener(this);
        recyclerView.setOnLoadMoreListener(this);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasLoadMore(true);
        adapter = new QuestionAnswerActivity.MyAdapter();
        recyclerView.setAdapter(adapter);
        uid = AppConfig.getUid(AppManager.getPreferences());
        initiationService = new InitiationService();
        loadData();
        recyclerView.setOnItemClickListener(new HeaderAndFooterRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {
                YyMobileQuestion ziluNews = data.get(position);
                Intent intent = new Intent(QuestionAnswerActivity.this, WebDetailActivity.class);
                intent.putExtra("url", ziluNews.getUrl());
                intent.putExtra("title", ziluNews.getHeadline());
                intent.putExtra("questionId", ziluNews.getQuestionId());
                intent.putExtra("showans", true);
                startActivity(intent);
            }
        });
    }

    int pageNo = 1;
    int pageSize = 20;
    InitiationService initiationService;
    String uid;
    boolean isLoadMore = false;
    List<YyMobileQuestion> data = new ArrayList<>();

    @Override
    protected void onBtnRight1Click() {
        super.onBtnRight1Click();
        if (AppConfig.isLogin(preference)) {
            Intent intent = new Intent(QuestionAnswerActivity.this, SendQuestionActivity_.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(QuestionAnswerActivity.this, LoginActivity_.class);
            startActivity(intent);
        }

    }

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
        initiationService.getQuestionList(pageNo, pageSize, uid, new DefaultResponseListener<BaseResponse<List<YyMobileQuestion>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileQuestion>> response) {
                swipe.onRefreshComplete();
                recyclerView.onLoadMoreComplete();
                if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                    if (!isLoadMore) {
                        data.clear();
                    }
                    data.addAll(response.getResult());
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtil.show(QuestionAnswerActivity.this, response.getMessage());
                }

            }

            @Override
            public void onError(VolleyError error) {
                swipe.onRefreshComplete();
                recyclerView.onLoadMoreComplete();
            }
        });
    }

    class MyAdapter extends RecyclerView.Adapter<QuestionAnswerActivity.MyAdapter.MyViewHolde> {


        @Override
        public QuestionAnswerActivity.MyAdapter.MyViewHolde onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflate.inflate(R.layout.item_initiation_qa, parent, false);
            MyViewHolde holder = new MyViewHolde(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.MyViewHolde holder, int position) {
            YyMobileQuestion ziluNews = data.get(position);
            holder.text_reply_count.setText(ziluNews.getReplyNum() + "");
            holder.text_content.setText(ziluNews.getHeadline());
            holder.text_see_count.setText(ziluNews.getClickNo() + "");
            holder.text_time.setText(ziluNews.getCreateDateFormat());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolde extends RecyclerView.ViewHolder {

            TextView text_content, text_see_count, text_time, text_reply_count;

            public MyViewHolde(View itemView) {
                super(itemView);
                text_content = (TextView) itemView.findViewById(R.id.text_content);
                text_see_count = (TextView) itemView.findViewById(R.id.text_see_count);
                text_time = (TextView) itemView.findViewById(R.id.text_time);
                text_reply_count = (TextView) itemView.findViewById(R.id.text_reply_count);
            }
        }
    }

}
