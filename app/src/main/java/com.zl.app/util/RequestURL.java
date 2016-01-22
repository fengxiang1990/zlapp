package com.zl.app.util;

public class RequestURL {


    //测试接口
    public static String DEBUG_DOMAIN = "http://120.25.69.142/";

    //正式接口
    public static String STANDARD_DOMAIN = "http://www.ziduedu.cn/";


    public static String SERVER = DEBUG_DOMAIN;

    //注册接口
    public static String API_REGIST = SERVER + "mobileUser/register.html";

    //注册验证码
    public static String API_REGIST_CODE = SERVER + "mobileUser/sendSms.html";

    //检测手机号码是否可以注册接口
    public static String API_RIGIST_CHECK = SERVER + "mobileUser/checkUser.html";

    //登录接口
    public static String API_LOGIN = SERVER + "mobileUser/login.html";

    //获取新密码
    public static String API_GET_PASSWORD = SERVER + "mobileUser/getpassword.html";

    //忘记密码短信发送接口
    public static String API_GET_PASSWORD_CODE = SERVER + "mobileUser/sendCode.html";

    //修改密码接口
    public static String API_MODIFY_PASSWORD = SERVER + "mobileUser/modifyPassword.html";

    //上传用户头像接口
    public static String API_USER_UPLOAD = SERVER + "mobileUpload/uploadFile.html";

    //设置用户信息接口
    public static String API_USER_INFO_UPDATE = SERVER + "mobileUser/update.html";


}
