package com.zl.app.activity.org;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zl.app.R;
import com.zl.app.activity.initiation.QuestionAnswerActivity_;
import com.zl.app.base.BaseActivityWithToolBar;

/**
 * Created by fengxiang on 2016/5/12.
 */
public class WebDetailActivity extends BaseActivityWithToolBar {

    WebView webView;
    String title;
    String url;
    String cid;
    boolean showshequan = false;
    boolean showqa = false;//是否显示问答入口

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_detail);
        webView = (WebView) findViewById(R.id.webView);
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        cid = getIntent().getStringExtra("cid");
        showshequan = getIntent().getBooleanExtra("showshequan", false);
        showqa = getIntent().getBooleanExtra("showqa", false);
        setTitle(title);
        setBtnLeft1Enable(true);
        if (showshequan) {
            setTextRight1Enable(true);
            setTextRight1Val("社圈");
        }


        if (showqa) {
            setBtnRight1Enable(true);
            setBtnRight1ImageResource(R.mipmap.ic_qa);
        }

        setBtnRight2Enable(true);
        setBtnRight2ImageResource(R.mipmap.ic_share);

        // 支持javascript
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
        // 扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);

        // 自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) { // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }

        });
        webView.loadUrl(url);
    }

    @Override
    protected void onTextRight1Click() {
        super.onTextRight1Click();
        Intent intent = new Intent(WebDetailActivity.this, OrgShequanActivity.class);
        intent.putExtra("cid", cid);
        startActivity(intent);
    }

    @Override
    protected void onBtnRight1Click() {
        super.onBtnRight1Click();
        Intent intent = new Intent(WebDetailActivity.this, QuestionAnswerActivity_.class);
        startActivity(intent);
    }
}
