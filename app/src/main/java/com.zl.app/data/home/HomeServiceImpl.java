package com.zl.app.data.home;

import android.text.TextUtils;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;
import com.zl.app.data.home.model.OrgType;
import com.zl.app.data.home.model.YyMobileAdvt;
import com.zl.app.data.model.customer.YyMobileCompany;
import com.zl.app.data.model.customer.YyMobileCompanyComment;
import com.zl.app.data.model.customer.YyMobileCompanyGrade;
import com.zl.app.data.model.user.YyMobileCity;
import com.zl.app.data.news.model.YyMobileNews;
import com.zl.app.util.AppConfig;
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

    String API_MSG_LIST = RequestURL.SERVER + "mobileCompanyComment/list.html";

    String API_SEND_MSG = RequestURL.SERVER + "mobileCompanyComment/insert.html";


    @Override
    public void sendOrgMessage(String uid, String companyId, String userId, String content, String picPath, DefaultResponseListener<BaseResponse> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("company.companyId", companyId);
        if(!TextUtils.isEmpty(userId)){
            params.put("yyuser.userId", userId);
        }
        if(!TextUtils.isEmpty(picPath)){
            params.put("picPath", picPath);
        }
        params.put("content", content);
        GsonRequest request = new GsonRequest(Request.Method.POST, API_SEND_MSG, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void getOrgMessages(String uid, String companyId, DefaultResponseListener<BaseResponse<List<YyMobileCompanyComment>>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("company.companyId", companyId);
        GsonRequest request = new GsonRequest(Request.Method.POST, API_MSG_LIST, params, null,
                new TypeToken<BaseResponse<List<YyMobileCompanyComment>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void getOrgTypes(String uid, DefaultResponseListener<BaseResponse<List<OrgType>>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("code", "company");
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_ORG_TYPE_LIST, params, null,
                new TypeToken<BaseResponse<List<OrgType>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void postOrgYuyue(String uid, String companyId, String username, String tel, String content, DefaultResponseListener<BaseResponse<String>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("company.companyId", companyId);
        params.put("username", username);
        params.put("mobile", tel);
        params.put("content", content);
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_ORG_SITE_YUYUE, params, null,
                new TypeToken<BaseResponse<String>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void postOrgGrade(String uid, String companyId, String grade, String content, DefaultResponseListener<BaseResponse> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("company.companyId", companyId);
        params.put("grade", grade);
        params.put("content", content);
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_ORG_GRADE_POST_COMMENT, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void getOrgsGradeComments(String uid, String companyId, int type, int pageNo, int pageSize, DefaultResponseListener<BaseResponse<List<YyMobileCompanyGrade>>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("pageNo", pageNo + "");
        params.put("pageSize", pageSize + "");
        params.put("company.companyId", companyId + "");
        params.put("type", type + "");
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_ORG_GRADE_COMMENTS, params, null,
                new TypeToken<BaseResponse<List<YyMobileCompanyGrade>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void getOrgSite(String uid, String companyId, DefaultResponseListener<BaseResponse<YyMobileCompany>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("companyId", companyId);
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_ORG_SITE, params, null,
                new TypeToken<BaseResponse<YyMobileCompany>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    @Override
    public void getOrgs(String uid, int pageNo, int pageSize, int typeId, String companyname,
                        int province_cityId, int city_cityId, int district_cityId, int street_cityId,
                        String orderName,
                        DefaultResponseListener<BaseResponse<List<YyMobileCompany>>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("pageNo", pageNo + "");
        params.put("pageSize", pageSize + "");
        params.put("type.typeId", typeId + "");
        if (!StringUtil.isEmpty(companyname)) {
            params.put("companyname", companyname);
        }
        if (province_cityId != 0) {
            params.put("province.cityId", province_cityId + "");
        }
        if (city_cityId != 0) {
            params.put("city.cityId", city_cityId + "");
        }
        if (district_cityId != 0) {
            params.put("district.cityId", district_cityId + "");
        }
        if (street_cityId != 0) {
            params.put("street.cityId", street_cityId + "");
        }
        if (!TextUtils.isEmpty(orderName)) {
            params.put("orderName", orderName);
            params.put("orderMethod", "desc");
        }
        params.put("lat", AppConfig.latitude);
        params.put("lng", AppConfig.longitude);
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
        params.put("lat", AppConfig.latitude);
        params.put("lng", AppConfig.longitude);
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

    @Override
    public void getCityAddress(String uid, int cityId, DefaultResponseListener<BaseResponse<List<YyMobileCity>>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("parentId", String.valueOf(cityId));
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_CITY_LIST, params, null,
                new TypeToken<BaseResponse<List<YyMobileCity>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }
}
