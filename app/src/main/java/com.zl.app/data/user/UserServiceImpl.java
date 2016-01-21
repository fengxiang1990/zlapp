package com.zl.app.data.user;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.reflect.TypeToken;
import com.zl.app.data.user.model.YyMobileUser;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.RequestURL;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.util.net.GsonRequest;
import com.zl.app.util.net.SimpleHttpResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by fengxiang on 2016/1/21.
 */
public class UserServiceImpl implements UserService {

    String TAG  = "UserServiceImpl";
    @Override
    public SimpleHttpResponse registCheck(String mobile) throws ExecutionException, InterruptedException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", mobile);
        RequestFuture<SimpleHttpResponse> future = RequestFuture.newFuture();
        GsonRequest<SimpleHttpResponse> request = new GsonRequest<SimpleHttpResponse>(
                Request.Method.POST, RequestURL.API_RIGIST_CHECK, params, null,
                new TypeToken<SimpleHttpResponse>() {
                }, future, future);
        AppManager.getRequestQueue().add(request);
        return future.get();
    }

    @Override
    public SimpleHttpResponse registSendCode(String mobile) throws ExecutionException, InterruptedException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", mobile);
        RequestFuture<SimpleHttpResponse> future = RequestFuture.newFuture();
        GsonRequest<SimpleHttpResponse> request = new GsonRequest<SimpleHttpResponse>(
                Request.Method.POST, RequestURL.API_REGIST_CODE, params, null,
                new TypeToken<SimpleHttpResponse>() {
                }, future, future);
        AppManager.getRequestQueue().add(request);
        return future.get();
    }

    @Override
    public SimpleHttpResponse regist(String mobile, String password, String passWordTwo, String nickName, String remark) throws ExecutionException, InterruptedException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", mobile);
        params.put("password", password);
        params.put("passWordTwo", passWordTwo);
        params.put("nickName", nickName);
        params.put("remark", remark);
        RequestFuture<SimpleHttpResponse> future = RequestFuture.newFuture();
        GsonRequest<SimpleHttpResponse> request = new GsonRequest<SimpleHttpResponse>(
                Request.Method.POST, RequestURL.API_REGIST, params, null,
                new TypeToken<SimpleHttpResponse>() {
                }, future, future);
        AppManager.getRequestQueue().add(request);
        return future.get();
    }

    @Override
    public void login(String account,String password,DefaultResponseListener<BaseResponse<YyMobileUser>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("account", account);
        params.put("password", password);
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_LOGIN,params,null,
                new TypeToken<BaseResponse>(){},
                 listener,listener);
        AppManager.getRequestQueue().add(request);
    }
}
