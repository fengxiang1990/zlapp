package com.zl.app.activity.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.R;
import com.zl.app.activity.mine.userupdate.UpdateAddressActivity;
import com.zl.app.activity.mine.userupdate.UpdateQianMingActivity;
import com.zl.app.activity.mine.userupdate.UpdateUserNameActivity;
import com.zl.app.activity.mine.userupdate.UserQRActivity;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.user.model.YyMobileUser;
import com.zl.app.util.AppConfig;

/**
 * Created by fengxiang on 2016/4/25.
 */
public class UserInfoActivity extends BaseActivityWithToolBar implements View.OnClickListener {

    SimpleDraweeView img_header;
    TextView text_name;
    TextView text_area;
    TextView text_address;
    TextView text_qianming;
    YyMobileUser userInfo;
    ImageView img_qr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        setTitle("个人资料");
        setBtnLeft1Enable(true);
        userInfo = AppConfig.getUserInfo(preference);
        img_header = (SimpleDraweeView) findViewById(R.id.img_header);
        text_name = (TextView) findViewById(R.id.text_name);
        text_area = (TextView) findViewById(R.id.text_area);
        text_address = (TextView) findViewById(R.id.text_address);
        text_qianming = (TextView) findViewById(R.id.text_qianming);
        img_qr = (ImageView) findViewById(R.id.img_qr);
        img_header.setOnClickListener(this);
        text_address.setOnClickListener(this);
        text_area.setOnClickListener(this);
        text_name.setOnClickListener(this);
        text_qianming.setOnClickListener(this);
        img_qr.setOnClickListener(this);
    }

    public void getUserInfo(){
        userInfo = AppConfig.getUserInfo(preference);
        img_header.setImageURI(Uri.parse(userInfo.getPicPath()));
        text_name.setText(userInfo.getNickName());
        text_area.setText(userInfo.getDistrict());
        text_address.setText(userInfo.getAddress());
        text_qianming.setText(userInfo.getIntroduce());
    }
    @Override
    public void onResume(){
        super.onResume();
        getUserInfo();
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent;
        switch (v.getId()) {
            case R.id.text_name:
                intent = new Intent(UserInfoActivity.this, UpdateUserNameActivity.class);
                startActivity(intent);
                break;
            case R.id.text_qianming:
                intent = new Intent(UserInfoActivity.this, UpdateQianMingActivity.class);
                startActivity(intent);
                break;
            case R.id.text_address:
                intent = new Intent(UserInfoActivity.this, UpdateAddressActivity.class);
                startActivity(intent);
                break;
            case R.id.img_qr:
                intent = new Intent(UserInfoActivity.this, UserQRActivity.class);
                startActivity(intent);
                break;
        }
    }
}
