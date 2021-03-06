package com.zl.app.activity;

import android.content.Intent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.zl.app.BaseActivity;
import com.zl.app.R;
import com.zl.app.util.AppConfig;
import com.zl.app.activity.user.LoginActivity_;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 启动页面
 * Created by admin on 2016/1/12.
 */
@EActivity(R.layout.activity_start)
public class StartActivity extends BaseActivity {

    private final String tag = StartActivity.class.getName();

    @ViewById(R.id.img_bg)
    ImageView imageView;

    String imgUrl = "http://b.hiphotos.baidu.com/zhidao/pic/item/d058ccbf6c81800a586adb34b23533fa828b4778.jpg";


    @AfterViews
    void afterViews() {
        final AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 1.0f);
        alphaAnimation.setDuration(2000);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                //if (AppConfig.isLogin(preference)) {
                    intent.setClass(StartActivity.this, MainActivity_.class);
               // } else {
               //     intent.setClass(StartActivity.this, LoginActivity_.class);
               // }
                startActivity(intent);
                StartActivity.this.finish();
            }
        });
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.startAnimation(alphaAnimation);
        /**
         AppManager.getImageLoader().get(imgUrl, new ImageLoader.ImageListener() {

        @Override public void onErrorResponse(VolleyError error) {
        Log.i(tag, error.getMessage() == null ? "" : error.getMessage());
        }

        @Override public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
        Bitmap bitmap = response.getBitmap();
        if (bitmap != null) {
        imageView.setImageBitmap(bitmap);
        imageView.startAnimation(alphaAnimation);
        }
        }
        });
         **/
    }
}
