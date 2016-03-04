package com.zl.app.activity.news;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.news.NewsService;
import com.zl.app.data.news.NewsServiceImpl;
import com.zl.app.data.news.model.YyMobileNews;
import com.zl.app.data.news.model.YyMobileNewsComment;
import com.zl.app.util.AppConfig;
import com.zl.app.util.RequestURL;
import com.zl.app.util.StringUtil;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import zhou.widget.RichText;

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
    RichText text_content;

    @ViewById(R.id.btn_zan)
    ImageView btn_zan;

    @ViewById(R.id.btn_sc)
    ImageView btn_sc;

    @ViewById(R.id.btn_submit)
    TextView btn_submit;

    @ViewById(R.id.edit_comment)
    EditText edit_comment;

    @ViewById(R.id.text_read_count)
    TextView text_read_count;

    @ViewById(R.id.text_comment_count)
    TextView text_comment_count;

    @ViewById(R.id.text_dz_count)
    TextView text_dz_count;

    @ViewById(R.id.text_sc_count)
    TextView text_sc_count;

    @ViewById(R.id.ll_coments)
    LinearLayout ll_coments;

    @SystemService
    LayoutInflater layoutInflater;

    String newsId;
    NewsService newsService;
    String uid;


    @AfterViews
    void afterViews() {
        setBtnLeft1Enable(true);
        setTextRight1Enable(true);
        setTextRight1Val("0人评论");
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
                text_read_count.setText(String.valueOf(yyMobileNews.getClickNo()));
                text_comment_count.setText(String.valueOf(yyMobileNews.getCommentNo()));
                text_dz_count.setText(String.valueOf(yyMobileNews.getGoodNo()));
                text_sc_count.setText(String.valueOf(yyMobileNews.getFavoriteNo()));
                text_title.setText(yyMobileNews.getHeadline());
                text_content.setRichText(yyMobileNews.getContent().replaceAll("/upload/attached/", "http://www.ziluedu.cn/upload/attached/"));
                setTextRight1Val(yyMobileNews.getCommentNo() + "人评论");
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

        newsService.getNewsComments(uid, newsId, 1, 5, new DefaultResponseListener<BaseResponse<List<YyMobileNewsComment>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileNewsComment>> response) {
                List<YyMobileNewsComment> list = response.getResult();
                Log.e(tag, "uid:" + uid + "  newsId:" + newsId);
                if (list != null) {
                    for (YyMobileNewsComment comment : list) {
                        View view = layoutInflater.inflate(R.layout.item_site_message, null);
                        SimpleDraweeView draweeView = (SimpleDraweeView) view.findViewById(R.id.img_user_header);
                        TextView text_user_name = (TextView) view.findViewById(R.id.text_user_name);
                        TextView text_create_time = (TextView) view.findViewById(R.id.text_create_time);
                        TextView btn_yinyong = (TextView) view.findViewById(R.id.btn_yinyong);
                        TextView text_message = (TextView) view.findViewById(R.id.text_message);
                        TextView text_yy_user_name = (TextView) view.findViewById(R.id.text_yy_user_name);
                        TextView text_yy_create_time = (TextView) view.findViewById(R.id.text_yy_create_time);
                        TextView text_yy_message = (TextView) view.findViewById(R.id.text_yy_message);
                        LinearLayout yyll = (LinearLayout) view.findViewById(R.id.yyll);
                        Uri uri = Uri.parse(RequestURL.SERVER + comment.getPicPath());
                        draweeView.setImageURI(uri);
                        btn_yinyong.setVisibility(View.GONE);
                        text_user_name.setText(comment.getUsername());
                        text_create_time.setText(comment.getCreateDate());
                        text_message.setText(comment.getContent());
                        if (!StringUtil.isEmpty(comment.getYycontent())) {
                            yyll.setVisibility(View.VISIBLE);
                            text_yy_user_name.setText(comment.getYyusername());
                            text_yy_create_time.setText(comment.getYydate());
                            text_yy_message.setText(comment.getYycontent());
                        } else {
                            yyll.setVisibility(View.GONE);
                        }
                        ll_coments.addView(view);
                    }
                    TextView textView = new TextView(context);
                    textView.setText("更多评论");
                    textView.setGravity(Gravity.CENTER);
                    textView.setClickable(true);
                    textView.setPadding(0,15,0,15);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.gravity = Gravity.CENTER;
                    ll_coments.addView(textView,layoutParams);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, CommentsListActivity_.class);
                            intent.putExtra("NEWS_ID", newsId);
                            startActivity(intent);
                        }
                    });
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
