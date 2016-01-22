package com.zl.app.data.user;

import com.zl.app.data.user.model.YyMobileUser;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import java.util.concurrent.ExecutionException;

/**
 * Created by fengxiang on 2016/1/21.
 */
public interface UserService {


    /**
     * 检测手机号能否注册
     *
     * @param mobile
     * @return
     */
    public BaseResponse registCheck(String mobile) throws ExecutionException, InterruptedException;

    /**
     * 发送注册验证码
     *
     * @param mobile
     * @return
     */
    public BaseResponse registSendCode(String mobile) throws ExecutionException, InterruptedException;

    /**
     * 注册用户
     *
     * @param mobile      手机号
     * @param password
     * @param passWordTwo
     * @param nickName    昵称
     * @param remark      注册短信验证码
     * @return
     */
    public BaseResponse regist(String mobile, String password, String passWordTwo, String nickName, String remark) throws ExecutionException, InterruptedException;

    /**
     * 登陆
     *
     * @param account
     * @param password
     * @param listener
     */
    public void login(String account, String password, DefaultResponseListener<BaseResponse<YyMobileUser>> listener);


    /**
     * 忘记密码短信发送接口
     *
     * @param account
     * @param listener
     */
    public void getPasswordCode(String account, DefaultResponseListener<BaseResponse> listener);

    /**
     * 获取新密码接口
     *
     * @param account
     * @param smsCode
     * @param listener
     */
    public void getNewPassword(String account, String smsCode, DefaultResponseListener<BaseResponse> listener);

    /**
     * 修改密码接口
     *
     * @param uid
     * @param remark
     * @param password
     * @param passWordTwo
     * @param listener
     */
    public void modifyPassword(String uid, String remark, String password, String passWordTwo, DefaultResponseListener<BaseResponse> listener);
}
