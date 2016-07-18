package com.zl.app.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.zl.app.BaseFragment;
import com.zl.app.MyApplication;
import com.zl.app.R;
import com.zl.app.activity.activities.PublishActivity;
import com.zl.app.activity.activities.SearchResultActivity;
import com.zl.app.activity.chart.ChartListActivity;
import com.zl.app.activity.initiation.QuestionAnswerActivity_;
import com.zl.app.activity.org.WebDetailActivity;
import com.zl.app.activity.user.LoginActivity_;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.ChartService;
import com.zl.app.data.user.UserServiceImpl;
import com.zl.app.fragment.FragmentActivities_;
import com.zl.app.fragment.FragmentClass;
import com.zl.app.fragment.FragmentClass_;
import com.zl.app.fragment.FragmentFind_;
import com.zl.app.fragment.FragmentInitiation_;
import com.zl.app.fragment.FragmentMine_;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.PackageUtil;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

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
    RadioButton tab_find;

    @ViewById
    RadioButton tab_class;

    @ViewById
    RadioButton tab_activities;

    @ViewById
    RadioButton tab_mine;

    @ViewById
    RadioButton tab_qimeng;

    @ViewById(R.id.rg)
    RadioGroup radioGroup;

    @App
    MyApplication application;

    public BaseFragment fragment_find;
    public BaseFragment fragment_class;
    public BaseFragment fragment_activities;
    public BaseFragment fragment_mine;
    public BaseFragment fragment_initiation;
    FragmentManager frgmentManager;

    List<BaseFragment> fragments;

    boolean isTeacher = false;

    public static int course_left_btn_resId = R.mipmap.change_t;

    @AfterViews
    void afterViews() {
        context = MainActivity.this;
        AppManager.context = context;
        setTitle("咨路教育");
        setBtnRight2Enable(true);
        setBtnRight2ImageResource(R.mipmap.ac_chart);
        frgmentManager = getSupportFragmentManager();
        fragment_find = new FragmentFind_();
        fragment_class = new FragmentClass_();
        fragment_activities = new FragmentActivities_();
        fragment_mine = new FragmentMine_();
        fragment_initiation = new FragmentInitiation_();
        FragmentTransaction fragmentTransaction = frgmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragments, fragment_find);
        fragmentTransaction.commit();

        fragments = new ArrayList<BaseFragment>();
        fragments.add(fragment_find);
        fragments.add(fragment_class);
        fragments.add(fragment_activities);
        fragments.add(fragment_mine);
        fragments.add(fragment_initiation);
        tab_find.setChecked(true);
        initCourseLeftIcon();
        /**
         new UserServiceImpl().updateJpushId(AppConfig.getUid(preference), AppConfig.JPUSH_ID, new DefaultResponseListener<BaseResponse>() {
        @Override public void onSuccess(BaseResponse response) {
        if(response!=null){
        Log.e(TAG,response.getStatus());
        }
        }

        @Override public void onError(VolleyError error) {
        }
        });**/
        checkUpdate();
    }

    void checkLogin() {
        new UserServiceImpl().checkLogin(AppConfig.getUid(preference), new DefaultResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                Log.e(TAG, "login status-->" + response.getStatus());
                if (response.getStatus().equals(AppConfig.HTTP_ERROR)) {
                    Log.e(TAG, "未登录");
                    if (!AppConfig.getUid(preference).equals("g5601f7f-4b9c-40bf-937e-4740778da12g")) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity_.class);
                        startActivity(intent);
                    }
                } else if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                    Log.e(TAG, "已登录");
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    void checkUpdate() {
        new UserServiceImpl().checkUpdate(new DefaultResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                int currentVersion = PackageUtil.getPackageInfo(MainActivity.this).versionCode;
                int remoteVersion = 0;
                if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                    String targetVersionStr = response.getMessage();
                    if (!TextUtils.isEmpty(targetVersionStr)) {
                        remoteVersion = Integer.parseInt(targetVersionStr);
                    }
                    if (remoteVersion > currentVersion) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("系统提示")
                                .setMessage("检测到有最新版本,是否更新?")
                                .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(MainActivity.this, WebDetailActivity.class);
                                        intent.putExtra("title", "咨路教育");
                                        intent.putExtra("url", "http://ziluedu.net/app/download.html#download");
                                        startActivity(intent);
                                    }
                                }).setNegativeButton("稍后更新", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
                    }
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }


    int newMsgCount = 0;

    void initChartMsgCount() {
        new ChartService().getNewMsgCount(AppConfig.getUid(preference), new DefaultResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                if (response != null && response.getStatus().equals(AppConfig.HTTP_OK)) {
                    newMsgCount = Integer.parseInt(response.getMessage());
                    if (newMsgCount > 0) {
                        if (!fragment_find.isHidden()) {
                            setBtnRight2MsgCountEnable(true);
                            text_msg_count.setText(newMsgCount + "");
                        }
                    } else {
                        setBtnRight2MsgCountEnable(false);
                        text_msg_count.setText("");
                    }
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    void initCourseLeftIcon() {
        final int loginType = AppConfig.getLoginType(preference);
        Log.e(TAG, "loginType-->" + loginType);
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
        if (!AppConfig.getUid(preference).equals("g5601f7f-4b9c-40bf-937e-4740778da12g")) {
            Log.e(TAG, "判断是不是老师");
            new UserServiceImpl().isTeacher(AppConfig.getUid(preference), new DefaultResponseListener<BaseResponse>() {
                @Override
                public void onSuccess(BaseResponse response) {
                    if (response != null) {
                        //是老师 并且当前选中课程页面
                        if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                            isTeacher = true;
                            /**
                             //当前是家长身份进入应用
                             if(loginType == 3){
                             setBtnLeft2ImageResource(R.mipmap.change_t);
                             }
                             if(loginType == 5){
                             setBtnLeft2ImageResource(R.mipmap.change_p);
                             }
                             setBtnLeft2Enable(true);
                             **/
                        }
                    }
                }

                @Override
                public void onError(VolleyError error) {

                }
            });
        }

        return;

    }

    @Override
    protected void onBtnRight2Click() {
        if (AppConfig.isLogin(preference)) {
            Intent intent = new Intent(MainActivity.this, ChartListActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity_.class);
            startActivity(intent);
        }

    }


    @Override
    protected void onBtnRight1Click() {
        if (isqimeng) {
            Intent intent = new Intent(this, QuestionAnswerActivity_.class);
            startActivity(intent);
            return;
        }
        if (AppConfig.isLogin(preference)) {
            Intent intent = new Intent(this, SearchResultActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity_.class);
            startActivity(intent);
        }
    }


    boolean isCurrentTabIsActivity = false;

    @Override
    protected void onBtnLeft2Click() {
        if (isCurrentTabIsActivity) {
            Intent intent = new Intent(MainActivity.this, PublishActivity.class);
            startActivity(intent);
        } else {
            //如果当前登录用户是老师 不用检查 直接切换为普通用户
            if (AppConfig.getLoginType(preference) == 5) {
                SharedPreferences.Editor editor = preference.edit();
                editor.putInt(AppConfig.LOGIN_TYPE, 3);
                editor.commit();
                course_left_btn_resId = R.mipmap.change_t;
                setBtnLeft2ImageResource(course_left_btn_resId);
                ToastUtil.show(MainActivity.this, "已切换为家长");
                setTitle("课程(家长)");
                ((FragmentClass) fragment_class).reloadData();
                return;
            }
            if (AppConfig.getLoginType(preference) == 3) {
                new UserServiceImpl().isTeacher(AppConfig.getUid(preference), new DefaultResponseListener<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        if (response != null) {
                            if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                                SharedPreferences.Editor editor = preference.edit();
                                editor.putInt(AppConfig.LOGIN_TYPE, 5);
                                editor.commit();
                                course_left_btn_resId = R.mipmap.change_p;
                                setBtnLeft2ImageResource(course_left_btn_resId);
                                ToastUtil.show(MainActivity.this, "已切换为老师");
                                setTitle("课程(老师)");
                                ((FragmentClass) fragment_class).reloadData();
                            }
                            //  ToastUtil.show(MainActivity.this, response.getMessage());
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
                return;
            }
        }
    }


    @Click(R.id.tab_find)
    void radiol1Click() {
        if (newMsgCount > 0) {
            setBtnRight2MsgCountEnable(true);
        } else {
            setBtnRight2MsgCountEnable(false);
        }
        setBtnRight2Enable(true);
        setBtnLeft1Enable(false);
        setTitle("咨路教育");
        setBtnRight1Enable(false);
        setBtnLeft2Enable(false);
        switchFragment(frgmentManager.beginTransaction(), fragment_find);
    }


    @Click(R.id.tab_class)
    void radio1Click() {
        isqimeng = false;
        isCurrentTabIsActivity = false;
        if (AppConfig.getLoginType(preference) == 3) {
            setTitle("课程(家长)");
            if (isTeacher) {
                setBtnLeft2Enable(true);
            } else {
                setBtnLeft2Enable(false);
            }

        } else if (AppConfig.getLoginType(preference) == 5) {
            setTitle("课程(老师)");
            setBtnLeft2Enable(true);
        }
        setBtnRight2Enable(false);
        setBtnLeft1Enable(false);
        setBtnRight1Enable(false);
        setBtnLeft2ImageResource(course_left_btn_resId);
        if (AppConfig.isLogin(preference)) {
            switchFragment(frgmentManager.beginTransaction(), fragment_class);
        } else {
            setTitle("咨路教育");
            tab_find.setChecked(true);
            setBtnLeft1Enable(false);
            setBtnLeft2Enable(false);
            setBtnRight1Enable(false);
            setBtnRight2Enable(true);
            Intent intent = new Intent(MainActivity.this, LoginActivity_.class);
            startActivity(intent);
        }
    }

    @Click(R.id.tab_activities)
    void radio2Click() {
        isCurrentTabIsActivity = true;
        isqimeng = false;
        setBtnLeft1Enable(false);
        setBtnRight1Enable(true);
        setBtnLeft2Enable(true);
        setBtnRight2Enable(false);
        setBtnLeft2ImageResource(R.mipmap.ic_publish_activity);
        setBtnRight1ImageResource(R.mipmap.find_search_icon);
        setTitle("我的活动");
        if (AppConfig.isLogin(preference)) {
            switchFragment(frgmentManager.beginTransaction(), fragment_activities);
        } else {
            setTitle("咨路教育");
            tab_find.setChecked(true);
            setBtnLeft1Enable(false);
            setBtnLeft2Enable(false);
            setBtnRight1Enable(false);
            setBtnRight2Enable(true);
            Intent intent = new Intent(MainActivity.this, LoginActivity_.class);
            startActivity(intent);
        }
    }

    boolean isqimeng = false;

    @Click(R.id.tab_qimeng)
    void radio5Click() {
        setTitle("启蒙研究院");
        setBtnLeft1Enable(false);
        setBtnLeft2Enable(false);
        setBtnRight2Enable(false);
        setBtnRight1Enable(true);
        isqimeng = true;
        setBtnRight1ImageResource(R.mipmap.ic_qa);
        switchFragment(frgmentManager.beginTransaction(), fragment_initiation);
    }

    @Click(R.id.tab_mine)
    void radio4Click() {
        setTitle(" ");
        isqimeng = false;
        setBtnLeft1Enable(false);
        setBtnRight1Enable(false);
        setBtnLeft2Enable(false);
        setBtnRight2Enable(false);
        if (AppConfig.isLogin(preference)) {
            switchFragment(frgmentManager.beginTransaction(), fragment_mine);
        } else {
            setTitle("咨路教育");
            tab_find.setChecked(true);
            setBtnLeft1Enable(false);
            setBtnLeft2Enable(false);
            setBtnRight1Enable(false);
            setBtnRight2Enable(true);
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
        // checkLogin();
        if (!AppConfig.getUid(preference).equals("g5601f7f-4b9c-40bf-937e-4740778da12g")) {
            initChartMsgCount();
        }
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


    private long mExitTime;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Object mHelperUtils;
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                throw  new NullPointerException("exit");
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
