package com.zl.app.util;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;

import com.zl.app.data.user.model.YyMobileUser;

public class AppConfig {

    public static final String HTTP_OK = "success";
    public static final String HTTP_ERROR = "error";
    public static final String HTTP_WARN = "warn";

    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    public static final String PREFERENCE_NAME = "APP";
    public static Uri imageUri; // 图片路径
    public static String filename; // 图片名称
    public static String USER_NAME = "username";
    public static String PASS_WORD = "password";
    public static String USER_HEAD_IMG = "user_head_img";
    public static String USER_ID = "user_id";
    public static String USER_UID = "uid";
    public static String MAIL = "mail";
    public static String TEL_PHONE = "tel_phone";
    public static String IS_LOGIN = "isLogin";
    public static String IS_REMEMBER_PASSWORD = "is_remember_password";
    public static String USER_QQ = "user_qq";
    public static String USER_AGE = "user_age";
    public static String USER_INTRODUCE = "user_introduce";
    public static String IS_MOBILE_SHOW ="is_mobile_show";
    public static String IS_EMAIL_SHOW ="is_email_show";
    public static String IS_QQ_SHOW ="is_qq_show";
    public static String IS_PL_SHOW ="is_pl_show";//评论
    public static String IS_DZ_SHOW ="is_dz_show";//点赞
    public static String IS_SC_SHOW ="is_sc_show";//收藏
    public static String DATE = "date";

    public static String IS_SHOW_IMG = "isNoImg";

    //检查是否无图模式
    public static boolean isShowImg(SharedPreferences preference) {
        return preference.getBoolean(AppConfig.IS_SHOW_IMG, true);
    }


    //检查是否登陆
    public static boolean isLogin(SharedPreferences preference) {
        return preference.getBoolean(AppConfig.IS_LOGIN, false);
        //return true;
    }

    //设置为登陆成功
    public static void setLoginSuccess(SharedPreferences preference, YyMobileUser user) {
        Editor editor = preference.edit();
        editor.putBoolean(IS_LOGIN, true);
        editor.putInt(USER_ID, user.getUserId());
        editor.putString(USER_HEAD_IMG, user.getPicPath());
        editor.putString(USER_UID, user.getUid());
        editor.putString(USER_NAME, user.getNickName());
        editor.putString(TEL_PHONE, user.getMobile());
        editor.putString(MAIL, user.getEmail());
        editor.putString(USER_AGE, String.valueOf(user.getAge()));
        editor.putString(USER_QQ, user.getQq());
        editor.putString(USER_INTRODUCE, user.getIntroduce());
        editor.putInt(IS_MOBILE_SHOW, user.getMobileshow());
        editor.putInt(IS_EMAIL_SHOW, user.getEmailshow());
        editor.putInt(IS_QQ_SHOW, user.getQqshow());
        editor.putInt(IS_PL_SHOW,user.getPlshow());
        editor.putInt(IS_DZ_SHOW,user.getDzshow());
        editor.putInt(IS_SC_SHOW,user.getScshow());
        editor.commit();
    }

    //保存更新的用户信息
    public static void saveUpdateInfo(SharedPreferences preference,YyMobileUser user){
        Editor editor = preference.edit();
        editor.putString(USER_HEAD_IMG, user.getPicPath());
        editor.putString(USER_NAME, user.getNickName());
        editor.putString(USER_AGE, String.valueOf(user.getAge()));
        editor.putString(USER_QQ, user.getQq());
        editor.putString(USER_INTRODUCE, user.getIntroduce());
        editor.putInt(IS_MOBILE_SHOW, user.getMobileshow());
        editor.putInt(IS_EMAIL_SHOW, user.getEmailshow());
        editor.putInt(IS_QQ_SHOW, user.getQqshow());
        editor.putInt(IS_PL_SHOW,user.getPlshow());
        editor.putInt(IS_DZ_SHOW,user.getDzshow());
        editor.putInt(IS_SC_SHOW,user.getScshow());
        editor.commit();
    }


    public static int getUserId(SharedPreferences preference) {
        return preference.getInt(AppConfig.USER_ID, 0);
    }

    public static String getUid(SharedPreferences preference) {
        return preference.getString(AppConfig.USER_UID, null);
    }

    public static String getUserHeadImg(SharedPreferences preference) {
        return preference.getString(AppConfig.USER_HEAD_IMG, null);
    }

}
