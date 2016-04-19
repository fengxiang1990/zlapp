package com.zl.app.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.user.model.YyMobileUser;
import com.zl.app.model.user.YyMobileStudent;
import com.zl.app.util.AppManager;
import com.zl.app.util.GsonUtil;

/**
 * Created by fengxiang on 2016/4/19.
 */
public class EditChildActivity extends BaseActivityWithToolBar {

    TextView text_name;
    TextView text_id_number;
    TextView text_birthday;
    TextView text_ralationship;
    ImageView img_header;
    String childStr;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_child);
        img_header = (ImageView) findViewById(R.id.img_header);
        text_name = (TextView) findViewById(R.id.text_name);
        text_id_number = (TextView) findViewById(R.id.text_id_number);
        text_birthday = (TextView) findViewById(R.id.text_birthday);
        text_ralationship = (TextView) findViewById(R.id.text_ralationship);
        childStr = getIntent().getStringExtra("child");
        if (!TextUtils.isEmpty(childStr)) {
            YyMobileStudent student = GsonUtil.getJsonObject(childStr, YyMobileStudent.class);
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
                AppManager.getImageLoader().get(student.getPhoto(), new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        img_header.setImageBitmap(response.getBitmap());
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
            }
        }
    }
}
