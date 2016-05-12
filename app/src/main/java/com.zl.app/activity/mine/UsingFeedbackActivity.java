package com.zl.app.activity.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zl.app.R;
import com.zl.app.adapter.AdviceImageAdapter;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.popwindow.PopSelectPicture;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CQ on 2016/5/6 0006.
 */

public class UsingFeedbackActivity extends BaseActivityWithToolBar
        implements View.OnClickListener,
        PopSelectPicture.OnPicturePopClickListener, AdapterView.OnItemClickListener{


    private static final int TAKE_PHOTO = 1;
    private static final int PICK_FROM_ALBUM = 2;

    private MyGridView imageContainer;
    private EditText giveSomeSuggestions;
    private Button sendBtn;
    private RelativeLayout feedBackLayout;
    private PopSelectPicture popSelectPicture;

    private Uri imageUri;
    private String imageName;
    private File imageFile;

    private AdviceImageAdapter mAdapter;
    private List<AdviceImage> images;

    private static final int MAX_IMAGE = 4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using_feedback);
        setBtnLeft1Enable(true);
        setTitle("使用反馈");

        initData();
        initView();
        initEvent();

    }

    private void initData() {
        images = new ArrayList<AdviceImage>();
        /*第一张图片对象完成初始化并放入images中*/
        AdviceImage image = new AdviceImage();
        image.setBitmap(null);
        image.setResourceId(R.mipmap.add_image);
        images.add(image);
    }

    private void initView() {
        imageContainer = (MyGridView)findViewById(R.id.image_container_grid_view);
        feedBackLayout = (RelativeLayout)findViewById(R.id.feedback_layout);
        sendBtn = (Button)findViewById(R.id.btn_send);
        popSelectPicture = new PopSelectPicture(this);
        giveSomeSuggestions = (EditText)findViewById(R.id.give_some_suggestion);

        mAdapter = new AdviceImageAdapter(this, R.layout.item_grid_view, images);
        imageContainer.setAdapter(mAdapter);
    }

    private void initEvent() {
        imageContainer.setOnItemClickListener(this);
        sendBtn.setOnClickListener(this);
        popSelectPicture.setOnPicturePopClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == (images.size() - 1)){
            popSelectPicture.showAtLocation(feedBackLayout, Gravity.BOTTOM, 0, 0);
        }else {
            return;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_send:
                /*点击发送按钮后的逻辑*/
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
                        refresh(getBitmapFromUri(imageUri));
                    }
                    break;
                case TAKE_PHOTO:
                    if (resultCode == RESULT_OK){
                        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
                        int image_width = screenWidth / 4 - 10;
                        Bitmap bitmap = getScaleBitmap(imageFile.getPath(), 10, 10);
                        bitmap = ThumbnailUtils.extractThumbnail(bitmap, image_width, image_width);
                        refresh(bitmap);
                    }
                    break;
            }

    }

    /**
     * 将拍照或相册获取的bitmap传入，设置到点击item的中
     * 将下一个item显示为默认图片
     * 刷新适配器
     * @param bitmap
     */
    private void refresh(Bitmap bitmap) {
        images.get(images.size() - 1)
                .setResourceId(0);
        images.get(images.size() - 1)
                .setBitmap(bitmap);

        AdviceImage image = new AdviceImage();
        image.setBitmap(null);
        if (images.size() < MAX_IMAGE) {
            image.setResourceId(R.mipmap.add_image);
        }else {
            image.setResourceId(0);
        }
        images.add(image);

        mAdapter.notifyDataSetChanged();
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
            int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
            int image_width = screenWidth / 4 - 10;
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            Bitmap smallBitmap = ThumbnailUtils.extractThumbnail(bitmap, image_width, image_width);
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
