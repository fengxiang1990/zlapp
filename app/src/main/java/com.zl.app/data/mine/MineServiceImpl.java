package com.zl.app.data.mine;

import android.text.TextUtils;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;
import com.zl.app.data.model.customer.YyMobileContract;
import com.zl.app.data.model.customer.YyMobileReservation;
import com.zl.app.data.model.customer.YyMobileSi;
import com.zl.app.data.model.user.YyMobileStudent;
import com.zl.app.data.model.user.YyMobileUserFans;
import com.zl.app.util.AppManager;
import com.zl.app.util.RequestURL;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.util.net.GsonRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fxa on 2016/4/17.
 */
public class MineServiceImpl implements MineService {

    @Override
    public void acceptTeacher(String uid, int teacherId, int type, DefaultResponseListener<BaseResponse> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("teacherId", teacherId + "");
        params.put("type", type + "");
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_SYSTEM_NOTICE_ACCEPT, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void getSystemNotice(String uid, int pageNo, int pageSize, DefaultResponseListener<BaseResponse<List<YyMobileSi>>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("pageNo", pageNo + "");
        params.put("pageSize", pageSize + "");
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_SYSTEM_NOTICE, params, null,
                new TypeToken<BaseResponse<List<YyMobileSi>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void deleteFrend(String uid, String ids, DefaultResponseListener<BaseResponse> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("ids", ids + "");
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_DELETE_FREND, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void updateStudent(String uid, int studentId, String photo, String name, String birthday, String idCard, int type, DefaultResponseListener<BaseResponse> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("student.studentId", studentId + "");
        if (!TextUtils.isEmpty(photo)) {
            params.put("student.photo", photo);
        }
        if (!TextUtils.isEmpty(name)) {
            params.put("student.name", name);
        }
        if (!TextUtils.isEmpty(birthday)) {
            params.put("birthdayString", birthday);
        }
        if (!TextUtils.isEmpty(idCard)) {
            params.put("student.idCard", idCard);
        }
        if (type > 0) {
            params.put("type", type + "");
        }

        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_UPDATE_CHILD_INFO, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void searchFrends(String uid, String mobile, DefaultResponseListener<BaseResponse<YyMobileUserFans>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("mobile", mobile);
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_FREND_SEARCH, params, null,
                new TypeToken<BaseResponse<YyMobileUserFans>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void getFrends(String uid, int pageNo, int pageSize, DefaultResponseListener<BaseResponse<List<YyMobileUserFans>>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("pageNo", pageNo + "");
        params.put("pageSize", pageSize + "");
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_FREND_LIST, params, null,
                new TypeToken<BaseResponse<List<YyMobileUserFans>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void getOrders(String uid, DefaultResponseListener<BaseResponse<List<YyMobileContract>>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_ORDER_LIST, params, null,
                new TypeToken<BaseResponse<List<YyMobileContract>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void getChildDetail(String uid, int studentId, DefaultResponseListener<BaseResponse<YyMobileStudent>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("student.studentId", String.valueOf(studentId));
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_BABY_DETAIL, params, null,
                new TypeToken<BaseResponse<YyMobileStudent>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void getBabies(String uid, DefaultResponseListener<BaseResponse<List<YyMobileStudent>>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_BABY_LIST, params, null,
                new TypeToken<BaseResponse<List<YyMobileStudent>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void addBaby(String uid, String photo, String name, String birthday, String idCard, int type, DefaultResponseListener<BaseResponse> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("student.photo", photo);
        params.put("student.name", name);
        params.put("birthdayString", birthday);
        params.put("student.idCard", idCard);
        params.put("type", String.valueOf(type));
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_ADD_BABY, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void getMyYuyue(String uid, int pageNo, int pageSize, DefaultResponseListener<BaseResponse<List<YyMobileReservation>>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("pageNo", pageNo + "");
        params.put("pageSize", pageSize + "");
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_MY_YUYUE, params, null,
                new TypeToken<BaseResponse<List<YyMobileReservation>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }
}
