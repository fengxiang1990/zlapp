package com.zl.app.fragment;

import android.content.Intent;

import com.zl.app.BaseFragment;
import com.zl.app.R;
import com.zl.app.activity.user.ModifyPasswordActivity_;
import com.zl.app.activity.user.UserInfoActivity_;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

/**
 * Created by admin on 2015/12/28.
 */

@EFragment(R.layout.fragment_d)
public class FragmentD extends BaseFragment {



    @Click(R.id.btn_modify_password)
    void modifyPassword() {
        Intent intent = new Intent(getActivity(), ModifyPasswordActivity_.class);
        startActivity(intent);
    }

    @Click(R.id.imageView)
    void toUserInfo(){
        Intent intent = new Intent(getActivity(),UserInfoActivity_.class);
        startActivity(intent);
    }

}
