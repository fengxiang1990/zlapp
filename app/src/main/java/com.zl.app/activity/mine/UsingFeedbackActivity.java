package com.zl.app.activity.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.popwindow.PopSelectPicture;

import java.io.File;
import java.io.IOException;

/**
 * Created by CQ on 2016/5/6 0006.
 */

public class UsingFeedbackActivity extends BaseActivityWithToolBar
        implements View.OnClickListener,PopSelectPicture.OnPicturePopClickListener{


    private static final int TAKE_PHOTO = 1;
    private static final int PICK_FROM_ALBUM = 2;
    private EditText giveSomeSuggestions;
    private PopSelectPicture popSelectPicture;
    private ImageView pictureToInsert1;
    private ImageView pictureToInsert2;
    private ImageView pictureToInsert3;
    private ImageView pictureToInsert4;
    private ImageView pictureToInsert5;
    private ImageView pictureToInsert6;
    private ImageView pictureToInsert7;
    private ImageView pictureToInsert8;
    private LinearLayout feedbackLayout;
    private Uri imageUri;
    private String imageName;
    private File imageFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using_feedback);
        setBtnLeft1Enable(true);
        setTitle(R.string.using_feedback);

        feedbackLayout = (LinearLayout) findViewById(R.id.feedback_layout);
        popSelectPicture = new PopSelectPicture(this);
        giveSomeSuggestions = (EditText)findViewById(R.id.give_some_suggestion);
        pictureToInsert1 = (ImageView)findViewById(R.id.image_to_insert1);
        pictureToInsert2 = (ImageView)findViewById(R.id.image_to_insert2);
        pictureToInsert3 = (ImageView)findViewById(R.id.image_to_insert3);
        pictureToInsert4 = (ImageView)findViewById(R.id.image_to_insert4);
        pictureToInsert5 = (ImageView)findViewById(R.id.image_to_insert5);
        pictureToInsert6 = (ImageView)findViewById(R.id.image_to_insert6);
        pictureToInsert7 = (ImageView)findViewById(R.id.image_to_insert7);
        pictureToInsert8 = (ImageView)findViewById(R.id.image_to_insert8);

        pictureToInsert1.setOnClickListener(this);
        pictureToInsert2.setOnClickListener(this);
        pictureToInsert3.setOnClickListener(this);
        pictureToInsert4.setOnClickListener(this);
        pictureToInsert5.setOnClickListener(this);
        pictureToInsert6.setOnClickListener(this);
        pictureToInsert7.setOnClickListener(this);
        pictureToInsert8.setOnClickListener(this);
        popSelectPicture.setOnPicturePopClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_to_insert1:
                popSelectPicture.showAtLocation(feedbackLayout, Gravity.BOTTOM, 0, 0);
                break;


        }

    }
    /**
     * 实现了OnPicturePopClickListener的onPicturePopClick方法，
     * 如此一来，弹出窗被点击时，就能回调该方法执行相应逻辑
     */
    @Override
    public void onPicturePopClick(int status) {
        switch (status){
            case PopSelectPicture.OnPicturePopClickListener.TakeCamera:
                getImageFromCamera();
                popSelectPicture.dismiss();
                break;
            case PopSelectPicture.OnPicturePopClickListener.PickFromPics:
                getImageFromPick();
                popSelectPicture.dismiss();
                break;
            default:
                break;
        }

    }
    /**
     * 从系统选择照片
     */
    private void getImageFromPick() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }
    /**
     * 调用系统相机拍照
     */
    private void getImageFromCamera() {
        imageName = System.currentTimeMillis()+".jpg";
        String sdState = Environment.getExternalStorageState();
        if (sdState.equals(Environment.MEDIA_MOUNTED)) {
            imageFile = new File(Environment.getExternalStorageDirectory(), imageName);
            try{
                imageFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            startActivityForResult(intent, TAKE_PHOTO);
        }else {
            Toast.makeText(getApplicationContext(), "Read failed,please check your SD card.",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode){
                case PICK_FROM_ALBUM:
                    if (resultCode == RESULT_OK) {
                        imageUri = data.getData();
                        pictureToInsert1.setImageBitmap(getBitmapFromUri(imageUri));
                    }
                    break;
                case TAKE_PHOTO:
                    if (resultCode == RESULT_OK){
                        Bitmap bitmap = getScaleBitmap(imageFile.getPath(), 40, 40);
                        pictureToInsert1.setImageBitmap(bitmap);
                    }
                    break;
            }

    }
    /**
     *为了防止直接加载图片产生OOM，将图片进行适当压缩
     */
    private Bitmap getScaleBitmap(String path, int reWidth, int reHeight) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, opt);

        int width = opt.outWidth;
        int height = opt.outHeight;
        opt.inSampleSize = 1;

        opt.inSampleSize = width/reWidth > height/reHeight ? width/reWidth : height/reHeight;
        if (opt.inSampleSize > 1){
            opt.inSampleSize = 1;
        }

        opt.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(path, opt);
        return bitmap;
    }
    /**
     * 根据图片uri解析出bitmap对象
     */
    private Bitmap getBitmapFromUri(Uri imageUri) {
        Bitmap bitmap = null;
        try{
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            Bitmap smallBitmap = getSmallBitmap(bitmap);
            return smallBitmap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            if (bitmap != null&&(!bitmap.isRecycled())){
                bitmap.recycle();
            }
        }
    }

    private Bitmap getSmallBitmap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(0.2f, 0.2f);
        Bitmap smallBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return smallBitmap;
    }


}
