package com.zl.app.data.mine;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;
import com.zl.app.data.news.model.YyMobileBase;
import com.zl.app.model.customer.YyMobileReservation;
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
