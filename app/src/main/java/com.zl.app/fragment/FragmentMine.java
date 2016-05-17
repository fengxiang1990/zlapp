package com.zl.app.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.BaseFragment;
import com.zl.app.R;
import com.zl.app.activity.mine.FrendsActivity;
import com.zl.app.activity.mine.MyChildrenActivity;
import com.zl.app.activity.mine.MyYuyueActivity;
import com.zl.app.activity.mine.OrderActivity;
import com.zl.app.activity.mine.SystemSettingsActivity;
import com.zl.app.activity.mine.UserInfoActivity;
import com.zl.app.data.user.model.YyMobileUser;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * 我的
 * Created by fengxiang on 2016/3/28.
 */
@EFragment(R.layout.fragment_mine)
public class FragmentMine extends BaseFragment implements View.OnClickListener {

    String tag = "FragmentMine";

    @ViewById(R.id.simpleDraweeView)
    SimpleDraweeView simpleDraweeView;

    @ViewById(R.id.text_name)
    TextView text_name;

    @ViewById(R.id.text_info)
    ImageView text_info;

    @ViewById(R.id.text_yuyue)
    ImageView text_yuyue;

    @ViewById(R.id.text_baby)
    ImageView text_baby;

    @ViewById(R.id.text_order)
    ImageView text_order;

    @ViewById(R.id.text_frends)
    ImageView text_frends;
    String uid;

    @ViewById(R.id.text_setting)
    ImageView text_setting;

    YyMobileUser userInfo;
    @AfterViews
    void afterViews() {
        uid = AppConfig.getUid(AppManager.getPreferences());
        text_yuyue.setOnClickListener(this);
        text_baby.setOnClickListener(this);
        text_order.setOnClickListener(this);
        text_info.setOnClickListener(this);
        text_frends.setOnClickListener(this);
        text_setting.setOnClickListener(this);
    }

    public void getUserInfo(){
        userInfo = AppConfig.getUserInfo(AppManager.getPreferences());
        simpleDraweeView.setImageURI(Uri.parse(userInfo.getPicPath()));
        text_name.setText(userInfo.getNickName());

    }

    @Override
    public void onResume(){
        super.onResume();
        getUserInfo();
    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.text_info:
                intent = new Intent(getActivity(), UserInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.text_baby:
                intent = new Intent(getActivity(), MyChildrenActivity.class);
                startActivity(intent);
                break;
            case R.id.text_yuyue:
                intent = new Intent(getActivity(), MyYuyueActivity.class);
                startActivity(intent);
                break;
            case R.id.text_order:
                intent = new Intent(getActivity(), OrderActivity.class);
                startActivity(intent);
                break;
            case R.id.text_frends:
                intent = new Intent(getActivity(), FrendsActivity.class);
                startActivity(intent);
                break;
            case R.id.text_setting:
                intent = new Intent(getActivity(), SystemSettingsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
