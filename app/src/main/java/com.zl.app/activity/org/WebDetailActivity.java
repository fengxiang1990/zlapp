package com.zl.app.activity.org;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zl.app.R;
import com.zl.app.activity.initiation.SendAnswerActivity_;
import com.zl.app.activity.user.LoginActivity_;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.util.AppConfig;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by fengxiang on 2016/5/12.
 */
public class WebDetailActivity extends BaseActivityWithToolBar {

    WebView webView;
    String title;
    String news_title;
    String url;
    String cid;
    boolean showshequan = false;
    boolean showqa = false;//是否显示问答入口
    boolean showans = false;//是否显示回答按钮

    int questionId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_detail);
        webView = (WebView) findViewById(R.id.webView);
        title = getIntent().getStringExtra("title");
        news_title = getIntent().getStringExtra("news_title");
        url = getIntent().getStringExtra("url");
        cid = getIntent().getStringExtra("cid");
        showshequan = getIntent().getBooleanExtra("showshequan", false);
        showqa = getIntent().getBooleanExtra("showqa", false);
        showans = getIntent().getBooleanExtra("showans", false);
        setTitle(title);
        setBtnLeft1Enable(true);
        if (showshequan) {
            setTextRight1Enable(true);
            setTextRight1Val("社圈");
        }


        if (showqa) {
            // setBtnRight1Enable(true);
            // setBtnRight1ImageResource(R.mipmap.ic_qa);
        }

        if (showans) {
            setTextRight2Enable(true);
            setTextRight2Val("回答");
            questionId = getIntent().getIntExtra("questionId", 0);
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

    }


    @Override
    protected void onResume() {
        super.onResume();
        webView.loadUrl(url);
    }

    @Override
    protected void onTextRight1Click() {
        super.onTextRight1Click();
        if (AppConfig.isLogin(preference)) {
            Intent intent = new Intent(WebDetailActivity.this, OrgShequanActivity.class);
            intent.putExtra("cid", cid);
            startActivity(intent);
        } else {
            Intent intent = new Intent(WebDetailActivity.this, LoginActivity_.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onTextRight2Click() {
        super.onTextRight2Click();
        Intent intent = new Intent(WebDetailActivity.this, SendAnswerActivity_.class);
        intent.putExtra("title", title);
        intent.putExtra("questionId", questionId);
        startActivity(intent);
    }


    @Override
    protected void onBtnRight2Click() {
        super.onBtnRight2Click();
        showShare();
    }

    @Override
    protected void onBtnRight1Click() {
        super.onBtnRight1Click();
//        if (AppConfig.isLogin(preference)) {
//            Intent intent = new Intent(WebDetailActivity.this, QuestionAnswerActivity_.class);
//            startActivity(intent);
//        } else {
//            Intent intent = new Intent(WebDetailActivity.this, LoginActivity_.class);
//            startActivity(intent);
//        }

    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(news_title == null ? title : news_title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(news_title == null ? title : news_title);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        oks.setImageUrl("http://a.hiphotos.bdimg.com/wisegame/pic/item/df315c6034a85edfede6183141540923dd5475be.jpg");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        //oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
}
