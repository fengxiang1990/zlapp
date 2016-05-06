package com.zl.app.util;

public class RequestURL {


    //测试接口
    public static String DEBUG_DOMAIN = "http://120.25.69.142/";

    //正式接口
    public static String STANDARD_DOMAIN = "http://www.ziluedu.cn/";


    public static String SERVER = STANDARD_DOMAIN;

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

    //首页广告
    public static String API_HOME_AD = SERVER +"mobileAdvt/home.html";

    //首页新闻
    public static String API_HOME_NEWS = SERVER + "mobileNews/home.html";

    //新闻分类
    public static String API_NEWS_TYPE = SERVER + "mobileNews/type.html";

    //新闻列表
    public static String API_NEWS_LIST = SERVER + "mobileNews/list.html";

    //新闻详情
    public static String API_NEWS_DETAIL = SERVER + "mobileNews/view.html";

    //评论列表
    public static String API_NEWS_COMMENTS = SERVER + "mobileNews/commentlist.html";

    //提交评论
    public static String API_NEWS_COMMENT_SUBMIT = SERVER + "mobileNews/comment.html";

    //点赞
    public static String API_NEWS_GOOD = SERVER + "mobileNews/good.html";

    //收藏
    public static String API_NEWS_FAVORITE= SERVER + "mobileNews/favorite.html";

    //根据是否显示（手机、email、qq、评论、收藏、点赞），来显示相应信息。
    public static String API_SITE_USER = SERVER+"mobileSite/user.html";

    //检查是否老师
    public static String API_CHECK_TEACHER = SERVER + "mobileUser/checkTeacher.html";

    //个人微站留言列表接口
    public static String API_SITE_USER_COMMENTS = SERVER +"mobileSite/usercommentlist.html";

    //个人微站提交留言接口
    public static String API_SITE_SUBMIT_USER_COMMENT = SERVER + "mobileSite/usercomment.html";

    //个人发表的文章列表接口
    public static String API_SITE_MY_NEWS = SERVER +"mobileSite/userCreateNews.html";

    //个人浏览的文章列表接口
    public static String API_SITE_READ_NEWS = SERVER + "mobileSite/userReadNews.html";

    //个人收藏的文章列表接口
    public static String API_SITE_FAVORITE_NEWS = SERVER +"mobileSite/userFavoriteNews.html";

    //个人点赞的文章列表接口
    public static String API_SITE_ZAN_NEWS = SERVER  + "mobileSite/userGoodNews.html";

    //个人关注的个人用户列表接口
    public static String API_SITE_USER_FANS = SERVER + "mobileSite/userfans.html";

    //关注接口
    public static String API_SITE_FAN= SERVER + "mobileUser/fans.html";

    //机构
    public static String API_HOME_TUIJIAN =SERVER +"mobileCompany/home.html";

    public static String API_ORG_LIST = SERVER + "mobileCompany/list.html";

    //机构微站接口
    public static String API_ORG_SITE = SERVER + "mobileCompany/view.html";

    //机构微站评价列表接口
    public static String API_ORG_GRADE_COMMENTS = SERVER + "mobileCompanyGrade/list.html";

    //提交机构评价接口
    public static String API_ORG_GRADE_POST_COMMENT = SERVER + "mobileCompanyGrade/insert.html";

    //机构留言列表接口
    public static String API_ORG_SITE_COMMENTS = SERVER + "mobileCompanyComment/list.html";

    //发布机构留言接口
    public static String API_ORG_SITE_SEND_COMMENTS = SERVER + "mobileCompanyComment/insert.html";

    //提交预约接口
    public static String API_ORG_SITE_YUYUE = SERVER + "mobileReservation/insert.html";

    //我的预约
    public static String API_MY_YUYUE = SERVER + "mobileReservation/list.html";

    //新增宝贝信息
    public static String API_ADD_BABY = SERVER + "mobileStudent/insert.html";

    //获取我的孩子信息
    public static String API_BABY_LIST = SERVER + "mobileStudent/list.html";

    //获取我的课程信息（家长）
    public static String API_COURSE_PLIST = SERVER + "mobilePeriod/plist.html";

    //获取我的课程信息（老师）
    public static String API_COURSE_TLIST = SERVER + "mobilePeriod/tlist.html";

    //获取课程学生动态
    public static String API_COURSE_STUDENT_DT = SERVER + "mobilePeriod/students.html";

    //提交课程学生动态
    public static String API_SUBMIT_COURSE_STUDENT_DT = SERVER + "mobilePeriod/pupdate.html";

    //获取订单
    public static String API_ORDER_LIST = SERVER + "mobileContract/list.html";

    //获取朋友
    public static String API_FREND_LIST = SERVER + "mobileUser/friends.html";

    //发布新活动
    public static String API_SEND_ACTIVITY = SERVER + "mobileActivity/insert.html";

    //老师审核学生动态
    public static String API_TEACHER_CHECKED = SERVER + "mobilePeriod/tupdate.html";

    //获取城市
    public static String API_CITY_LIST = SERVER + "mobileCity/list.html";

    //获取机构分类
    public static String API_ORG_TYPE_LIST = SERVER + "mobileCompany/type.html";

    //发送课程评论
    public static String API_COURSE_MSG_SEND = SERVER + "mobilePeriod/send.html";

    //课程评论列表
    public static String API_COURSE_MSG_LIST = SERVER + "mobilePeriodBbs/list.html";




}
