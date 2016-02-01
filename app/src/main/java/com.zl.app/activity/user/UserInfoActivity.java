package com.zl.app.activity.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.user.UserService;
import com.zl.app.data.user.UserServiceImpl;
import com.zl.app.data.user.model.YyMobileUser;
import com.zl.app.util.AppConfig;
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

    private static final String EDIT = "修改";
    private static final String SAVE = "保存";

    @ViewById(R.id.camera_layout)
    LinearLayout camera_layout;

    @ViewById(R.id.imageView)
    SimpleDraweeView imageView;

    @ViewById(R.id.text_nickname)
    EditText textNickName;

    @ViewById(R.id.text_age)
    EditText textAge;

    @ViewById(R.id.text_qq)
    EditText textQq;

    @ViewById(R.id.text_introduce)
    EditText textIntroduce;

    @ViewById(R.id.check_mobile_show)
    CheckBox check_mobile_show;

    @ViewById(R.id.check_email_show)
    CheckBox check_email_show;

    @ViewById(R.id.check_qq_show)
    CheckBox check_qq_show;

    @ViewById(R.id.check_pl_show)
    CheckBox check_pl_show;

    @ViewById(R.id.check_dz_show)
    CheckBox check_dz_show;

    @ViewById(R.id.check_sc_show)
    CheckBox check_sc_show;

    UserService userService;

    boolean isEditMode = false;
    String nickName = "";
    String qq = "";
    String age = "";
    String introduce = "";
    int is_mobile_show = 0;
    int is_email_show = 0;
    int is_qq_show = 0;
    int is_pl_show = 0;
    int is_dz_show = 0;
    int is_sc_show = 0;

    @AfterViews
    void afterViews() {
        setBtnLeft1Enable(true);
        setTextRight1Enable(true);
        setTextRight1Val(EDIT);
        setTitle(getResources().getString(R.string.user_info));
        userService = new UserServiceImpl();
        String picPath = AppConfig.getUserHeadImg(preference);
        picPath = RequestURL.SERVER + picPath;
        Log.e(tag, picPath);
        Uri uri = Uri.parse(RequestURL.SERVER + picPath);
        imageView.setImageURI(uri);
        nickName = preference.getString(AppConfig.USER_NAME, "");
        age = preference.getString(AppConfig.USER_AGE, "");
        qq = preference.getString(AppConfig.USER_QQ, "");
        introduce = preference.getString(AppConfig.USER_INTRODUCE, "");
        is_mobile_show = preference.getInt(AppConfig.IS_MOBILE_SHOW, 0);
        is_email_show = preference.getInt(AppConfig.IS_EMAIL_SHOW, 0);
        is_qq_show = preference.getInt(AppConfig.IS_QQ_SHOW, 0);
        is_pl_show = preference.getInt(AppConfig.IS_PL_SHOW, 0);
        is_dz_show = preference.getInt(AppConfig.IS_DZ_SHOW, 0);
        is_sc_show = preference.getInt(AppConfig.IS_SC_SHOW, 0);
        textNickName.setText(nickName);
        textAge.setText(age);
        textQq.setText(qq);
        textIntroduce.setText(introduce);
        check_mobile_show.setChecked(is_mobile_show == 1 ? true : false);
        check_email_show.setChecked(is_email_show == 1 ? true : false);
        check_qq_show.setChecked(is_qq_show == 1 ? true : false);
        check_pl_show.setChecked(is_pl_show == 1 ? true : false);
        check_dz_show.setChecked(is_dz_show == 1 ? true : false);
        check_sc_show.setChecked(is_sc_show == 1 ? true : false);
    }

    @Override
    protected void onTextRight1Click() {
        super.onTextRight1Click();
        if (!isEditMode) {
            isEditMode = true;
            setTextRight1Val(SAVE);
            textNickName.setEnabled(true);
            textAge.setEnabled(true);
            textQq.setEnabled(true);
            textIntroduce.setEnabled(true);
            check_mobile_show.setEnabled(true);
            check_email_show.setEnabled(true);
            check_qq_show.setEnabled(true);
            check_pl_show.setEnabled(true);
            check_dz_show.setEnabled(true);
            check_sc_show.setEnabled(true);
        } else {
            isEditMode = false;
            setTextRight1Val(EDIT);
            textNickName.setEnabled(false);
            textAge.setEnabled(false);
            textQq.setEnabled(false);
            textIntroduce.setEnabled(false);
            check_mobile_show.setEnabled(false);
            check_email_show.setEnabled(false);
            check_qq_show.setEnabled(false);
            check_pl_show.setEnabled(false);
            check_dz_show.setEnabled(false);
            check_sc_show.setEnabled(false);
            String picPath = AppConfig.getUserHeadImg(preference);
            String nikeName = String.valueOf(textNickName.getText());
            String age = String.valueOf(textAge.getText());
            String qq = String.valueOf(textQq.getText());
            String introduce = String.valueOf(textIntroduce.getText());
            int is_mobile_show = check_mobile_show.isChecked() ? 1 : 0;
            int is_email_show = check_email_show.isChecked() ? 1 : 0;
            int is_qq_show = check_qq_show.isChecked() ? 1 : 0;
            int is_pl_show = check_pl_show.isChecked() ? 1 : 0;
            int is_dz_show = check_dz_show.isChecked() ? 1 : 0;
            int is_sc_show = check_sc_show.isChecked() ? 1 : 0;
            saveUserInfo(picPath, nikeName, age, qq, introduce, is_mobile_show, is_email_show, is_qq_show, is_pl_show, is_dz_show, is_sc_show);
        }


    }

    @Click(R.id.imageView)
    void showImageSelection() {
        ViewUtil.show(camera_layout);

    }


    @Click(R.id.btn_modify_password)
    void modifyPassword() {
        Intent intent = new Intent(UserInfoActivity.this, ModifyPasswordActivity_.class);
        startActivity(intent);
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
                            nickName = preference.getString(AppConfig.USER_NAME, "");
                            age = preference.getString(AppConfig.USER_AGE, "");
                            qq = preference.getString(AppConfig.USER_QQ, "");
                            introduce = preference.getString(AppConfig.USER_INTRODUCE, "");
                            is_mobile_show = preference.getInt(AppConfig.IS_MOBILE_SHOW, 0);
                            is_email_show = preference.getInt(AppConfig.IS_EMAIL_SHOW, 0);
                            is_qq_show = preference.getInt(AppConfig.IS_QQ_SHOW, 0);
                            is_pl_show = preference.getInt(AppConfig.IS_PL_SHOW, 0);
                            is_dz_show = preference.getInt(AppConfig.IS_DZ_SHOW, 0);
                            is_sc_show = preference.getInt(AppConfig.IS_SC_SHOW, 0);
                            saveUserInfo(response.getMessage(), nickName, age, qq, introduce, is_mobile_show, is_email_show, is_qq_show, is_pl_show, is_dz_show, is_sc_show);
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


    /**
     * @param picPath
     * @param nickName
     * @param age
     * @param qq
     * @param introduce
     * @param isMobileShow
     * @param isEmailShow
     * @param isQQShow
     * @param isPlShow
     * @param isDzShow
     * @param isScShow
     */
    public void saveUserInfo(final String picPath, final String nickName, final String age, final String qq, final String introduce
            , final int isMobileShow, final int isEmailShow, final int isQQShow, final int isPlShow, final int isDzShow, final int isScShow) {
        userService.updateUserInfo(AppConfig.getUid(preference), picPath,
                nickName, age, qq, introduce, String.valueOf(isMobileShow), String.valueOf(isEmailShow), String.valueOf(isQQShow), String.valueOf(isPlShow), String.valueOf(isDzShow), String.valueOf(isScShow), new DefaultResponseListener<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        YyMobileUser user = new YyMobileUser();
                        user.setPicPath(picPath);
                        user.setNickName(nickName);
                        user.setAge(StringUtil.isEmpty(age) ? 0 : Integer.parseInt(age));
                        user.setQq(qq);
                        user.setIntroduce(introduce);
                        user.setMobileshow(isMobileShow);
                        user.setEmailshow(isEmailShow);
                        user.setQqshow(isQQShow);
                        user.setPlshow(isPlShow);
                        user.setDzshow(isDzShow);
                        user.setScshow(isScShow);
                        AppConfig.saveUpdateInfo(preference, user);
                        ToastUtil.show(getApplicationContext(), response.getMessage());
                    }

                    @Override
                    public void onError(VolleyError error) {
                        ToastUtil.show(getApplicationContext(), "修改失败");
                    }
                });
    }
}
