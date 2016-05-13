package com.zl.app.data;

import android.text.TextUtils;

import com.android.volley.Request;
import com.baidu.mapapi.map.Text;
import com.google.gson.reflect.TypeToken;
import com.zl.app.model.activity.YyMobileActivity;
import com.zl.app.model.activity.YyMobileActivityComment;
import com.zl.app.model.activity.YyMobileActivityUser;
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


    String API_ACTIVITY_DETAIL = RequestURL.SERVER + "mobileActivity/view.html";
    String API_LISI = RequestURL.SERVER +"mobileActivity/home.html";
    String API_DELETE = RequestURL.SERVER +"mobileActivity/del.html";
    //活动报名
    String API_JOIN = RequestURL.SERVER + "mobileActivity/join.html";
    //取消活动报名
    String API_UNJOIN = RequestURL.SERVER + "mobileActivity/unjoin.html";
    //活动人员接口
    String API_ACTIVITY_MEMBERS = RequestURL.SERVER + "mobileActivity/member.html";

    //活动评论列表接口
    String API_ACTIVITY_COMMENT_LIST = RequestURL.SERVER + "mobileActivityComment/list.html";

    //活动评论接口
    String API_ACTIVITY_COMMENT = RequestURL.SERVER + "mobileActivityComment/insert.html";

    //活动搜索
    String API_ACTIVITY_SEARCH = RequestURL.SERVER + "mobileActivity/search.html";

    //他人活动
    String API_HIS_ACTIVITY = RequestURL.SERVER + "mobileActivity/other.html";

    public void getHisActivities(String uid, int userId, DefaultResponseListener<BaseResponse<List<YyMobileActivity>>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("userId", userId+"");
        GsonRequest request = new GsonRequest(Request.Method.POST, API_HIS_ACTIVITY, params, null,
                new TypeToken<BaseResponse<List<YyMobileActivity>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    public void searchActivities(String uid, String keyword, DefaultResponseListener<BaseResponse<List<YyMobileActivity>>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("headline", keyword);
        GsonRequest request = new GsonRequest(Request.Method.POST, API_ACTIVITY_SEARCH, params, null,
                new TypeToken<BaseResponse<List<YyMobileActivity>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    public void comment(String uid, String activityId,
                        String userId, String content, String picPath,
                        DefaultResponseListener<BaseResponse> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("activity.activityId", activityId);
        params.put("yyuser.userId", userId);
        params.put("content", content);
        if(!TextUtils.isEmpty(picPath)){
            params.put("picPath", picPath);
        }
        GsonRequest request = new GsonRequest(Request.Method.POST, API_ACTIVITY_COMMENT, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    public void getActivityComments(String uid, String activityId, DefaultResponseListener<BaseResponse<List<YyMobileActivityComment>>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("activity.activityId", activityId);
        GsonRequest request = new GsonRequest(Request.Method.POST, API_ACTIVITY_COMMENT_LIST, params, null,
                new TypeToken<BaseResponse<List<YyMobileActivityComment>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }


    public void getActivityMembers(String uid, String activityId, DefaultResponseListener<BaseResponse<List<YyMobileActivityUser>>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("activityId", activityId);
        GsonRequest request = new GsonRequest(Request.Method.POST, API_ACTIVITY_MEMBERS, params, null,
                new TypeToken<BaseResponse<List<YyMobileActivityUser>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    public void unjoinActivity(String uid, String activityId, DefaultResponseListener<BaseResponse> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("activityId", activityId);
        GsonRequest request = new GsonRequest(Request.Method.POST, API_UNJOIN, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }


    public void joinActivity(String uid, String activityId, DefaultResponseListener<BaseResponse> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("activityId", activityId);
        GsonRequest request = new GsonRequest(Request.Method.POST, API_JOIN, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }


    public void getHdDetail(String uid, String activityId, DefaultResponseListener<BaseResponse<YyMobileActivity>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("activityId", activityId);
        GsonRequest request = new GsonRequest(Request.Method.POST, API_ACTIVITY_DETAIL, params, null,
                new TypeToken<BaseResponse<YyMobileActivity>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

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
