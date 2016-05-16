package com.zl.app.activity.mine;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.mine.MineServiceImpl;
import com.zl.app.data.user.UserServiceImpl;
import com.zl.app.popwindow.PopSelectPicture;
import com.zl.app.util.AppConfig;
import com.zl.app.util.GsonUtil;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by fengxiang on 2016/4/18.
 */
public class AddChildActivity extends BaseActivityWithToolBar implements View.OnClickListener, PopSelectPicture.OnPicturePopClickListener {

    public static final int DEFAULT_IMG_WIDTH = 400;
    private static final int PHOTO_CAMERA = 1;// 拍照
    private static final int PHOTO_ZOOM = 2; // 缩放
    private static final int PHOTO_RESULT = 3;// 结果
    Uri uritempFile, resultUri;

    TextView text_img;
    ImageView img_header;
    LinearLayout root;
    PopSelectPicture popSelectPicture;

    EditText edit_name;
    EditText edit_id_number;
    EditText edit_birthday;
    RadioGroup rg;
    int type = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        root = (LinearLayout) findViewById(R.id.root);
        popSelectPicture = new PopSelectPicture(this);
        popSelectPicture.setOnPicturePopClickListener(this);
        setTitle("新增宝贝");
        setBtnLeft1Enable(true);
        setTextRight1Enable(true);
        setTextRight1Val("保存");
        text_img = (TextView) findViewById(R.id.text_img);
        img_header = (ImageView) findViewById(R.id.img_header);
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_id_number = (EditText) findViewById(R.id.edit_id_number);
        edit_birthday = (EditText) findViewById(R.id.edit_birthday);
        rg = (RadioGroup) findViewById(R.id.rg);
        text_img.setOnClickListener(this);
        img_header.setOnClickListener(this);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.r1:
                        type = 1;
                        break;
                    case R.id.r2:
                        type = 3;
                        break;
                    case R.id.r3:
                        type = 3;
                        break;
                    case R.id.r4:
                        type = 4;
                        break;
                    case R.id.r5:
                        type = 5;
                        break;
                    case R.id.r6:
                        type = 6;
                        break;
                }
            }
        });
    }

    @Override
    protected void onTextRight1Click() {
        super.onTextRight1Click();
        final String name = String.valueOf(edit_name.getText());
        final String idnum = String.valueOf(edit_id_number.getText());
        final String birthday = String.valueOf(edit_birthday.getText());
        if (TextUtils.isEmpty(name)) {
            ToastUtil.show(getApplicationContext(), "孩子姓名不能为空");
            return;
        }
        if (TextUtils.isEmpty(birthday)) {
            ToastUtil.show(getApplicationContext(), "孩子生日不能为空");
            return;
        }
        if (type == 0) {
            ToastUtil.show(getApplicationContext(), "选择与孩子的关系");
            return;
        }

        if (resultUri == null) {
            ToastUtil.show(getApplicationContext(), "请上传孩子头像");
            return;
        }
        //上传

        Bitmap bitmap = getBitmapFromUri(resultUri);
        File file = saveBitmapFile(bitmap);
        Log.e("file", file.getPath());
        new UserServiceImpl().uploadUserHeadImg(AppConfig.getUid(preference), file, new DefaultResponseListener<String>() {

            @Override
            public void onSuccess(String response) {
                if (!TextUtils.isEmpty(response)) {
                    BaseResponse baseResponse = GsonUtil.getJsonObject(response, BaseResponse.class);
                    new MineServiceImpl().addBaby(AppConfig.getUid(preference), baseResponse.getMessage(), name, birthday, idnum, type, new DefaultResponseListener<BaseResponse>() {

                        @Override
                        public void onSuccess(BaseResponse response) {
                            if (response != null) {
                                if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                                    finish();
                                }
                                ToastUtil.show(getApplicationContext(), response.getMessage());
                            }
                        }

                        @Override
                        public void onError(VolleyError error) {

                        }
                    });
                }

            }

            @Override
            public void onError(VolleyError error) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.text_img:
                popSelectPicture.showAtLocation(root, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.img_header:
                popSelectPicture.showAtLocation(root, Gravity.BOTTOM, 0, 0);
                break;
        }
    }


    @Override
    public void onPicturePopClick(int status) {
        switch (status) {
            case PopSelectPicture.OnPicturePopClickListener.TakeCamera:
                getImageFromCamera();
                popSelectPicture.dismiss();
                break;
            case PopSelectPicture.OnPicturePopClickListener.PickFromPics:
                getImageFromAlbum();
                popSelectPicture.dismiss();
                break;

        }
    }


    //相册
    protected void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, PHOTO_ZOOM);
    }

    String imgNameByCamera;

    //相机
    protected void getImageFromCamera() {
        imgNameByCamera = getImageName();
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment
                    .getExternalStorageDirectory(), imgNameByCamera)));
            startActivityForResult(intent, PHOTO_CAMERA);
        } else {
            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri uri = null;
        // 拍照
        if (requestCode == PHOTO_CAMERA) {
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
        if (requestCode == PHOTO_ZOOM) {
            // startPhotoZoom(data.getData());
            if (data != null) {
                uri = data.getData();
            }
        }

        // 处理结果
        if (requestCode == PHOTO_RESULT) {
            img_header.setImageBitmap(getBitmapFromUri(uri));
            resultUri = uri;
        }
        if (uri != null) {
            Log.e("uri", uri.toString());
            img_header.setImageBitmap(getBitmapFromUri(uri));
            resultUri = uri;
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
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        int aspectX = 1;
        int aspectY = 1;
        intent.putExtra("scale", true);
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        int height = DEFAULT_IMG_WIDTH * aspectY / aspectX;
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", DEFAULT_IMG_WIDTH);
        intent.putExtra("outputY", height);
        /**
         * 此方法返回的图片只能是小图片（sumsang测试为高宽160px的图片）
         * 故将图片保存在Uri中，调用时将Uri转换为Bitmap，此方法还可解决miui系统不能return data的问题
         */
        intent.putExtra("return-data", false);
        //uritempFile为Uri类变量，实例化uritempFile
        uritempFile = Uri.fromFile(new File(Environment
                .getExternalStorageDirectory(), getImageName()));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, PHOTO_RESULT);
    }

    private Bitmap getBitmapFromUri(Uri uri) {
        Bitmap bitmap = null;
        try {
            // 读取uri所在的图片
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            Bitmap newbitmap = getSmallBitmap(bitmap);
            return newbitmap;
        } catch (Exception e) {
            Log.e("[Android]", e.getMessage());
            Log.e("[Android]", "目录为：" + uri);
            e.printStackTrace();
            return null;
        } finally {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }


    /**
     * Bitmap缩小的方法
     */
    private static Bitmap getSmallBitmap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(0.2f, 0.2f); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;

    }

    public static String getImageName() {
        return "zlapp_" + System.currentTimeMillis() + ".jpg";
    }


    //Bitmap对象保存味图片文件
    String temp_file_name = "";

    public File saveBitmapFile(Bitmap bitmap) {
        temp_file_name = new Date().getTime() + ".jpg";
        File file = new File(Environment
                .getExternalStorageDirectory() + "/" + temp_file_name);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过Uri返回File文件
     * 注意：通过相机的是类似content://media/external/images/media/97596
     * 通过相册选择的：file:///storage/sdcard0/DCIM/Camera/IMG_20150423_161955.jpg
     * 通过查询获取实际的地址
     *
     * @param uri
     * @return
     */
    public File getFileByUri(Uri uri) {
        String path = null;
        if ("file".equals(uri.getScheme())) {
            path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA}, buff.toString(), null, null);
                int index = 0;
                int dataIdx = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    index = cur.getInt(index);
                    dataIdx = cur.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    path = cur.getString(dataIdx);
                }
                cur.close();
                if (index == 0) {
                } else {
                    Uri u = Uri.parse("content://media/external/images/media/" + index);
                    System.out.println("temp uri is :" + u);
                }
            }
            if (path != null) {
                return new File(path);
            }
        } else if ("content".equals(uri.getScheme())) {
            // 4.2.2以后
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(columnIndex);
            }
            cursor.close();

            return new File(path);
        } else {
            Log.i("add product", "Uri Scheme:" + uri.getScheme());
        }
        return null;
    }

}
