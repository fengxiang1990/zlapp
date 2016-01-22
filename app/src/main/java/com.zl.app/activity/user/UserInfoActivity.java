package com.zl.app.activity.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.user.UserService;
import com.zl.app.data.user.UserServiceImpl;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.RequestURL;
import com.zl.app.util.StringUtil;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.ViewUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by admin on 2016/1/22.
 */
@EActivity(R.layout.activity_user_info)
public class UserInfoActivity extends BaseActivityWithToolBar {

    String tag = "UserInfoActivity";

    private static final int NONE = 0;
    private static final int PHOTO_CAMERA = 1;// 拍照
    private static final int PHOTO_ZOOM = 2; // 缩放
    private static final int PHOTO_RESOULT = 3;// 结果
    private static final String IMAGE_UNSPECIFIED = "image/*";

    @ViewById(R.id.camera_layout)
    LinearLayout camera_layout;

    @ViewById(R.id.imageView)
    ImageView imageView;

    UserService userService;

    @AfterViews
    void afterViews() {
        userService = new UserServiceImpl();
        String picPath = AppConfig.getUserHeadImg(preference);
        picPath = RequestURL.SERVER + picPath;
        Log.i(tag,picPath);
        if(!StringUtil.isEmpty(picPath)){
            AppManager.getImageLoader().get(picPath, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    imageView.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }

    @Click(R.id.imageView)
    void showImageSelection() {
        ViewUtil.show(camera_layout);

    }

    @Click(R.id.btn_take_camera)
    void takeCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment
                .getExternalStorageDirectory(), "temp.jpg")));
        startActivityForResult(intent, PHOTO_CAMERA);
    }

    @Click(R.id.btn_pick)
    void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
        startActivityForResult(intent, PHOTO_ZOOM);
    }

    @Click(R.id.cancel)
    void cancel() {
        ViewUtil.hide(camera_layout);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == NONE)
            return;
        // 拍照
        if (requestCode == PHOTO_CAMERA) {
            // 设置文件保存路径
            File picture = new File(Environment.getExternalStorageDirectory()
                    + "/temp.jpg");
            startPhotoZoom(Uri.fromFile(picture));
        }

        if (data == null)
            return;

        // 读取相册缩放图片
        if (requestCode == PHOTO_ZOOM) {
            startPhotoZoom(data.getData());
        }
        // 处理结果
        if (requestCode == PHOTO_RESOULT) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 60, stream);// (0-100)压缩文件
                imageView.setImageBitmap(photo);
                try {
                    String base64Str = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
                    userService.uploadUserHeadImg(AppConfig.getUid(preference), base64Str, new DefaultResponseListener<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse response) {
                            ToastUtil.show(getApplicationContext(), response.getMessage());
                            userService.updateUserInfo(AppConfig.getUid(preference), response.getMessage(),
                                    "", "", "", "", "", "", "", "", "", "", new DefaultResponseListener<BaseResponse>() {
                                        @Override
                                        public void onSuccess(BaseResponse response) {
                                           // ToastUtil.show(getApplicationContext(), response.getMessage());
                                        }

                                        @Override
                                        public void onError(VolleyError error) {

                                        }
                                    });
                        }

                        @Override
                        public void onError(VolleyError error) {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 收缩图片
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_RESOULT);
    }


}
