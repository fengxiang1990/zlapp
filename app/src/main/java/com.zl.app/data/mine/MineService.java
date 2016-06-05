package com.zl.app.data.mine;

import com.zl.app.data.model.customer.YyMobileContract;
import com.zl.app.data.model.customer.YyMobileReservation;
import com.zl.app.data.model.customer.YyMobileSi;
import com.zl.app.data.model.user.YyMobileStudent;
import com.zl.app.data.model.user.YyMobileUserFans;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import java.util.List;

/**
 * Created by fxa on 2016/4/17.
 */
public interface MineService {

    /**
     * 操作老师邀请接口
     * @param uid
     * @param teacherId
     * @param type
     * @param listener
     */
    void acceptTeacher(String uid,int teacherId,int type,DefaultResponseListener<BaseResponse> listener);
    /**
     * 系统通知
     * @param uid
     * @param pageNo
     * @param pageSize
     * @param listener
     */
    void getSystemNotice(String uid,int pageNo,int pageSize,DefaultResponseListener<BaseResponse<List<YyMobileSi>>> listener);
    /**
     * 删除好友
     * @param uid
     * @param ids
     * @param listener
     */
     void deleteFrend(String uid,String ids,DefaultResponseListener<BaseResponse> listener);

    /**
     *
     * @param uid
     * @param studentId
     * @param photo
     * @param name
     * @param birthday
     * @param idCard
     * @param type
     * @param listener
     */
    void updateStudent(String uid,int studentId,String photo,String name,
                       String birthday,String idCard,int type,DefaultResponseListener<BaseResponse> listener);

    /**
     * 搜索朋友
     *
     * @param uid
     * @param mobile
     * @param listener
     */
    void searchFrends(String uid,String mobile, DefaultResponseListener<BaseResponse<YyMobileUserFans>> listener);

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
     * 获取孩子详情
     * @param uid
     * @param studentId
     * @param listener
     */
    void getChildDetail(String uid,int studentId,DefaultResponseListener<BaseResponse<YyMobileStudent>> listener);

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
