package com.zl.app.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.BaseFragment;
import com.zl.app.R;
import com.zl.app.activity.user.ModifyPasswordActivity_;
import com.zl.app.activity.user.UserInfoActivity_;
import com.zl.app.data.user.UserService;
import com.zl.app.data.user.UserServiceImpl;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.RequestURL;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by admin on 2015/12/28.
 */

@EFragment(R.layout.fragment_d)
public class FragmentSetting extends BaseFragment {
    String tag = "FragmentSetting";

    @ViewById(R.id.simpleDraweeView)
    SimpleDraweeView simpleDraweeView;

    @ViewById(R.id.text_nickname)
    EditText textNickName;

    @ViewById(R.id.text_age)
    EditText textAge;

    @ViewById(R.id.text_qq)
    EditText textQq;

    @ViewById(R.id.text_mobile)
    EditText textMobile;

    @ViewById(R.id.text_email)
    EditText textEmail;

    @ViewById(R.id.text_introduce)
    EditText textIntroduce;

    @ViewById(R.id.check_mobile_show)
    CheckBox check_mobile_show;

    @ViewById(R.id.check_email_show)
    CheckBox check_email_show;

    @ViewById(R.id.check_qq_show)
    CheckBox check_qq_show;

    @ViewById(R.id.check_pl_show)
    CheckBox check_pl_show;

    @ViewById(R.id.check_dz_show)
    CheckBox check_dz_show;

    @ViewById(R.id.check_sc_show)
    CheckBox check_sc_show;

    UserService userService;

    boolean isEditMode = false;
    String nickName = "";
    String qq = "";
    String age = "";
    String mobile = "";
    String email = "";
    String introduce = "";
    int is_mobile_show = 0;
    int is_email_show = 0;
    int is_qq_show = 0;
    int is_pl_show = 0;
    int is_dz_show = 0;
    int is_sc_show = 0;

    SharedPreferences preference;

    @AfterViews
    void afterViews() {
        preference = AppManager.getPreferences();
        userService = new UserServiceImpl();
        String picPath = AppConfig.getUserHeadImg(preference);
        picPath = RequestURL.SERVER + picPath;
        Log.e(tag, picPath);
        Uri uri = Uri.parse(picPath);
        simpleDraweeView.setImageURI(uri);
        mobile = preference.getString(AppConfig.TEL_PHONE, "");
        email = preference.getString(AppConfig.MAIL, "");
        nickName = preference.getString(AppConfig.USER_NAME, "");
        age = preference.getString(AppConfig.USER_AGE, "");
        qq = preference.getString(AppConfig.USER_QQ, "");
        introduce = preference.getString(AppConfig.USER_INTRODUCE, "");
        is_mobile_show = preference.getInt(AppConfig.IS_MOBILE_SHOW, 0);
        is_email_show = preference.getInt(AppConfig.IS_EMAIL_SHOW, 0);
        is_qq_show = preference.getInt(AppConfig.IS_QQ_SHOW, 0);
        is_pl_show = preference.getInt(AppConfig.IS_PL_SHOW, 0);
        is_dz_show = preference.getInt(AppConfig.IS_DZ_SHOW, 0);
        is_sc_show = preference.getInt(AppConfig.IS_SC_SHOW, 0);
        textNickName.setText(nickName);
        textAge.setText(age);
        textQq.setText(qq);
        textMobile.setText(mobile);
        textEmail.setText(email);
        textIntroduce.setText(introduce);
        check_mobile_show.setChecked(is_mobile_show == 1 ? true : false);
        check_email_show.setChecked(is_email_show == 1 ? true : false);
        check_qq_show.setChecked(is_qq_show == 1 ? true : false);
        check_pl_show.setChecked(is_pl_show == 1 ? true : false);
        check_dz_show.setChecked(is_dz_show == 1 ? true : false);
        check_sc_show.setChecked(is_sc_show == 1 ? true : false);
    }


    @Click(R.id.simpleDraweeView)
    void showImageSelection() {
        Intent intent = new Intent(getActivity(),UserInfoActivity_.class);
        startActivity(intent);
    }

    @Click(R.id.btn_modify_password)
    void modifyPassword() {
        Intent intent = new Intent(getActivity(), ModifyPasswordActivity_.class);
        startActivity(intent);
    }

}
