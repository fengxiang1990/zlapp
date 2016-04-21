package com.zl.app.activity.course;

import android.os.Bundle;
import android.widget.TextView;

import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.model.customer.YyMobilePeriod;
import com.zl.app.util.GsonUtil;

/**
 * 班级学生动态 家长
 * Created by fengxiang on 2016/4/21.
 */
public class CoursePDTActivity extends BaseActivityWithToolBar {

    TextView text_time;
    TextView text_teacher;
    YyMobilePeriod yyMobilePeriod;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_p);
        text_time = (TextView) findViewById(R.id.text_time);
        text_teacher = (TextView) findViewById(R.id.text_teacher);
        yyMobilePeriod = GsonUtil.getJsonObject(getIntent().getStringExtra("course"), YyMobilePeriod.class);
        if (yyMobilePeriod != null) {
            setTitle(yyMobilePeriod.getClassname());
            text_time.setText(yyMobilePeriod.getClasstime());
            text_teacher.setText(yyMobilePeriod.getTeachername());
        }
    }
}
