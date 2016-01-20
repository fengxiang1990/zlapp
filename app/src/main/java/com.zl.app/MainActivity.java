package com.zl.app;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;

import com.zl.app.activity.LoginActivity_;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.fragment.FragmentA_;
import com.zl.app.fragment.FragmentB_;
import com.zl.app.fragment.FragmentC_;
import com.zl.app.fragment.FragmentD_;
import com.zl.app.util.ToastUtil;

import org.androidannotations.annotations.AfterViews;
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
    RadioButton radio1;

    @ViewById
    RadioButton radio2;

    @ViewById
    RadioButton radio3;

    @ViewById
    RadioButton radio4;

    BaseFragment fragment_a, fragment_b, fragment_c, fragment_d;

    FragmentManager frgmentManager;

    List<BaseFragment> fragments;

    @AfterViews
    void afterViews() {
        context = MainActivity.this;
        setTitle("主界面");
        setBtnRight1Enable(true);
        setBtnRight2Enable(true);
        setBtnRight1ImageResource(R.mipmap.icon_side_setting_selected);
        setBtnRight2ImageResource(R.mipmap.menu_search);
        frgmentManager = getSupportFragmentManager();
        fragment_a = new FragmentA_();
        fragment_b = new FragmentB_();
        fragment_c = new FragmentC_();
        fragment_d = new FragmentD_();
        FragmentTransaction fragmentTransaction = frgmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, fragment_a);
        fragmentTransaction.commit();

        fragments = new ArrayList<BaseFragment>();
        fragments.add(fragment_a);
        fragments.add(fragment_b);
        fragments.add(fragment_c);
        fragments.add(fragment_d);

        radio1.setChecked(true);
    }

    @Override
    protected void onBtnRight2Click() {
        ToastUtil.show(context, "search btn click");

    }

    @Override
    protected void onBtnRight1Click() {
        ToastUtil.show(context, "setting btn click");
    }


    @Click(R.id.radio1)
    void radio1Click() {
        titleView.setText("tab1");
        switchFragment(frgmentManager.beginTransaction(), fragment_a);
    }

    @Click(R.id.radio2)
    void radio2Click() {
        titleView.setText("tab2");
        switchFragment(frgmentManager.beginTransaction(), fragment_b);
    }

    @Click(R.id.radio3)
    void radio3Click() {
        titleView.setText("tab3");
        switchFragment(frgmentManager.beginTransaction(), fragment_c);
    }

    @Click(R.id.radio4)
    void radio4Click() {
        titleView.setText("tab4");
        Intent intent = new Intent(MainActivity.this, LoginActivity_.class);
        startActivity(intent);
        //switchFragment(frgmentManager.beginTransaction(), fragment_d);
    }

    public void switchFragment(FragmentTransaction fragmentTransaction,
                               BaseFragment fragment) {
        for (BaseFragment objFragment : fragments) {
            if (objFragment.isAdded()) {
                fragmentTransaction.hide(objFragment);
            }
        }
        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.container, fragment);
        } else {
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commit();
    }

}
