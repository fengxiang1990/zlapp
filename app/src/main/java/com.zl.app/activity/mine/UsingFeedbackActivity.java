package com.zl.app.activity.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.zl.app.CameraActivity;
import com.zl.app.R;
import com.zl.app.adapter.AdviceImageAdapter;
import com.zl.app.data.user.UserServiceImpl;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.GsonUtil;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.view.LoadingDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CQ on 2016/5/6 0006.
 */

public class UsingFeedbackActivity extends CameraActivity
        implements View.OnClickListener {


    private static final int TAKE_PHOTO = 1;
    private static final int PICK_FROM_ALBUM = 2;

    private MyGridView imageContainer;
    private EditText giveSomeSuggestions;
    private Button sendBtn;
    private RelativeLayout feedBackLayout;

    private AdviceImageAdapter mAdapter;
    private List<AdviceImage> images;

    private static final int MAX_IMAGE = 4;


    ImageView img1;
    ImageView img2;
    ImageView img3;
    ImageView img4;

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

    int index = 1;
    String imgurl1;
    String imgurl2;
    String imgurl3;
    String imgurl4;

    @Override
    protected void onGetImageSuccess(File file) {
        Log.e("UsingFeedbackActivity", file == null ? "file is null" : file.getAbsolutePath());
        LoadingDialog.getInstance(UsingFeedbackActivity.this).setTitle("正在上传图片...").show();
        new UserServiceImpl().uploadUserHeadImg(AppConfig.getUid(preference), file, new DefaultResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                LoadingDialog.getInstance(UsingFeedbackActivity.this).dismiss();
                if (!TextUtils.isEmpty(response)) {
                    BaseResponse baseResponse = GsonUtil.getJsonObject(response, BaseResponse.class);
                    final String imgUrl = baseResponse.getMessage();
                    Log.e("EditChildActivity", "imgUrl--->" + imgUrl);
                    if (index == 1) {
                        imgurl1 = imgUrl;
                        AppManager.getImageLoader().get(imgurl1, new ImageLoader.ImageListener() {
                            @Override
                            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                                img1.setImageBitmap(response.getBitmap());
                            }

                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                    } else if (index == 2) {
                        imgurl2 = imgUrl;
                        AppManager.getImageLoader().get(imgurl2, new ImageLoader.ImageListener() {
                            @Override
                            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                                img2.setImageBitmap(response.getBitmap());
                            }

                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                    } else if (index == 3) {
                        imgurl3 = imgUrl;
                        AppManager.getImageLoader().get(imgurl3, new ImageLoader.ImageListener() {
                            @Override
                            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                                img3.setImageBitmap(response.getBitmap());
                            }

                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                    } else if (index == 4) {
                        imgurl4 = imgUrl;
                        AppManager.getImageLoader().get(imgurl4, new ImageLoader.ImageListener() {
                            @Override
                            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                                img4.setImageBitmap(response.getBitmap());
                            }

                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                    }

                }
            }

            @Override
            public void onError(VolleyError error) {
                ToastUtil.show(UsingFeedbackActivity.this, error.getMessage());
                LoadingDialog.getInstance(UsingFeedbackActivity.this).dismiss();
            }
        });
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
        giveSomeSuggestions = (EditText) findViewById(R.id.give_some_suggestion);
        imageContainer = (MyGridView) findViewById(R.id.image_container_grid_view);
        feedBackLayout = (RelativeLayout) findViewById(R.id.feedback_layout);
        sendBtn = (Button) findViewById(R.id.btn_send);
        giveSomeSuggestions = (EditText) findViewById(R.id.give_some_suggestion);

        mAdapter = new AdviceImageAdapter(this, R.layout.item_grid_view, images);
        imageContainer.setAdapter(mAdapter);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        img1.setClickable(true);
        img2.setClickable(true);
        img3.setClickable(true);
        img4.setClickable(true);

    }

    private void initEvent() {
        sendBtn.setOnClickListener(this);
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        img4.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_send:
                /*点击发送按钮后的逻辑*/
                if (TextUtils.isEmpty(String.valueOf(giveSomeSuggestions.getText()))) {
                    ToastUtil.show(UsingFeedbackActivity.this, "请填写您的建议");
                    return;
                }
                if (imgurl1 == null || imgurl2 == null
                        || imgurl3 == null || imgurl4 == null) {
                    ToastUtil.show(UsingFeedbackActivity.this, "请上传四张图片");
                    return;
                }
                new UserServiceImpl().applyUserAdvice(AppConfig.getUid(preference), String.valueOf(giveSomeSuggestions.getText())
                        , imgurl1, imgurl2, imgurl3, imgurl4, new DefaultResponseListener<BaseResponse>() {
                            @Override
                            public void onSuccess(BaseResponse response) {
                                if (response != null && response.getStatus().equals(AppConfig.HTTP_OK)) {
                                    finish();
                                }
                                ToastUtil.show(UsingFeedbackActivity.this, response.getMessage());
                            }

                            @Override
                            public void onError(VolleyError error) {
                                ToastUtil.show(UsingFeedbackActivity.this, error.getMessage());
                            }
                        });
                break;
            case R.id.img1:
                index = 1;
                popSelectPicture.showAtLocation(feedBackLayout, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.img2:
                index = 2;
                popSelectPicture.showAtLocation(feedBackLayout, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.img3:
                index = 3;
                popSelectPicture.showAtLocation(feedBackLayout, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.img4:
                index = 4;
                popSelectPicture.showAtLocation(feedBackLayout, Gravity.BOTTOM, 0, 0);
                break;
        }

    }
}
