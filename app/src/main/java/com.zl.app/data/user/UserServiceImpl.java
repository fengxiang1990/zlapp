package com.zl.app.data.user;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;
import com.zl.app.data.user.model.YyMobileUser;
import com.zl.app.util.AppManager;
import com.zl.app.util.RequestURL;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.util.net.GsonRequest;
import com.zl.app.util.net.MultiPartStack;
import com.zl.app.util.net.MultiPartStringRequest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by fengxiang on 2016/1/21.
 */
public class UserServiceImpl implements UserService {

    String TAG = "UserServiceImpl";

    @Override
    public void isTeacher(String uid, DefaultResponseListener<BaseResponse> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_CHECK_TEACHER, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

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
                new TypeToken<BaseResponse<YyMobileUser>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void getPasswordCode(String account, DefaultResponseListener<BaseResponse> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("account", account);
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_GET_PASSWORD_CODE, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void getNewPassword(String account, String smsCode, DefaultResponseListener<BaseResponse> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("account", account);
        params.put("password", smsCode);
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_GET_PASSWORD, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void modifyPassword(String uid, String remark, String password, String passWordTwo, DefaultResponseListener<BaseResponse> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("remark", remark);
        params.put("password", password);
        params.put("passWordTwo", passWordTwo);
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_MODIFY_PASSWORD, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void uploadUserHeadImg(String uid, File imgFile, DefaultResponseListener<String> listener) {
        MultiPartStringRequest multiPartRequest = new MultiPartStringRequest(
                Request.Method.POST, RequestURL.API_USER_UPLOAD,listener, listener) ;
        multiPartRequest.addStringUpload("uid", uid);
        multiPartRequest.addFileUpload("imgFile", imgFile);
        AppManager.getRequestQueue().add(multiPartRequest);
    }

    @Override
    public void updateUserInfo(String uid, String picPath, String nickName, String age, String qq, String introduce,
                               String mobileshow, String emailshow, String qqshow,
                               String plshow, String dzshow, String scshow,
                               DefaultResponseListener<BaseResponse> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("picPath", picPath);
        params.put("nickName", nickName);
        params.put("age", age);
        params.put("qq", qq);
        params.put("introduce", introduce);
        params.put("mobileshow", mobileshow);
        params.put("emailshow", emailshow);
        params.put("qqshow", qqshow);
        params.put("plshow", plshow);
        params.put("dzshow", dzshow);
        params.put("scshow", scshow);
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_USER_INFO_UPDATE, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }
}
