package com.zl.app.data.user;

import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.toolbox.RequestFuture;
import com.baidu.mapapi.map.Text;
import com.google.gson.reflect.TypeToken;
import com.zl.app.data.user.model.YyMobileUser;
import com.zl.app.util.AppManager;
import com.zl.app.util.RequestURL;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.util.net.GsonRequest;
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
    public void checkSmsCode(String account, String remark, DefaultResponseListener<BaseResponse> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("account", account);
        params.put("remark", remark);
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_CHECK_SMS_CODE, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

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
    public void modifyPassword(String account, String remark, String password, String passWordTwo, DefaultResponseListener<BaseResponse> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("account", account);
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
                               String plshow, String dzshow, String scshow,String address,
                               DefaultResponseListener<BaseResponse> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        if(!TextUtils.isEmpty(picPath)){
            params.put("picPath", picPath);
        }
        if(!TextUtils.isEmpty(nickName)){
            params.put("nickName", nickName);
        }
        if(!TextUtils.isEmpty(address)){
            params.put("address", address);
        }
        if(!TextUtils.isEmpty(age)){
            params.put("age", age);
        }
        if(!TextUtils.isEmpty(qq)){
            params.put("qq", qq);
        }
        if(!TextUtils.isEmpty(introduce)){
            params.put("introduce", introduce);
        }
        if(!TextUtils.isEmpty(mobileshow)){
            params.put("mobileshow", mobileshow);
        }
        if(!TextUtils.isEmpty(emailshow)){
            params.put("emailshow", emailshow);
        }
        if(!TextUtils.isEmpty(qqshow)){
            params.put("qqshow", qqshow);
        }
        if(!TextUtils.isEmpty(dzshow)){
            params.put("plshow", dzshow);
        }
        if(!TextUtils.isEmpty(dzshow)){
            params.put("dzshow", dzshow);
        }
        if(!TextUtils.isEmpty(scshow)){
            params.put("scshow", scshow);
        }

        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_USER_INFO_UPDATE, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void applyUserAdvice(String uid, String content, String image1_path, String image2_path,
                                String image3_path, String image4_path,
                                DefaultResponseListener<BaseResponse> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("adviceContent", content);
        params.put("image1", image1_path);
        params.put("image2", image2_path);
        params.put("image3", image3_path);
        params.put("image4", image4_path);
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_USER_ADVICE, params, null,
                new TypeToken<BaseResponse>() {
                }, listener, listener);
        AppManager.getRequestQueue().add(request);
    }
}
