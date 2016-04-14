package com.zl.app.activity.org;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.android.volley.VolleyError;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.home.HomeService;
import com.zl.app.data.home.HomeServiceImpl;
import com.zl.app.util.AppConfig;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

/**
 * Created by fengxiang on 2016/4/14.
 */
public class OrgGradePostActivity extends BaseActivityWithToolBar {

    RatingBar ratingBar;
    EditText editText;
    Button btn1;

    HomeService homeService;
    String uid;
    String companyId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_grade_comment);
        setTitle("评价机构");
        setBtnLeft1Enable(true);
        uid = AppConfig.getUid(preference);
        companyId = getIntent().getStringExtra("companyId");
        homeService = new HomeServiceImpl();
        ratingBar = (RatingBar) findViewById(R.id.ratingBar2);
        editText = (EditText) findViewById(R.id.editText);
        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String content = String.valueOf(editText.getText());
                int score = (int) (ratingBar.getRating() * 2);
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.show(OrgGradePostActivity.this, "评价内容不能为空");
                    return;
                }
                if (score == 0) {
                    ToastUtil.show(OrgGradePostActivity.this, "请给机构打分");
                    return;
                }
                homeService.postOrgGrade(uid, companyId, String.valueOf(score), content, new DefaultResponseListener<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        if (response != null) {
                            if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                                ToastUtil.show(OrgGradePostActivity.this, response.getMessage());
                                finish();
                            } else {
                                ToastUtil.show(OrgGradePostActivity.this, response.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
            }
        });
    }
}
