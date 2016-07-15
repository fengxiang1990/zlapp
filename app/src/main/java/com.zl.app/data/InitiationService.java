package com.zl.app.data;

import android.util.Log;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;
import com.zl.app.data.model.initiation.YyMobileQuestion;
import com.zl.app.data.model.initiation.YyMobileZiluNews;
import com.zl.app.util.AppManager;
import com.zl.app.util.GsonUtil;
import com.zl.app.util.RequestURL;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.util.net.GsonRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fxa on 2016/7/14.
 */

public class InitiationService {

    String api_news_list = RequestURL.SERVER + "mobileZiluNews/list.html";
    String api_question_list = RequestURL.SERVER + "mobileQa/list.html";

    public void getNewsList(int pageNo, int pageSize, String uid, DefaultResponseListener<BaseResponse<List<YyMobileZiluNews>>> listener) {
        Map<String, String> map = new HashMap<>();
        map.put("pageNo", pageNo + "");
        map.put("pageSize", pageSize + "");
        map.put("uid", uid);
        Log.e("getNewsList", GsonUtil.gson.toJson(map));
        GsonRequest request = new GsonRequest(Request.Method.POST, api_news_list, map, null,
                new TypeToken<BaseResponse<List<YyMobileZiluNews>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    public void getQuestionList(int pageNo, int pageSize, String uid, DefaultResponseListener<BaseResponse<List<YyMobileQuestion>>> listener) {
        Map<String, String> map = new HashMap<>();
        map.put("pageNo", pageNo + "");
        map.put("pageSize", pageSize + "");
        map.put("uid", uid);
        Log.e("getQuestionList", GsonUtil.gson.toJson(map));
        GsonRequest request = new GsonRequest(Request.Method.POST, api_question_list, map, null,
                new TypeToken<BaseResponse<List<YyMobileQuestion>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }
}
