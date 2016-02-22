package com.zl.app.activity.news;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.VolleyError;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.news.NewsService;
import com.zl.app.data.news.NewsServiceImpl;
import com.zl.app.data.news.model.YyMobileNews;
import com.zl.app.util.AppConfig;
import com.zl.app.util.GsonUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fengxiang on 2016/2/18.
 */

@EActivity(R.layout.activity_news_detail_web)
public class NewsDetailActivityWeb extends BaseActivityWithToolBar {

    String tag = "NewsDetailActivityWeb";
    Context context;

    @ViewById(R.id.webView)
    WebView webView;

    String newsId;
    NewsService newsService;
    String uid;

    String result="";
    @AfterViews
    void afterViews() {
        webView.addJavascriptInterface(this, "jsInterface");
        // 支持javascript
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(false);
        // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(false);
        // 扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);

        // 自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) { // 重写此方法表明点击网页里面的链接还是在当前的web里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });

        setBtnLeft1Enable(true);
        setTextRight1Enable(true);
        setTextRight1Val("无人跟帖");
        setBtnRight1Enable(false);
        setBtnRight1ImageResource(R.mipmap.review_count);
        context = NewsDetailActivityWeb.this;
        uid = AppConfig.getUid(preference);
        newsService = new NewsServiceImpl();
        newsId = getIntent().getStringExtra("NEWS_ID");
        newsService.getNewsDetail(uid, newsId, new DefaultResponseListener<BaseResponse<YyMobileNews>>() {
            @Override
            public void onSuccess(BaseResponse<YyMobileNews> response) {
                String message = response.getMessage();
                Log.e(tag, message);
                YyMobileNews yyMobileNews = response.getResult();
                setTextRight1Val(yyMobileNews.getCommentNo() + "人跟帖");
                String[] strs = message.split(",");
                Map<String, Object> map = new HashMap<String, Object>();
                yyMobileNews.setContent(yyMobileNews.getContent().replaceAll("/upload/attached/", "http://www.ziluedu.cn/upload/attached/"));
                map.put("data", yyMobileNews);
                map.put("isSc", strs[0]);
                map.put("isZan", strs[1]);
                String json = GsonUtil.gson.toJson(map);
                result = json;
                webView.loadUrl("file:///android_asset/page/news_deatil.html");
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    @JavascriptInterface
    public String getResult(){
        return result;
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
