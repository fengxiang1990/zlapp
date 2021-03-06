package com.zl.app.activity.news;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.zl.app.R;
import com.zl.app.adapter.CommentAdapter;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.news.NewsService;
import com.zl.app.data.news.NewsServiceImpl;
import com.zl.app.data.news.model.YyMobileNewsComment;
import com.zl.app.util.AppConfig;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.view.LoadingDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
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

    @ViewById(R.id.btn_submit)
    TextView btn_submit;

    @ViewById(R.id.edit_comment)
    EditText edit_comment;

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
        //setTextRight1Enable(true);
        //setTextRight1Val("我要评论");
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
    }

    public void loadData(int pageNo, int pageSize) {
        LoadingDialog.getInstance(CommentsListActivity.this).show();
        newsService.getNewsComments(uid, newsId, pageNo, pageSize, new DefaultResponseListener<BaseResponse<List<YyMobileNewsComment>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileNewsComment>> response) {
                List<YyMobileNewsComment> list = response.getResult();
                Log.e(tag, "uid:" + uid + "  newsId:" + newsId);
                data.clear();
                data.addAll(list);
                commentAdapter.notifyDataSetChanged();
                LoadingDialog.getInstance(CommentsListActivity.this).dismiss();
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    @Click(R.id.btn_submit)
    void submitComment() {
        newsService.submitComment(uid, newsId, null, null, null, edit_comment.getText().toString(), new DefaultResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                Log.e(tag, response.toString());
                ToastUtil.show(context, "评论成功");
                pageNo = 1;
                loadData(pageNo, pageSize);
                edit_comment.setText("");
            }

            @Override
            public void onError(VolleyError error) {

            }
        });

    }
}
