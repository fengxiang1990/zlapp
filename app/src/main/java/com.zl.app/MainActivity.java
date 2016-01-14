package com.zl.app;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import com.zl.app.R;
import com.zl.app.activity.LoginActivity;
import com.zl.app.fragment.FragmentA_;
import com.zl.app.fragment.FragmentB_;
import com.zl.app.fragment.FragmentC_;
import com.zl.app.fragment.FragmentD_;


@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    String TAG = MainActivity.class.getName();

    @ViewById
    RadioButton radio1;

    @ViewById
    RadioButton radio2;

    @ViewById
    RadioButton radio3;

    @ViewById
    RadioButton radio4;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById
    TextView titleView;


    BaseFragment fragment_a, fragment_b, fragment_c, fragment_d;

    FragmentManager frgmentManager;

    List<BaseFragment> fragments;

    @AfterViews
    void afterViews() {
        toolbar.setTitle("");
        titleView.setText("tab1");
        setSupportActionBar(toolbar);
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

    @Click(R.id.right1)
    void right1Click(){
        Toast.makeText(MainActivity.this, "right1 btn click", Toast.LENGTH_SHORT).show();
    }

    @Click(R.id.right2)
    void right2Click(){
        Toast.makeText(MainActivity.this, "right2 btn click", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
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
