package com.zl.app.activity.mine.babyupdate;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.model.user.YyMobileStudent;
import com.zl.app.data.user.model.YyMobileUser;
import com.zl.app.util.AppConfig;
import com.zl.app.util.QRUtil;

/**
 * 用户二维码
 * Created by fengxiang on 2016/5/17.
 */
public class BabyQRActivity extends BaseActivityWithToolBar{

    ImageView imageView;
    int id;
    String title;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_qr_code);
        imageView = (ImageView) findViewById(R.id.imageView);
        title = getIntent().getStringExtra("title");
        setTitle(title);
        setBtnLeft1Enable(true);
        int width = getWindowManager().getDefaultDisplay().getWidth();
        id = getIntent().getIntExtra("id",0);
        Bitmap bitmap = QRUtil.createQRImage("http://www.ziluedu.cn/mobileStudent/share.html?student.studentId="+id+"",width/4*3,width/4*3);
        imageView.setImageBitmap(bitmap);
    }

}
