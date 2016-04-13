package com.zl.app.data.home;

import com.zl.app.data.home.model.YyMobileAdvt;
import com.zl.app.data.news.model.YyMobileNews;
import com.zl.app.model.customer.YyMobileCompany;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import java.util.List;

/**
 * Created by fengxiang on 2016/2/16.
 */
public interface HomeService {


    void getOrgs(String uid, int pageNo, int pageSize, int typeId, String companyname, DefaultResponseListener<BaseResponse<List<YyMobileCompany>>> listener);


    /**
     * 获取首页推荐机构
     * @param uid
     *  @param pageNo
     *  @param pageSize
     * @param listener
     */
    void getHomeCompany(String uid, int pageNo, int pageSize, DefaultResponseListener<BaseResponse<List<YyMobileCompany>>> listener);

    /**
     *
     * @param uid
     * @param listener
     */
    void getHomeAds(String uid, DefaultResponseListener<BaseResponse<List<YyMobileAdvt>>> listener);

    /**
     *
     * @param uid
     * @param pageNo
     * @param pageSize
     * @param listener
     */
    void getHomeNews(String uid, int pageNo, int pageSize, DefaultResponseListener<BaseResponse<List<YyMobileNews>>> listener);

}
