package com.zl.app.data.news;


import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;
import com.zl.app.data.news.model.YyMobileBase;
import com.zl.app.data.news.model.YyMobileNews;
import com.zl.app.data.news.model.YyMobileNewsComment;
import com.zl.app.util.AppManager;
import com.zl.app.util.RequestURL;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.util.net.GsonRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fengxiang on 2016/2/17.
 */
public class NewsServiceImpl implements NewsService {

    @Override
    public void getNewsType(String uid,String code, DefaultResponseListener<BaseResponse<List<YyMobileBase>>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("code", code == null ? DEFAULT_CODE : code);
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_NEWS_TYPE, params, null,
                new TypeToken<BaseResponse<List<YyMobileBase>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void getNewsByType(String uid, int pageNo, int pageSize, String code, String value, DefaultResponseListener<BaseResponse<List<YyMobileNews>>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("pageNo",String.valueOf(pageNo));
        params.put("PageSize",String.valueOf(pageSize));
        params.put("code",code);
        params.put("value",value);
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_NEWS_LIST, params, null,
                new TypeToken<BaseResponse<List<YyMobileNews>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void getNewsDetail(String uid, String url, DefaultResponseListener<BaseResponse<YyMobileNews>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("url",url);
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_NEWS_DETAIL, params, null,
                new TypeToken<BaseResponse<YyMobileNews>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void getNewsComments(String uid, String url, int pageNo, int pageSize, DefaultResponseListener<BaseResponse<List<YyMobileNewsComment>>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("url", url);
        params.put("pageNo",String.valueOf(pageNo));
        params.put("PageSize",String.valueOf(pageSize));
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_NEWS_COMMENTS, params, null,
                new TypeToken<BaseResponse<List<YyMobileNewsComment>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void submitComment(String uid, String url, String yyuserId, String yycontent, String yydate, String content, DefaultResponseListener<BaseResponse> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("url", url);
        if(yyuserId != null)
            params.put("yyuserId",yyuserId);
        if(yycontent != null)
            params.put("yycontent",yycontent);
        if(yydate != null)
            params.put("yydate",yydate);
        params.put("content",content);
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_NEWS_COMMENT_SUBMIT, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void submitGood(String uid, String url, DefaultResponseListener<BaseResponse> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("url", url);
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_NEWS_GOOD, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void favorite(String uid, String url, DefaultResponseListener<BaseResponse> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("url", url);
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_NEWS_FAVORITE, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }
}
