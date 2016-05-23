package com.zl.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.popwindow.PopSelectPicture;
import com.zl.app.util.CameraUtil;

import java.io.File;

/**
 * 用于处理拍照的activity
 * Created by fxa on 2016/5/23.
 */
public abstract class CameraActivity extends BaseActivityWithToolBar implements PopSelectPicture.OnPicturePopClickListener{

    String tag = "CamearActivity";
    static final int DEFAULT_IMG_WH = 400;
    Uri resultUri;
    String imgNameByCamera;
    protected  PopSelectPicture popSelectPicture;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        popSelectPicture = new PopSelectPicture(this);
        popSelectPicture.setOnPicturePopClickListener(this);
    }

    @Override
    public void onPicturePopClick(int status) {
        switch (status) {
            case PopSelectPicture.OnPicturePopClickListener.TakeCamera:
                imgNameByCamera = CameraUtil.getImageName();
                CameraUtil.getImageFromCamera(CameraActivity.this, imgNameByCamera);
                popSelectPicture.dismiss();
                break;
            case PopSelectPicture.OnPicturePopClickListener.PickFromPics:
                CameraUtil.getImageFromAlbum(CameraActivity.this);
                popSelectPicture.dismiss();
                break;
        }
    }

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
            Log.e(tag, "uri--->"+uri.toString());
            resultUri = uri;
        }
        if (resultUri != null) {
            Bitmap bitmap = CameraUtil.getBitmapFromUri(CameraActivity.this, resultUri, DEFAULT_IMG_WH, DEFAULT_IMG_WH);
            File file = CameraUtil.saveBitmapFile(bitmap);
            onGetImageSuccess(file);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    protected abstract void onGetImageSuccess(File file);
}
