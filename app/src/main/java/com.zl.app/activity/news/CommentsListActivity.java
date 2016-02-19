package com.zl.app.activity.news;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.VolleyError;
import com.zl.app.R;
import com.zl.app.adapter.CommentAdapter;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.news.NewsService;
import com.zl.app.data.news.NewsServiceImpl;
import com.zl.app.data.news.model.YyMobileNewsComment;
import com.zl.app.util.AppConfig;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengxiang on 2016/2/19.
 */
@EActivity(R.layout.activity_comment_list)
public class CommentsListActivity extends BaseActivityWithToolBar {

    String tag = "CommentsListActivity";
    Context context;

    @ViewById(R.id.recyclerViewComments)
    RecyclerView recyclerViewComments;

    LinearLayoutManager layoutManager;
    CommentAdapter commentAdapter;
    List<YyMobileNewsComment> data;
    NewsService newsService;
    String uid;
    String newsId;
    public int pageNo = 1;
    public int pageSize = 10;

    @AfterViews
    void afterViews() {
        setBtnLeft1Enable(true);
        newsId = getIntent().getStringExtra("NEWS_ID");
        uid = AppConfig.getUid(preference);
        context = CommentsListActivity.this;
        newsService = new NewsServiceImpl();
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewComments.setLayoutManager(layoutManager);
        data = new ArrayList<YyMobileNewsComment>();
        commentAdapter = new CommentAdapter(this, data);
        recyclerViewComments.setAdapter(commentAdapter);
        loadData(pageNo, pageSize);
        //test submit comment
        newsService.submitComment(uid, newsId, null, null, null, "test 测试评论", new DefaultResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                Log.e(tag,response.toString());
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    public void loadData(int pageNo, int pageSize) {
        newsService.getNewsComments(uid, newsId, pageNo, pageSize, new DefaultResponseListener<BaseResponse<List<YyMobileNewsComment>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileNewsComment>> response) {
                List<YyMobileNewsComment> list = response.getResult();
                Log.e(tag,"uid:"+uid+"  newsId:"+newsId);
                data.clear();
                data.addAll(list);
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }
}
