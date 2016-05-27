package com.zl.app.activity.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.R;
import com.zl.app.activity.mine.userupdate.UpdateAddressActivity;
import com.zl.app.activity.mine.userupdate.UpdateQianMingActivity;
import com.zl.app.activity.mine.userupdate.UpdateUserNameActivity;
import com.zl.app.activity.mine.userupdate.UserQRActivity;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.user.UserServiceImpl;
import com.zl.app.data.user.model.YyMobileUser;
import com.zl.app.popwindow.PopSelectPicture;
import com.zl.app.util.AppConfig;
import com.zl.app.util.CameraUtil;
import com.zl.app.util.GsonUtil;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.view.LoadingDialog;

import java.io.File;

/**
 * Created by fengxiang on 2016/4/25.
 */
public class UserInfoActivity extends BaseActivityWithToolBar implements View.OnClickListener,
        PopSelectPicture.OnPicturePopClickListener {

    static final int DEFAULT_IMG_WH = 400;
    SimpleDraweeView img_header;
    TextView text_name;
    TextView text_area;
    TextView text_address;
    TextView text_qianming;
    YyMobileUser userInfo;
    ImageView img_qr;
    View root;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        setTitle("个人资料");
        setBtnLeft1Enable(true);
        popSelectPicture = new PopSelectPicture(this);
        popSelectPicture.setOnPicturePopClickListener(this);
        userInfo = AppConfig.getUserInfo(preference);
        root = findViewById(R.id.root);
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

    public void getUserInfo() {
        userInfo = AppConfig.getUserInfo(preference);
        img_header.setImageURI(Uri.parse(userInfo.getPicPath()));
        text_name.setText(userInfo.getNickName());
        text_area.setText(userInfo.getDistrict());
        text_address.setText(userInfo.getAddress());
        text_qianming.setText(userInfo.getIntroduce());
    }

    @Override
    public void onResume() {
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
                intent.putExtra("text",userInfo.getNickName());
                startActivity(intent);
                break;
            case R.id.text_qianming:
                intent = new Intent(UserInfoActivity.this, UpdateQianMingActivity.class);
                intent.putExtra("text",userInfo.getIntroduce());
                startActivity(intent);
                break;
            case R.id.text_address:
                intent = new Intent(UserInfoActivity.this, UpdateAddressActivity.class);
                intent.putExtra("text",userInfo.getAddress());
                startActivity(intent);
                break;
            case R.id.img_qr:
                intent = new Intent(UserInfoActivity.this, UserQRActivity.class);
                startActivity(intent);
                break;
            case R.id.img_header:
                popSelectPicture.showAtLocation(root, Gravity.BOTTOM, 0, 0);
                break;
        }
    }


    String imgNameByCamera;
    PopSelectPicture popSelectPicture;

    @Override
    public void onPicturePopClick(int status) {
        switch (status) {
            case PopSelectPicture.OnPicturePopClickListener.TakeCamera:
                imgNameByCamera = CameraUtil.getImageName();
                CameraUtil.getImageFromCamera(UserInfoActivity.this, imgNameByCamera);
                popSelectPicture.dismiss();
                break;
            case PopSelectPicture.OnPicturePopClickListener.PickFromPics:
                CameraUtil.getImageFromAlbum(UserInfoActivity.this);
                popSelectPicture.dismiss();
                break;
        }
    }

    Uri resultUri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri uri = null;
        // 拍照
        if (requestCode == CameraUtil.PHOTO_CAMERA) {
            // 设置文件保存路径
            File picture = new File(Environment.getExternalStorageDirectory() + "/" + imgNameByCamera);
            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");
                if (bitmap != null) {
                    uri = Uri.fromFile(picture);
                }
            } else {
                if (picture.exists()) {
                    uri = Uri.fromFile(picture);
                }
            }
        }
        // 读取相册缩放图片
        if (requestCode == CameraUtil.PHOTO_ALBUM) {
            if (data != null) {
                uri = data.getData();
            }
        }
        // 处理结果
        if (requestCode == CameraUtil.PHOTO_RESULT) {
            resultUri = uri;
        }
        if (uri != null) {
            Log.e("uri", uri.toString());
            resultUri = uri;
        }
        if (resultUri != null) {
            //上传压缩后的图片
            //Bitmap bitmap = CameraUtil.getBitmapFromUri(DetailActivity.this, resultUri);
            //File file = CameraUtil.saveBitmapFile(bitmap);
            //上传原图(超过500kb 无法上传)
            //File file = CameraUtil.getFileByUri(DetailActivity.this,resultUri);
            //上传指定大小的图片
            Bitmap bitmap = CameraUtil.getBitmapFromUri(UserInfoActivity.this, resultUri, DEFAULT_IMG_WH, DEFAULT_IMG_WH);
            File file = CameraUtil.saveBitmapFile(bitmap);
            LoadingDialog.getInstance(UserInfoActivity.this).setTitle("正在上传头像...").show();
            new UserServiceImpl().uploadUserHeadImg(AppConfig.getUid(preference), file, new DefaultResponseListener<String>() {
                @Override
                public void onSuccess(String response) {
                    if (!TextUtils.isEmpty(response)) {
                        BaseResponse baseResponse = GsonUtil.getJsonObject(response, BaseResponse.class);
                        final String imgUrl = baseResponse.getMessage();
                        Log.e("UserInfoActivity", "imgUrl--->" + imgUrl);
                        new UserServiceImpl().updateUserInfo(AppConfig.getUid(preference), imgUrl,
                                null, null, null, null, null, null, null, null, null, null,
                                null, new DefaultResponseListener<BaseResponse>() {

                                    @Override
                                    public void onSuccess(BaseResponse response) {
                                        if (response != null && response.getStatus().equals(AppConfig.HTTP_OK)) {
                                            img_header.setImageURI(Uri.parse(imgUrl));
                                            YyMobileUser user = AppConfig.getUserInfo(preference);
                                            user.setPicPath(imgUrl);
                                            AppConfig.saveUpdateInfo(preference, user);
                                            LoadingDialog.getInstance(UserInfoActivity.this).dismiss();
                                            ToastUtil.show(UserInfoActivity.this, response.getMessage());
                                        }
                                    }

                                    @Override
                                    public void onError(VolleyError error) {
                                        LoadingDialog.getInstance(UserInfoActivity.this).dismiss();
                                        ToastUtil.show(UserInfoActivity.this, error.getMessage());
                                    }
                                });
                    }
                }

                @Override
                public void onError(VolleyError error) {
                    ToastUtil.show(UserInfoActivity.this, error.getMessage());
                    LoadingDialog.getInstance(UserInfoActivity.this).dismiss();
                }
            });
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
