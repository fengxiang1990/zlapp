package com.zl.app.data.home;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;
import com.zl.app.data.home.model.YyMobileAdvt;
import com.zl.app.data.news.model.YyMobileNews;
import com.zl.app.model.customer.YyMobileCompany;
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
 * Created by fengxiang on 2016/2/16.
 */
public class HomeServiceImpl implements HomeService {
    @Override
    public void getOrgs(String uid, int pageNo, int pageSize, int typeId, String companyname, DefaultResponseListener<BaseResponse<List<YyMobileCompany>>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("pageNo", pageNo + "");
        params.put("pageSize", pageSize + "");
        params.put("type.typeId", typeId + "");
        if (!StringUtil.isEmpty(companyname)) {
            params.put("companyname", companyname);
        }
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_ORG_LIST, params, null,
                new TypeToken<BaseResponse<List<YyMobileCompany>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void getHomeCompany(String uid, int pageNo, int pageSize, DefaultResponseListener<BaseResponse<List<YyMobileCompany>>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("pageNo", pageNo + "");
        params.put("pageSize", pageSize + "");
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_HOME_TUIJIAN, params, null,
                new TypeToken<BaseResponse<List<YyMobileCompany>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void getHomeAds(String uid, DefaultResponseListener<BaseResponse<List<YyMobileAdvt>>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_HOME_AD, params, null,
                new TypeToken<BaseResponse<List<YyMobileAdvt>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void getHomeNews(String uid, int pageNo, int pageSize, DefaultResponseListener<BaseResponse<List<YyMobileNews>>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("pageNo", String.valueOf(pageNo));
        params.put("PageSize", String.valueOf(pageSize));
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_HOME_NEWS, params, null,
                new TypeToken<BaseResponse<List<YyMobileNews>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }
}
