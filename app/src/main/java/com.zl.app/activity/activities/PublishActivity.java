package com.zl.app.activity.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.android.volley.VolleyError;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.ActivityService;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.DateUtil;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.view.LoadingDialog;
import com.zl.app.view.datetimepicker.DateTimePickDialogUtil;

import java.util.Date;

/**
 * Created by fengxiang on 2016/4/25.
 */
public class PublishActivity extends BaseActivityWithToolBar  implements View.OnClickListener {

    EditText edit_title;
    EditText edit_location;
    EditText edit_date;
    RadioButton rb1,rb2;
    Button btn_send;
    int ispublic = 2; //默认公开
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_publish);
        setTitle("发起新活动");
        setBtnLeft1Enable(true);
        edit_title = (EditText) findViewById(R.id.edit_title);
        edit_location = (EditText) findViewById(R.id.edit_location);
        edit_date = (EditText) findViewById(R.id.edit_date);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
        btn_send = (Button) findViewById(R.id.btn_send);
        rb1.setOnClickListener(this);
        rb2.setOnClickListener(this);
        btn_send.setOnClickListener(this);
        edit_date.setOnClickListener(this);
    }

    public void sendActivity(){
         String title = String.valueOf(edit_title.getText());
         String location = String.valueOf(edit_location.getText());
         String date = String.valueOf(edit_date.getText());
        if(TextUtils.isEmpty(title)){
            ToastUtil.show(getApplicationContext(),"活动标题不能为空");
            return;
        }
        if(TextUtils.isEmpty(location)){
            ToastUtil.show(getApplicationContext(),"活动地点不能为空");
            return;
        }
        if(TextUtils.isEmpty(date)){
            ToastUtil.show(getApplicationContext(),"活动时间不能为空");
            return;
        }
        LoadingDialog.getInstance(PublishActivity.this).show();
        new ActivityService().sendActivity(AppConfig.getUid(preference),date,title,location,ispublic, new DefaultResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                if(response!=null){
                    ToastUtil.show(PublishActivity.this,response.getMessage());
                    if(response.getStatus().equals(AppConfig.HTTP_OK)){
                        finish();
                    }
                }
                LoadingDialog.getInstance(PublishActivity.this).dismiss();
            }

            @Override
            public void onError(VolleyError error) {
                LoadingDialog.getInstance(PublishActivity.this).dismiss();
            }
        });
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_send:
                sendActivity();
                break;
            case R.id.rb1:
                ispublic = 2;
                break;
            case R.id.rb2:
                ispublic = 3;
                break;
            case R.id.edit_date:
                showDateTimePickerDialog();
                break;
        }
    }

    private void showDateTimePickerDialog() {
        Date date = new Date();
        DateTimePickDialogUtil dateTimePicker = new DateTimePickDialogUtil(
                PublishActivity.this, DateUtil.DateToString(date, DateUtil.DateStyle.YYYY_MM_DD_HH_MM));
        dateTimePicker.dateTimePickDialog(edit_date);
    }

}
