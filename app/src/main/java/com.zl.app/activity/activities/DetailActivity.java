package com.zl.app.activity.activities;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.ActivityService;
import com.zl.app.model.activity.YyMobileActivity;
import com.zl.app.util.AppConfig;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.view.LoadingDialog;

/**
 * Created by fengxiang on 2016/4/28.
 */
public class DetailActivity extends BaseActivityWithToolBar {

    SimpleDraweeView simpleDraweeView;
    TextView text_name;
    TextView text_join;
    TextView text_location;
    TextView text_time;
    TextView text_content;
    TextView text_count;

    String uid;
    int activityId;
    ActivityService activityService;
    YyMobileActivity activity;
    String title;
    int isjoin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_detail);
        uid = AppConfig.getUid(preference);
        activityService = new ActivityService();
        activityId = getIntent().getIntExtra("activityId", 0);
        title = getIntent().getStringExtra("title");
        setTitle(title);
        setBtnLeft1Enable(true);
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.simpleDraweeView);
        text_name = (TextView) findViewById(R.id.text_name);
        text_join = (TextView) findViewById(R.id.text_join);
        text_location = (TextView) findViewById(R.id.text_location);
        text_time = (TextView) findViewById(R.id.text_time);
        text_content = (TextView) findViewById(R.id.text_content);
        text_count = (TextView) findViewById(R.id.text_count);
        loadData();

        text_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isjoin == 2) {
                    activityService.unjoinActivity(uid, activityId + "", new DefaultResponseListener<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse response) {
                            if (response != null) {
                                if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                                    loadData();
                                }
                                ToastUtil.show(DetailActivity.this, response.getMessage());
                            }
                        }

                        @Override
                        public void onError(VolleyError error) {

                        }
                    });
                } else if (isjoin == 3) {
                    activityService.joinActivity(uid, activityId + "", new DefaultResponseListener<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse response) {
                            if (response != null) {
                                if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                                    loadData();
                                }
                                ToastUtil.show(DetailActivity.this, response.getMessage());
                            }
                        }

                        @Override
                        public void onError(VolleyError error) {

                        }
                    });
                }

            }
        });
    }

    public void loadData() {
        LoadingDialog.getInstance(this).show();
        activityService.getHdDetail(uid, String.valueOf(activityId), new DefaultResponseListener<BaseResponse<YyMobileActivity>>() {
            @Override
            public void onSuccess(BaseResponse<YyMobileActivity> response) {
                LoadingDialog.getInstance(DetailActivity.this).dismiss();
                if (response != null && response.getResult() != null) {
                    activity = response.getResult();
                    isjoin = activity.getIsjoin();
                    if (isjoin == 2) {
                        text_join.setText("取消报名");
                    } else if (isjoin == 3) {
                        text_join.setText("报名参加");
                    }
                    simpleDraweeView.setImageURI(Uri.parse(activity.getPicPath()));
                    text_name.setText("发起人:" + activity.getUsername());
                    text_content.setText(activity.getHeadline());
                    text_location.setText(activity.getAddress());
                    text_time.setText(activity.getHdDate());
                    text_count.setText(activity.getYbrs() + "");
                }
            }

            @Override
            public void onError(VolleyError error) {
                LoadingDialog.getInstance(DetailActivity.this).dismiss();
            }
        });
    }
}
