package com.zl.app.data;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;
import com.zl.app.model.activity.YyMobileActivity;
import com.zl.app.util.AppManager;
import com.zl.app.util.RequestURL;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.util.net.GsonRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fxa on 2016/4/26.
 */
public class ActivityService {


    String API_LISI = RequestURL.SERVER +"mobileActivity/home.html";

    String API_DELETE = RequestURL.SERVER +"mobileActivity/del.html";

    public void delete(String uid,String activityId,DefaultResponseListener<BaseResponse> listener){
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("activityId", activityId);
        GsonRequest request = new GsonRequest(Request.Method.POST,API_DELETE, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    public void getActivities(String uid,DefaultResponseListener<BaseResponse<List<YyMobileActivity>>> listener){
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        GsonRequest request = new GsonRequest(Request.Method.POST, API_LISI, params, null,
                new TypeToken<BaseResponse<List<YyMobileActivity>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    //发布活动
    public void sendActivity(String uid, String hdDateString, String headline, String address, int ispublic,DefaultResponseListener<BaseResponse> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("hdDateString", hdDateString);
        params.put("headline", headline);
        params.put("address", address);
        params.put("ispublic", ispublic+"");
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_SEND_ACTIVITY, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }
}
