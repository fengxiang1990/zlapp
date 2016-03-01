package com.zl.app.activity;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zl.app.BaseFragment;
import com.zl.app.MyApplication;
import com.zl.app.R;
import com.zl.app.activity.user.LoginActivity_;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.fragment.FragmentActivities_;
import com.zl.app.fragment.FragmentHome_;
import com.zl.app.fragment.FragmentMessage_;
import com.zl.app.fragment.FragmentSetting;
import com.zl.app.fragment.FragmentSetting_;
import com.zl.app.fragment.FragmentSite_;
import com.zl.app.util.AppConfig;
import com.zl.app.util.ToastUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;


@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivityWithToolBar {

    String TAG = MainActivity.class.getName();
    Context context = null;

    @ViewById
    RadioButton radiol1;

    @ViewById
    RadioButton radiol2;

    @ViewById
    RadioButton radioHome;

    @ViewById
    RadioButton radioActivities;

    @ViewById
    RadioButton radioSetting;

    @ViewById(R.id.rg)
    RadioGroup radioGroup;

    @App
    MyApplication application;

    public BaseFragment fragment_site,fragment_message,fragment_home, fragment_activities, fragment_setting;

    FragmentManager frgmentManager;

    List<BaseFragment> fragments;


    @AfterViews
    void afterViews() {
        context = MainActivity.this;
        setTitle("首页");
        //首页新闻显示侧滑
        setBtnLeft1Enable(true);
        setBtnRight1Enable(false);
        setBtnRight2Enable(true);
        setBtnRight1ImageResource(R.mipmap.icon_side_setting_selected);
        setBtnRight2ImageResource(R.mipmap.menu_search);
        frgmentManager = getSupportFragmentManager();
        fragment_site = new FragmentSite_();
        fragment_message = new FragmentMessage_();
        fragment_home = new FragmentHome_();
        fragment_activities = new FragmentActivities_();
        fragment_setting = new FragmentSetting_();
        FragmentTransaction fragmentTransaction = frgmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragments, fragment_home);
        fragmentTransaction.commit();

        fragments = new ArrayList<BaseFragment>();
        fragments.add(fragment_home);
        fragments.add(fragment_activities);
        fragments.add(fragment_setting);
        fragments.add(fragment_site);
        fragments.add(fragment_message);

        radioHome.setChecked(true);
    }

    @Override

    protected void onBtnRight2Click() {
        ToastUtil.show(context, "search btn click");
        application.mLocationClient.start();

    }


    @Override
    protected void onBtnRight1Click() {
        ToastUtil.show(context, "setting btn click");
    }


    @Override
    protected void onBtnLeft1Click() {
        // drawerLayout.openDrawer(Gravity.LEFT);
    }

    @Click(R.id.radioHome)
    void radio1Click() {
        setTitle("首页");
        setBtnLeft1Enable(true);
        switchFragment(frgmentManager.beginTransaction(), fragment_home);
    }

    @Click(R.id.radioActivities)
    void radio2Click() {
        setTitle("活动");
        setBtnLeft1Enable(false);
        switchFragment(frgmentManager.beginTransaction(), fragment_activities);
    }

    @Click(R.id.radioSetting)
    void radio4Click() {
        setTitle("设置");
        setBtnLeft1Enable(false);
        if (AppConfig.isLogin(preference)) {
            switchFragment(frgmentManager.beginTransaction(), fragment_setting);
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity_.class);
            startActivity(intent);
        }
    }

    public void switchFragment(FragmentTransaction fragmentTransaction,
                               BaseFragment fragment) {
        for (BaseFragment objFragment : fragments) {
            if (objFragment.isAdded()) {
                fragmentTransaction.hide(objFragment);
            }
        }
        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.fragments, fragment);
        } else {
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (AppConfig.isLogin(preference)) {
            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.radiol1:
                    switchFragment(frgmentManager.beginTransaction(), fragment_site);
                    break;
                case R.id.radiol2:
                    switchFragment(frgmentManager.beginTransaction(), fragment_message);
                    break;
                case R.id.radioHome:
                    switchFragment(frgmentManager.beginTransaction(), fragment_home);
                    break;
                case R.id.radioActivities:
                    switchFragment(frgmentManager.beginTransaction(), fragment_activities);
                    break;
                case R.id.radioSetting:
                    switchFragment(frgmentManager.beginTransaction(), fragment_setting);
                    break;
            }
        }
    }

}
