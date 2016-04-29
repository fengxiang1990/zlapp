package com.zl.app.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.VolleyError;
import com.zl.app.BaseFragment;
import com.zl.app.MyApplication;
import com.zl.app.R;
import com.zl.app.activity.activities.PublishActivity;
import com.zl.app.activity.activities.SearchActivity;
import com.zl.app.activity.user.LoginActivity_;
import com.zl.app.activity.user.UserInfoActivity_;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.user.UserService;
import com.zl.app.data.user.UserServiceImpl;
import com.zl.app.fragment.FragmentActivities_;
import com.zl.app.fragment.FragmentClass;
import com.zl.app.fragment.FragmentClass_;
import com.zl.app.fragment.FragmentFind;
import com.zl.app.fragment.FragmentFind_;
import com.zl.app.fragment.FragmentHome;
import com.zl.app.fragment.FragmentMine_;
import com.zl.app.fragment.FragmentSetting;
import com.zl.app.fragment.course.FragmentCourse;
import com.zl.app.fragment.course.FragmentCourse_;
import com.zl.app.util.AppConfig;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EditorAction;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;


@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivityWithToolBar {

    String TAG = MainActivity.class.getName();
    Context context = null;

    @ViewById
    RadioButton tab_find;

    @ViewById
    RadioButton tab_class;

    @ViewById
    RadioButton tab_activities;

    @ViewById
    RadioButton tab_mine;

    @ViewById(R.id.rg)
    RadioGroup radioGroup;

    @App
    MyApplication application;

    public BaseFragment  fragment_find;
    public BaseFragment  fragment_class;
    public BaseFragment  fragment_activities;
    public BaseFragment  fragment_mine;

    FragmentManager frgmentManager;

    List<BaseFragment> fragments;


    public static int course_left_btn_resId = R.mipmap.change_t;


    @AfterViews
    void afterViews() {
        context = MainActivity.this;
        setTitle("首页");
        //首页新闻显示侧滑
        //setBtnLeft1Enable(false);
        //setBtnLeft1ImageResource(R.mipmap.ic_more);
        //setBtnRight1Enable(true);
        //setBtnRight2Enable(true);
       // setBtnRight1ImageResource(R.mipmap.icon_side_setting_selected);
        //setBtnRight2ImageResource(R.mipmap.menu_search);
        frgmentManager = getSupportFragmentManager();
        fragment_find = new FragmentFind_();
        fragment_class = new FragmentClass_();
        fragment_activities = new FragmentActivities_();
        fragment_mine = new FragmentMine_();
        FragmentTransaction fragmentTransaction = frgmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragments, fragment_find);
        fragmentTransaction.commit();

        fragments = new ArrayList<BaseFragment>();
        fragments.add(fragment_find);
        fragments.add(fragment_class);
        fragments.add(fragment_activities);
        fragments.add(fragment_mine);
        tab_find.setChecked(true);
        initCourseLeftIcon();
    }

    void initCourseLeftIcon() {
        int loginType = AppConfig.getLoginType(preference);
        switch (loginType) {
            case 3:
                //当前是家长登录
                course_left_btn_resId = R.mipmap.change_t;
                break;
            case 5:
                //当前是教师登录
                course_left_btn_resId = R.mipmap.change_p;
                break;
        }
    }

    @Override
    protected void onBtnRight2Click() {
        ToastUtil.show(context, "search btn click");
        application.mLocationClient.start();

    }


    @Override
    protected void onBtnRight1Click() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onBtnLeft1Click() {
        new UserServiceImpl().isTeacher(AppConfig.getUid(preference), new DefaultResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                if (response != null) {
                    if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                        SharedPreferences.Editor editor = preference.edit();
                        editor.putInt(AppConfig.LOGIN_TYPE, 5);
                        editor.commit();
                        for (FragmentCourse fragment : ((FragmentClass) fragment_class).fragments) {
                            fragment = new FragmentCourse_();
                        }
                    }
                    ToastUtil.show(MainActivity.this, response.getMessage());
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    @Override
    protected void onTextLeft1Click() {
        Intent intent = new Intent(MainActivity.this, PublishActivity.class);
        startActivity(intent);
    }

    @Click(R.id.tab_find)
    void radiol1Click() {
        setBtnLeft1Enable(false);
        setTitle("发现");
        setTextLeft1Enable(false);
        setBtnRight1Enable(false);
        switchFragment(frgmentManager.beginTransaction(), fragment_find);
    }


    @Click(R.id.tab_class)
    void radio1Click() {
        setTitle("课程");
        setBtnLeft1Enable(true);
        setTextLeft1Enable(false);
        setBtnRight1Enable(false);
        setBtnLeft1ImageResource(course_left_btn_resId);
        switchFragment(frgmentManager.beginTransaction(), fragment_class);
    }

    @Click(R.id.tab_activities)
    void radio2Click() {
        setBtnLeft1Enable(false);
        setTextLeft1Val("发起活动");
        setTextLeft1Enable(true);
        setBtnRight1Enable(true);
        setBtnRight1ImageResource(R.mipmap.ic_search_icon);
        setTitle("活动");
        switchFragment(frgmentManager.beginTransaction(), fragment_activities);
    }

    @Click(R.id.tab_mine)
    void radio4Click() {
        setTitle("我的");
        setBtnLeft1Enable(false);
        setTextLeft1Enable(false);
        setBtnRight1Enable(false);
        if (AppConfig.isLogin(preference)) {
            switchFragment(frgmentManager.beginTransaction(), fragment_mine);
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
                case R.id.tab_find:
                    switchFragment(frgmentManager.beginTransaction(), fragment_find);
                    break;
                case R.id.tab_class:
                    switchFragment(frgmentManager.beginTransaction(), fragment_class);
                    break;
                case R.id.tab_activities:
                    switchFragment(frgmentManager.beginTransaction(), fragment_activities);
                    break;
                case R.id.tab_mine:
                    switchFragment(frgmentManager.beginTransaction(), fragment_mine);
                    break;
            }
        }
    }

}
