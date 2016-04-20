package com.zl.app.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.viewpagerindicator.TabPageIndicator;
import com.zl.app.BaseFragment;
import com.zl.app.R;
import com.zl.app.fragment.course.FragmentCourse;
import com.zl.app.fragment.course.FragmentCourse_;
import com.zl.app.util.DateUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程
 * Created by fengxiang on 2016/3/28.
 */
@EFragment(R.layout.fragment_class)
public class FragmentClass extends BaseFragment{

    @ViewById(R.id.pageIndicator)
    TabPageIndicator pageIndicator;

    @ViewById(R.id.viewPager)
    ViewPager viewPager;

    private static final String[] CONTENT = new String[]{"上周", "本周", "下周", "其他"};

    List<FragmentCourse> fragments;
    FragmentCourse courseLast;
    FragmentCourse courseThis;
    FragmentCourse courseNext;
    FragmentCourse courseOther;
    MyPagerAdapter adapter;

    public static Map<String, String> datemap = new HashMap<String, String>();

    @AfterViews
    void afterViews() {
        fragments = new ArrayList<FragmentCourse>();
        courseLast = new FragmentCourse_();
        courseThis = new FragmentCourse_();
        courseNext = new FragmentCourse_();
        courseOther = new FragmentCourse_();
        courseLast.type = -1;
        courseThis.type = 0;
        courseNext.type = 1;
        courseOther.type = 2;
        fragments.add(courseLast);
        fragments.add(courseThis);
        fragments.add(courseNext);
        fragments.add(courseOther);
        adapter = new MyPagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);
        pageIndicator.setViewPager(viewPager);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
        String this_week_1 = df.format(cal.getTime());
        Log.e("this_week_1", this_week_1);
        datemap.put("this_week_1", this_week_1);
        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); //获取本周日的日期
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        String this_week_7 = df.format(cal.getTime());
        Log.e("this_week_7", this_week_7);
        datemap.put("this_week_7", this_week_7);
        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //下周一
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        String next_week_1 = df.format(cal.getTime());
        Log.e("next_week_1", next_week_1);
        datemap.put("next_week_1", next_week_1);
        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); //下周日
        cal.add(Calendar.WEEK_OF_YEAR, 2);
        String next_week_7 = df.format(cal.getTime());
        Log.e("next_week_7", next_week_7);
        datemap.put("next_week_7", next_week_7);
        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //上周一
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        String last_week_1 = df.format(cal.getTime());
        Log.e("last_week_1", last_week_1);
        datemap.put("last_week_1", last_week_1);
        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); //上周日
        String last_week_7 = df.format(cal.getTime());
        Log.e("last_week_7", last_week_7);
        datemap.put("last_week_7", last_week_7);

    }


    class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position % CONTENT.length);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length];
        }


        @Override
        public int getCount() {
            return CONTENT.length;
        }
    }

}
