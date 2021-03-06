package com.zl.app.data.home;

import com.zl.app.data.home.model.OrgType;
import com.zl.app.data.home.model.YyMobileAdvt;
import com.zl.app.data.model.customer.YyMobileCompany;
import com.zl.app.data.model.customer.YyMobileCompanyComment;
import com.zl.app.data.model.customer.YyMobileCompanyGrade;
import com.zl.app.data.model.user.YyMobileCity;
import com.zl.app.data.news.model.YyMobileNews;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import java.util.List;

/**
 * Created by fengxiang on 2016/2/16.
 */
public interface HomeService {


    /**
     *
     * @param uid
     * @param companyId
     * @param userId   回复人id
     * @param content
     * @param picPath
     * @param listener
     */
    void sendOrgMessage(String uid,String companyId,String userId,String content,String picPath,DefaultResponseListener<BaseResponse> listener);

    /**
     * 机构留言列表
     * @param uid
     * @param companyId
     * @param listener
     */
    void getOrgMessages(String uid,String companyId,DefaultResponseListener<BaseResponse<List<YyMobileCompanyComment>>> listener);

    /**
     * @param uid
     * @param listener
     */
    void getOrgTypes(String uid, DefaultResponseListener<BaseResponse<List<OrgType>>> listener);

    /**
     * 机构预约
     * @param uid
     * @param companyId
     * @param username
     * @param tel
     * @param content
     * @param listener
     */
    void postOrgYuyue(String uid,String companyId,String username,String tel,String content,DefaultResponseListener<BaseResponse<String>> listener);

    /**
     * 评价机构
     *
     * @param uid
     * @param companyId
     * @param grade
     * @param content
     * @param listener
     */
    void postOrgGrade(String uid, String companyId, String grade, String content, DefaultResponseListener<BaseResponse> listener);

    /**
     * 获取机构评价
     * @param uid
     * @param companyId
     * @param type      0全部 1 差评 2 中评 3好评
     */
    void getOrgsGradeComments(String uid, String companyId, int type, int pageNo, int pageSize, DefaultResponseListener<BaseResponse<List<YyMobileCompanyGrade>>> listener);

    void getOrgSite(String uid, String companyId, DefaultResponseListener<BaseResponse<YyMobileCompany>> listener);


    void getOrgs(String uid, int pageNo, int pageSize, int typeId, String companyname,
                 int province_cityId,
                 int city_cityId,
                 int district_cityId,
                 int street_cityId,
                 String orderName,
                 DefaultResponseListener<BaseResponse<List<YyMobileCompany>>> listener);


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


    /**
     * @param uid
     * @param cityId
     * @param listener
     */
    void getCityAddress(String uid, int cityId, DefaultResponseListener<BaseResponse<List<YyMobileCity>>> listener);
}
