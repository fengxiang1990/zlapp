package com.zl.app.activity.news;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.news.NewsService;
import com.zl.app.data.news.NewsServiceImpl;
import com.zl.app.data.news.model.YyMobileNews;
import com.zl.app.util.AppConfig;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by fengxiang on 2016/2/18.
 */

@EActivity(R.layout.activity_news_detail)
public class NewsDetailActivity extends BaseActivityWithToolBar {

    String tag = "NewsDetailActivity";
    Context context;

    @ViewById(R.id.text_title)
    TextView text_title;

    @ViewById(R.id.text_content)
    TextView text_content;

    String newsId;
    NewsService newsService;
    String uid;

    @AfterViews
    void afterViews() {
        setBtnLeft1Enable(true);
        setTextRight1Enable(true);
        setTextRight1Val("查看评论");
        context = NewsDetailActivity.this;
        uid = AppConfig.getUid(preference);
        newsService = new NewsServiceImpl();
        newsId = getIntent().getStringExtra("NEWS_ID");
        newsService.getNewsDetail(uid, newsId, new DefaultResponseListener<BaseResponse<YyMobileNews>>() {
            @Override
            public void onSuccess(BaseResponse<YyMobileNews> response) {
                YyMobileNews yyMobileNews = response.getResult();
                text_title.setText(yyMobileNews.getHeadline());
               // setTitle(yyMobileNews.getHeadline());
                text_content.setText(Html.fromHtml(yyMobileNews.getContent()));
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    @Override
    protected void onTextRight1Click() {
        super.onTextRight1Click();
        Intent intent = new Intent(this,CommentsListActivity_.class);
        intent.putExtra("NEWS_ID",newsId);
        startActivity(intent);
    }
}
