package com.zl.app.activity.mine;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.mine.MineServiceImpl;
import com.zl.app.data.model.user.YyMobileStudent;
import com.zl.app.util.AppConfig;
import com.zl.app.util.GsonUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

/**
 * Created by fengxiang on 2016/4/19.
 */
public class EditChildActivity extends BaseActivityWithToolBar {

    TextView text_name;
    TextView text_id_number;
    TextView text_birthday;
    TextView text_ralationship;
    SimpleDraweeView img_header;
    String childStr;
    String uid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_child);
        setBtnLeft1Enable(true);
        setTitle("我的宝贝");
        uid = AppConfig.getUid(preference);
        img_header = (SimpleDraweeView) findViewById(R.id.img_header);
        text_name = (TextView) findViewById(R.id.text_name);
        text_id_number = (TextView) findViewById(R.id.text_id_number);
        text_birthday = (TextView) findViewById(R.id.text_birthday);
        text_ralationship = (TextView) findViewById(R.id.text_ralationship);
        childStr = getIntent().getStringExtra("child");
        if (!TextUtils.isEmpty(childStr)) {
            YyMobileStudent student1 = GsonUtil.getJsonObject(childStr, YyMobileStudent.class);

            new MineServiceImpl().getChildDetail(uid, student1.getStudentId(), new DefaultResponseListener<BaseResponse<YyMobileStudent>>() {
                @Override
                public void onSuccess(BaseResponse<YyMobileStudent> response) {
                    YyMobileStudent student = response.getResult();

                    if (student != null) {
                        text_name.setText(student.getName());
                        text_id_number.setText(student.getIdCard());
                        text_birthday.setText(student.getBirthday());
                        String relationStr = "";
                        switch (student.getRelation()) {
                            case 1:
                                relationStr = "爸爸";
                                break;
                            case 2:
                                relationStr = "妈妈";
                                break;
                            case 3:
                                relationStr = "爷爷";
                                break;
                            case 4:
                                relationStr = "奶奶";
                                break;
                            case 5:
                                relationStr = "外公";
                                break;
                            case 6:
                                relationStr = "外婆";
                                break;
                        }
                        text_ralationship.setText(relationStr);
                        img_header.setImageURI(Uri.parse(student.getPhoto()));
                    }
                }

                @Override
                public void onError(VolleyError error) {

                }
            });

        }
    }
}
