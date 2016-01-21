package com.zl.app.data.user;

import com.zl.app.data.user.model.YyMobileUser;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.util.net.SimpleHttpResponse;

import java.util.concurrent.ExecutionException;

/**
 * Created by fengxiang on 2016/1/21.
 */
public interface UserService{


    /**
     * 检测手机号能否注册
     * @param mobile
     * @return
     */
    public SimpleHttpResponse registCheck(String mobile) throws ExecutionException, InterruptedException;

    /**
     * 发送注册验证码
     * @param mobile
     * @return
     */
    public SimpleHttpResponse registSendCode(String mobile) throws ExecutionException, InterruptedException;

    /**
     * 注册用户
     * @param mobile  手机号
     * @param password
     * @param passWordTwo
     * @param nickName  昵称
     * @param remark  注册短信验证码
     * @return
     */
    public SimpleHttpResponse regist(String mobile,String password,String passWordTwo,String nickName,String remark) throws ExecutionException, InterruptedException;

    /**
     * 登陆
     * @param account
     * @param password
     * @param listener
     */
    public void login(String account,String password, DefaultResponseListener<BaseResponse<YyMobileUser>> listener);
}
