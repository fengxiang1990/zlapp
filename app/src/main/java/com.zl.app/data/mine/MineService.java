package com.zl.app.data.mine;

import com.zl.app.model.customer.YyMobileContract;
import com.zl.app.model.customer.YyMobileReservation;
import com.zl.app.model.user.YyMobileStudent;
import com.zl.app.model.user.YyMobileUserFans;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import java.util.List;

/**
 * Created by fxa on 2016/4/17.
 */
public interface MineService {

    /**
     * 获取朋友
     *
     * @param uid
     * @param pageNo
     * @param pageSize
     * @param listener
     */
    void getFrends(String uid, int pageNo, int pageSize, DefaultResponseListener<BaseResponse<List<YyMobileUserFans>>> listener);

    /**
     * 获取订单
     *
     * @param uid
     * @param listener
     */
    void getOrders(String uid, DefaultResponseListener<BaseResponse<List<YyMobileContract>>> listener);



    /**
     * 获取我的孩子列表
     *
     * @param uid
     * @param listener
     */
    void getBabies(String uid, DefaultResponseListener<BaseResponse<List<YyMobileStudent>>> listener);


    /**
     * 增加宝贝
     * @param uid
     * @param photo
     * @param name
     * @param birthday
     * @param idCard
     * @param type
     * @param listener
     */
    void addBaby(String uid, String photo, String name, String birthday, String idCard, int type, DefaultResponseListener<BaseResponse> listener);


    /**
     * 我的预约
     * @param uid
     * @param pageNo
     * @param pageSize
     * @param listener
     */
    void getMyYuyue(String uid, int pageNo, int pageSize, DefaultResponseListener<BaseResponse<List<YyMobileReservation>>> listener);
}
