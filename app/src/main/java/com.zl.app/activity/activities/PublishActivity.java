package com.zl.app.activity.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;

/**
 * Created by fengxiang on 2016/4/25.
 */
public class PublishActivity extends BaseActivityWithToolBar {

    EditText edit_date;
    LinearLayout lldate;
    DatePicker datePicker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_publish);
        edit_date = (EditText) findViewById(R.id.edit_date);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        lldate = (LinearLayout) findViewById(R.id.lldate);
        edit_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    lldate.setVisibility(View.VISIBLE);
                } else {
                    lldate.setVisibility(View.GONE);
                }

            }
        });
        lldate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lldate.setVisibility(View.GONE);
            }
        });
    }
}
