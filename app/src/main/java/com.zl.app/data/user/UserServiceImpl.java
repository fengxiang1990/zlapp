package com.zl.app.data.user;

import com.android.volley.Request;
import com.android.volley.toolbox.RequestFuture;
import com.google.gson.reflect.TypeToken;
import com.zl.app.data.user.model.YyMobileUser;
import com.zl.app.util.AppManager;
import com.zl.app.util.RequestURL;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.util.net.GsonRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by fengxiang on 2016/1/21.
 */
public class UserServiceImpl implements UserService {

    String TAG = "UserServiceImpl";

    @Override
    public BaseResponse registCheck(String mobile) throws ExecutionException, InterruptedException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", mobile);
        RequestFuture<BaseResponse> future = RequestFuture.newFuture();
        GsonRequest<BaseResponse> request = new GsonRequest<BaseResponse>(
                Request.Method.POST, RequestURL.API_RIGIST_CHECK, params, null,
                new TypeToken<BaseResponse>() {
                }, future, future);
        AppManager.getRequestQueue().add(request);
        return future.get();
    }

    @Override
    public BaseResponse registSendCode(String mobile) throws ExecutionException, InterruptedException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", mobile);
        RequestFuture<BaseResponse> future = RequestFuture.newFuture();
        GsonRequest<BaseResponse> request = new GsonRequest<BaseResponse>(
                Request.Method.POST, RequestURL.API_REGIST_CODE, params, null,
                new TypeToken<BaseResponse>() {
                }, future, future);
        AppManager.getRequestQueue().add(request);
        return future.get();
    }

    @Override
    public BaseResponse regist(String mobile, String password, String passWordTwo, String nickName, String remark) throws ExecutionException, InterruptedException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", mobile);
        params.put("password", password);
        params.put("passWordTwo", passWordTwo);
        params.put("nickName", nickName);
        params.put("remark", remark);
        RequestFuture<BaseResponse> future = RequestFuture.newFuture();
        GsonRequest<BaseResponse> request = new GsonRequest<BaseResponse>(
                Request.Method.POST, RequestURL.API_REGIST, params, null,
                new TypeToken<BaseResponse>() {
                }, future, future);
        AppManager.getRequestQueue().add(request);
        return future.get();
    }

    @Override
    public void login(String account, String password, DefaultResponseListener<BaseResponse<YyMobileUser>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("account", account);
        params.put("password", password);
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_LOGIN, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }
}
