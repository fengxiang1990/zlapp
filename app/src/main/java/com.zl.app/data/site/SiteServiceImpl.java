package com.zl.app.data.site;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;
import com.zl.app.data.news.model.YyMobileUserComment;
import com.zl.app.data.user.model.YyMobileUser;
import com.zl.app.util.AppManager;
import com.zl.app.util.RequestURL;
import com.zl.app.util.StringUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.util.net.GsonRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/3/2.
 */
public class SiteServiceImpl implements SiteService {

    @Override
    public void getUserInfo(String uid, String ids, DefaultResponseListener<BaseResponse<YyMobileUser>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("ids", ids);
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_SITE_USER, params, null,
                new TypeToken<BaseResponse<YyMobileUser>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void getCommentList(String uid, String ids, int pageNo, int pageSize, DefaultResponseListener<BaseResponse<List<YyMobileUserComment>>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("ids", ids);
        params.put("pageNo", String.valueOf(pageNo));
        params.put("pageSize", String.valueOf(pageSize));
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_SITE_USER_COMMENTS, params, null,
                new TypeToken<BaseResponse<List<YyMobileUserComment>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void reply(String uid, String ids, String yyuserId, String yycontent, String yydate, String content, DefaultResponseListener<BaseResponse> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("ids", ids);
        if(!StringUtil.isEmpty(yyuserId)){
            params.put("yyuserId",yyuserId);
        }
        if(!StringUtil.isEmpty(yycontent)){
            params.put("yycontent",yycontent);
        }
        if(!StringUtil.isEmpty(yydate)){
            params.put("yydate",yydate);
        }
        params.put("content", content);
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_SITE_SUBMIT_USER_COMMENT, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }
}
