package com.zl.app.fragment;

import android.content.Intent;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.BaseFragment;
import com.zl.app.R;
import com.zl.app.activity.user.ModifyPasswordActivity_;
import com.zl.app.activity.user.UserInfoActivity_;
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
public class FragmentD extends BaseFragment {


    @ViewById(R.id.img_user_header)
    SimpleDraweeView img_user_header;

    @AfterViews
    void afterViews(){
        String url  = AppConfig.getUserHeadImg(AppManager.getPreferences());
        Uri uri = Uri.parse(RequestURL.SERVER + url);
        img_user_header.setImageURI(uri);
    }

    @Click(R.id.img_user_header)
    void userHeaderClick(){
        Intent intent = new Intent(getActivity(),UserInfoActivity_.class);
        startActivity(intent);
    }

}
