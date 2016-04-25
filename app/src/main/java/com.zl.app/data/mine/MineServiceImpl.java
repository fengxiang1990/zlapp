package com.zl.app.data.mine;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;
import com.zl.app.data.news.model.YyMobileBase;
import com.zl.app.model.customer.YyMobileContract;
import com.zl.app.model.customer.YyMobileReservation;
import com.zl.app.model.user.YyMobileStudent;
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
public class MineServiceImpl implements  MineService{

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
        params.put("pageNo",pageNo+"");
        params.put("pageSize",pageSize+"");
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_MY_YUYUE, params, null,
                new TypeToken<BaseResponse<List<YyMobileReservation>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }
}
