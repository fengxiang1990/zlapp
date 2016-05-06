package com.zl.app.activity.course;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.CourseService;
import com.zl.app.model.customer.YyMobilePeriod;
import com.zl.app.util.AppConfig;
import com.zl.app.util.GsonUtil;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

/**
 * Created by fengxiang on 2016/5/6.
 */
public class CourseMessageActivity extends BaseActivityWithToolBar implements View.OnClickListener {

    ListView listView;
    TextView textTime;
    TextView textTeacher;
    TextView textSend;
    EditText editMsg;
    LinearLayout ll2;
    CourseService courseService;
    YyMobilePeriod yyMobilePeriod;
    int periodId;
    String uid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_inner_msg);
        initView();
        initEvent();
        initData();
        loadData();
    }

    public void initData() {
        setBtnLeft1Enable(true);
        uid = AppConfig.getUid(preference);
        courseService = new CourseService();
        yyMobilePeriod = GsonUtil.getJsonObject(getIntent().getStringExtra("course"), YyMobilePeriod.class);
        if (yyMobilePeriod != null) {
            periodId = yyMobilePeriod.getPeriodId();
            setTitle(yyMobilePeriod.getClassname());
        }
    }

    public void initView() {
        listView = (ListView) findViewById(R.id.listView5);
        textTime = (TextView) findViewById(R.id.text_time);
        textTeacher = (TextView) findViewById(R.id.text_teacher);
        textSend = (TextView) findViewById(R.id.text_send);
        editMsg = (EditText) findViewById(R.id.edit_msg);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
    }

    public void initEvent() {
        textSend.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    public void loadData() {

    }


    int yyuserId = 0;
    String image1, image2, image3, image4;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.text_send:
                String content = String.valueOf(editMsg.getText());
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.show(CourseMessageActivity.this, "内容不能为空");
                    return;
                }
                Log.e("uid", uid);
                Log.e("periodId", periodId + "");
                courseService.sendCourseMessage(uid, periodId, content, yyuserId, image1, image2, image3, image4, new DefaultResponseListener<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        if (response != null) {
                            if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                                editMsg.setText("");
                            }
                            ToastUtil.show(CourseMessageActivity.this, response.getMessage());
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
                break;
        }
    }
}
