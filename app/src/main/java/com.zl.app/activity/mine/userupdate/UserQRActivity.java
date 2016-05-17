package com.zl.app.activity.mine.userupdate;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.user.model.YyMobileUser;
import com.zl.app.util.AppConfig;
import com.zl.app.util.QRUtil;

/**
 * 用户二维码
 * Created by fengxiang on 2016/5/17.
 */
public class UserQRActivity extends BaseActivityWithToolBar{

    ImageView imageView;
    YyMobileUser user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_qr_code);
        imageView = (ImageView) findViewById(R.id.imageView);
        setTitle("二维码名片");
        setBtnLeft1Enable(true);
        user = AppConfig.getUserInfo(preference);
        int width = getWindowManager().getDefaultDisplay().getWidth();
        Bitmap bitmap = QRUtil.createQRImage(String.valueOf(user.getUserId()),width/4*3,width/4*3);
        imageView.setImageBitmap(bitmap);

    }

}
