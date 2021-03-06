package com.zl.app.data;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.google.gson.reflect.TypeToken;
import com.zl.app.data.model.user.YyMobileLetter;
import com.zl.app.data.model.user.YyMobileUserLetter;
import com.zl.app.util.AppManager;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.util.net.GsonRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 聊天服务
 * Created by fxa on 2016/5/31.
 */
public class ChartService {

    String tag  = "ChartService";

    String API_CHART_LIST = "http://www.ziluedu.cn/mobileLetter/getLetters.html";

    String API_SEND = "http://www.ziluedu.cn/mobileLetter/send.html";

    String API_LIST_OLD = "http://www.ziluedu.cn/mobileLetter/oldlist.html";

    String API_LIST_NEW = "http://www.ziluedu.cn/mobileLetter/newlist.html";

    String API_GET_NEW_MSG_COUNT  = "http://www.ziluedu.cn/mobileLetter/checkLetter.html";

    int pageSize = 15;


    /**
     * 未读私信数量接口
     * @param uid
     * @param listener
     */
    public void getNewMsgCount(String uid, DefaultResponseListener<BaseResponse> listener){
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        Log.e(tag,params.toString());
        GsonRequest request = new GsonRequest(Request.Method.POST, API_GET_NEW_MSG_COUNT, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }
    /**
     * 获取聊天列表
     * @param uid
     * @param pageNo
     * @param pageSize
     * @param listener
     */
    public void getChartList(String uid,int pageNo,int pageSize,DefaultResponseListener<BaseResponse<List<YyMobileUserLetter>>> listener){
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("pageNo", pageNo+"");
        params.put("pageSize", pageSize+"");
        Log.e(tag,params.toString());
        GsonRequest request = new GsonRequest(Request.Method.POST, API_CHART_LIST, params, null,
                new TypeToken<BaseResponse<List<YyMobileUserLetter>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    /**
     * 发送聊天信息
     * @param uid
     * @param toUserId
     * @param content
     * @param picPath
     * @param listener
     */
    public void send(String uid, String toUserId, String content, String picPath, DefaultResponseListener<BaseResponse> listener){
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("toUserId", toUserId);
        if(!TextUtils.isEmpty(content)){
            params.put("content", content);
        }
        if(!TextUtils.isEmpty(picPath)){
            params.put("picPath", picPath);
        }
        Log.e(tag,params.toString());
        GsonRequest request = new GsonRequest(Request.Method.POST, API_SEND, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }


    /**
     * 获取最新的聊天记录
     * @param uid
     * @param toUserId  好友的userid
     * @param contentId 当前内容的最大主键
     */
    public void getNewList(String uid, String toUserId, int contentId, DefaultResponseListener<BaseResponse<List<YyMobileLetter>>> listener){
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("toUserId", toUserId);
        params.put("contentId", contentId+"");
        params.put("pageSize", pageSize+"");
        Log.e(tag,params.toString());
        GsonRequest request = new GsonRequest(Request.Method.POST, API_LIST_NEW, params, null,
                new TypeToken<BaseResponse<List<YyMobileLetter>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    /**
     * 获取上一页聊天记录
     * @param uid
     * @param toUserId
     * @param contentId 当前内容的最小主键
     * @param listener
     */
    public void getOldList(String uid, String toUserId, int contentId, DefaultResponseListener<BaseResponse<List<YyMobileLetter>>> listener){
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("toUserId", toUserId);
        params.put("contentId", contentId+"");
        params.put("pageSize", pageSize+"");
        Log.e(tag,params.toString());
        GsonRequest request = new GsonRequest(Request.Method.POST, API_LIST_OLD, params, null,
                new TypeToken<BaseResponse<List<YyMobileLetter>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

}
