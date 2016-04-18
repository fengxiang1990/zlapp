package com.zl.app.data.mine;

import com.zl.app.model.customer.YyMobileReservation;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import java.util.List;

/**
 * Created by fxa on 2016/4/17.
 */
public interface MineService {


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
