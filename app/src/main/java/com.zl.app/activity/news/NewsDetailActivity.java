package com.zl.app.activity.news;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
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
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.sufficientlysecure.htmltextview.HtmlTextView;

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
    HtmlTextView text_content;

    @ViewById(R.id.btn_zan)
    ImageView btn_zan;

    @ViewById(R.id.btn_sc)
    ImageView btn_sc;

    @ViewById(R.id.btn_submit)
    TextView btn_submit;

    @ViewById(R.id.edit_comment)
    EditText edit_comment;

    String newsId;
    NewsService newsService;
    String uid;

    @AfterViews
    void afterViews() {
        setBtnLeft1Enable(true);
        setTextRight1Enable(true);
        setTextRight1Val("无人跟帖");
        setBtnRight1Enable(false);
        setBtnRight1ImageResource(R.mipmap.review_count);
        context = NewsDetailActivity.this;
        uid = AppConfig.getUid(preference);
        newsService = new NewsServiceImpl();
        newsId = getIntent().getStringExtra("NEWS_ID");
        newsService.getNewsDetail(uid, newsId, new DefaultResponseListener<BaseResponse<YyMobileNews>>() {
            @Override
            public void onSuccess(BaseResponse<YyMobileNews> response) {
                String message = response.getMessage();
                Log.e(tag, message);
                final YyMobileNews yyMobileNews = response.getResult();
                text_title.setText(yyMobileNews.getHeadline());
                text_content.setHtmlFromString(yyMobileNews.getContent().replaceAll("/upload/attached/", "http://www.ziluedu.cn/upload/attached/"), new HtmlTextView.RemoteImageGetter());
                setTextRight1Val(yyMobileNews.getCommentNo() + "人跟帖");
                String[] strs = message.split(",");
                //已经收藏
                if (strs[0].equals("2")) {
                    btn_sc.setImageResource(R.mipmap.item_recomm);
                }
                //已经点赞
                if (strs[1].equals("2")) {
                    btn_zan.setImageResource(R.mipmap.item_support);
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    @Click(R.id.btn_zan)
    void zan() {
        newsService.submitGood(uid, newsId, new DefaultResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                Log.e(tag, response.toString());
                ToastUtil.show(context, response.getResult().toString());
                if (response.getResult().toString().equals("点赞成功")) {
                    btn_zan.setImageResource(R.mipmap.item_support);
                } else {
                    btn_zan.setImageResource(R.mipmap.item_nosupport);
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }


    @Click(R.id.btn_sc)
    void sc() {
        newsService.favorite(uid, newsId, new DefaultResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                Log.e(tag, response.toString());
                ToastUtil.show(context, response.getResult().toString());
                if (response.getResult().toString().equals("收藏成功")) {
                    btn_sc.setImageResource(R.mipmap.item_recomm);
                } else {
                    btn_sc.setImageResource(R.mipmap.item_norecomm);
                }
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
                ToastUtil.show(context, "跟帖成功");
                edit_comment.setText("");
            }

            @Override
            public void onError(VolleyError error) {

            }
        });

    }

    @Override
    protected void onBtnRight1Click() {
        super.onBtnRight1Click();
        Intent intent = new Intent(this, CommentsListActivity_.class);
        intent.putExtra("NEWS_ID", newsId);
        startActivity(intent);
    }

    @Override
    protected void onTextRight1Click() {
        super.onTextRight1Click();
        Intent intent = new Intent(this, CommentsListActivity_.class);
        intent.putExtra("NEWS_ID", newsId);
        startActivity(intent);
    }

}
