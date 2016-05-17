package com.zl.app.data.user;

import com.zl.app.data.user.model.YyMobileUser;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * Created by fengxiang on 2016/1/21.
 */
public interface UserService {

    /**
     * 校验验证码
     * @param account
     * @param remark
     * @param listener
     */
    void checkSmsCode(String account,String remark,DefaultResponseListener<BaseResponse> listener);

    void isTeacher(String uid, DefaultResponseListener<BaseResponse> listener);

    /**
     * 检测手机号能否注册
     *
     * @param mobile
     * @return
     */
    BaseResponse registCheck(String mobile) throws ExecutionException, InterruptedException;

    /**
     * 发送注册验证码
     *
     * @param mobile
     * @return
     */
    BaseResponse registSendCode(String mobile) throws ExecutionException, InterruptedException;

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
    BaseResponse regist(String mobile, String password, String passWordTwo, String nickName, String remark) throws ExecutionException, InterruptedException;

    /**
     * 登陆
     *
     * @param account
     * @param password
     * @param listener
     */
    void login(String account, String password, DefaultResponseListener<BaseResponse<YyMobileUser>> listener);


    /**
     * 忘记密码短信发送接口
     *
     * @param account
     * @param listener
     */
    void getPasswordCode(String account, DefaultResponseListener<BaseResponse> listener);

    /**
     * 获取新密码接口
     *
     * @param account
     * @param smsCode
     * @param listener
     */
    void getNewPassword(String account, String smsCode, DefaultResponseListener<BaseResponse> listener);

    /**
     * 修改密码接口
     *
     * @param account 登陆手机号 账号
     * @param remark
     * @param password
     * @param passWordTwo
     * @param listener
     */
    void modifyPassword(String account, String remark, String password, String passWordTwo, DefaultResponseListener<BaseResponse> listener);


    /**
     * 上传用户头像
     * 文件上传使用 MultiPartStringRequest  返回结果为字符串类型
     * @param uid
     * @param imgFile
     * @param listener
     */
    void uploadUserHeadImg(String uid, File imgFile, DefaultResponseListener<String> listener);

    /**
     * 设置用户信息
     * @param uid
     * @param picPath
     * @param nickName
     * @param age
     * @param qq
     * @param introduce
     * @param mobileshow
     * @param emailshow
     * @param qqshow
     * @param plshow
     * @param dzshow
     * @param scshow
     * @param address
     * @param listener
     */
    void updateUserInfo(String uid, String picPath, String nickName, String age, String qq, String introduce,
                        String mobileshow, String emailshow, String qqshow, String plshow, String dzshow, String scshow
                        ,String address
            , DefaultResponseListener<BaseResponse> listener);

    /**
     * 意见反馈
     * @param uid
     * @param content
     * @param image1_path
     * @param image2_path
     * @param image3_path
     * @param image4_path
     * @param listener
     */
    void applyUserAdvice(String uid, String content, String image1_path, String image2_path, String image3_path, String image4_path,
                    DefaultResponseListener<BaseResponse> listener);
}
