package com.zl.app.activity.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.ActivityService;
import com.zl.app.data.user.UserService;
import com.zl.app.data.user.UserServiceImpl;
import com.zl.app.data.model.activity.YyMobileActivity;
import com.zl.app.data.model.activity.YyMobileActivityComment;
import com.zl.app.popwindow.PopSelectPicture;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.CameraUtil;
import com.zl.app.util.GsonUtil;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.view.LoadingDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengxiang on 2016/4/28.
 */
public class DetailActivity extends BaseActivityWithToolBar implements PopSelectPicture.OnPicturePopClickListener {

    static final int DEFAULT_IMG_WH = 800;
    SimpleDraweeView simpleDraweeView;
    TextView text_name;
    TextView text_join;
    TextView text_location;
    TextView text_time;
    TextView text_content;
    TextView text_count, text_comment_count;
    EditText edit_content;
    TextView text_comment;
    ListView listView;
    ImageView img_take_pic;
    LinearLayout ll_comment;
    LinearLayout ll_imgs;
    ImageView img1;
    ImageView img_close;
    String uid;
    int activityId;
    ActivityService activityService;
    UserService userService;
    YyMobileActivity activity;
    String title;
    int isjoin;
    List<YyMobileActivityComment> data;
    MyAdapter adapter;
    PopSelectPicture popSelectPicture;

    View headerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_detail);
        setBtnRight1Enable(true);
        setBtnRight1ImageResource(R.mipmap.ac_join_member);
        uid = AppConfig.getUid(preference);
        userService = new UserServiceImpl();
        headerView = LayoutInflater.from(this).inflate(R.layout.activity_activity_detail_header,null);
        data = new ArrayList<YyMobileActivityComment>();
        popSelectPicture = new PopSelectPicture(this);
        popSelectPicture.setOnPicturePopClickListener(this);
        adapter = new MyAdapter(data);
        activityService = new ActivityService();
        activityId = getIntent().getIntExtra("activityId", 0);
        title = getIntent().getStringExtra("title");
        setTitle(title);
        setBtnLeft1Enable(true);
        listView = (ListView) findViewById(R.id.listView);
        listView.addHeaderView(headerView);
        listView.setAdapter(adapter);
        simpleDraweeView = (SimpleDraweeView) headerView.findViewById(R.id.simpleDraweeView);
        text_name = (TextView) headerView.findViewById(R.id.text_name);
        text_join = (TextView)headerView. findViewById(R.id.text_join);
        text_location = (TextView)headerView. findViewById(R.id.text_location);
        text_time = (TextView) headerView.findViewById(R.id.text_time);
        text_count = (TextView) headerView.findViewById(R.id.text_count);
        text_content = (TextView)headerView. findViewById(R.id.text_content);
        edit_content = (EditText) findViewById(R.id.edit_content);
        text_comment = (TextView) findViewById(R.id.text_comment);
        text_comment_count = (TextView)headerView. findViewById(R.id.text_comment_count);
        img_take_pic = (ImageView) findViewById(R.id.img_take_pic);
        ll_comment = (LinearLayout) findViewById(R.id.ll_comment);
        ll_imgs = (LinearLayout) findViewById(R.id.ll_imgs);
        img1 = (ImageView) findViewById(R.id.img1);
        img_close = (ImageView) findViewById(R.id.img_close);
        loadData();

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultUri = null;
                ll_imgs.setVisibility(View.GONE);
            }
        });
        img_take_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popSelectPicture.showAtLocation(ll_comment, Gravity.BOTTOM, 0, 0);
            }
        });
        text_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String commentContent = String.valueOf(edit_content.getText());
                if (TextUtils.isEmpty(commentContent)) {
                    Toast.makeText(DetailActivity.this, "评论内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (resultUri != null) {
                    //上传压缩后的图片
                    //Bitmap bitmap = CameraUtil.getBitmapFromUri(DetailActivity.this, resultUri);
                    //File file = CameraUtil.saveBitmapFile(bitmap);
                    //上传原图(超过500kb 无法上传)
                    //File file = CameraUtil.getFileByUri(DetailActivity.this,resultUri);
                   //上传指定大小的图片
                    Bitmap bitmap = CameraUtil.getBitmapFromUri(DetailActivity.this, resultUri,DEFAULT_IMG_WH,DEFAULT_IMG_WH);
                    File file = CameraUtil.saveBitmapFile(bitmap);
                    LoadingDialog.getInstance(DetailActivity.this).setTitle("发送中...").show();
                    userService.uploadUserHeadImg(uid, file, new DefaultResponseListener<String>() {
                        @Override
                        public void onSuccess(String response) {
                            if (!TextUtils.isEmpty(response)) {
                                BaseResponse baseResponse = GsonUtil.getJsonObject(response, BaseResponse.class);
                                String imgUrl = baseResponse.getMessage();
                                commitComment(commentContent, imgUrl);
                            }
                        }

                        @Override
                        public void onError(VolleyError error) {
                            LoadingDialog.getInstance(DetailActivity.this).dismiss();
                        }
                    });
                } else {
                    commitComment(commentContent, null);
                }

            }
        });

        text_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, JoinMembersActivity.class);
                intent.putExtra("id", activityId + "");
                startActivity(intent);
            }
        });
        text_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isjoin == 2) {
                    activityService.unjoinActivity(uid, activityId + "", new DefaultResponseListener<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse response) {
                            if (response != null) {
                                if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                                    loadData();
                                }
                                ToastUtil.show(DetailActivity.this, response.getMessage());
                            }
                        }

                        @Override
                        public void onError(VolleyError error) {

                        }
                    });
                } else if (isjoin == 3) {
                    activityService.joinActivity(uid, activityId + "", new DefaultResponseListener<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse response) {
                            if (response != null) {
                                if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                                    loadData();
                                }
                                ToastUtil.show(DetailActivity.this, response.getMessage());
                            }
                        }

                        @Override
                        public void onError(VolleyError error) {

                        }
                    });
                }

            }
        });
    }

    @Override
    protected void onBtnRight1Click(){
        Intent intent = new Intent(DetailActivity.this, JoinMembersActivity.class);
        intent.putExtra("id", activityId + "");
        startActivity(intent);
    }

    public void commitComment(String content, String img) {
        activityService.comment(uid, activityId + "", activity.getUserId() + "", content, img, new DefaultResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                LoadingDialog.getInstance(DetailActivity.this).dismiss();
                if (response != null) {
                    if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                        edit_content.setText("");
                        loadData();
                        ll_imgs.setVisibility(View.GONE);
                        resultUri = null;
                    }
                    ToastUtil.show(DetailActivity.this, response.getMessage());
                }
            }

            @Override
            public void onError(VolleyError error) {
                LoadingDialog.getInstance(DetailActivity.this).dismiss();
            }
        });
    }

    public void loadComments() {
        activityService.getActivityComments(uid, activityId + "", new DefaultResponseListener<BaseResponse<List<YyMobileActivityComment>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileActivityComment>> response) {
                if (response != null && response.getResult() != null) {
                    data.clear();
                    data.addAll(response.getResult());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }


    public void loadData() {
        LoadingDialog.getInstance(this).show();
        activityService.getHdDetail(uid, String.valueOf(activityId), new DefaultResponseListener<BaseResponse<YyMobileActivity>>() {
            @Override
            public void onSuccess(BaseResponse<YyMobileActivity> response) {
                LoadingDialog.getInstance(DetailActivity.this).dismiss();
                if (response != null && response.getResult() != null) {
                    loadComments();
                    activity = response.getResult();
                    isjoin = activity.getIsjoin();
                    if (isjoin == 2) {
                        text_join.setText("取消报名");
                    } else if (isjoin == 3) {
                        text_join.setText("报名参加");
                    }
                    if (activity.getUserId() == AppConfig.getUserId(AppManager.getPreferences())) {
                        text_join.setVisibility(View.GONE);
                    }
                    simpleDraweeView.setImageURI(Uri.parse(activity.getPicPath()));
                    text_name.setText("发起人:" + activity.getUsername());
                    text_content.setText(activity.getHeadline());
                    text_location.setText(activity.getAddress());
                    text_time.setText(activity.getHdDate());
                    text_count.setText(activity.getYbrs() + "");
                    text_comment_count.setText("(共" + activity.getPlno() + "条)");
                }
            }

            @Override
            public void onError(VolleyError error) {
                LoadingDialog.getInstance(DetailActivity.this).dismiss();
            }
        });
    }


    class MyAdapter extends BaseAdapter {

        List<YyMobileActivityComment> data;

        public MyAdapter(List<YyMobileActivityComment> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(DetailActivity.this).inflate(R.layout.item_activity_comment, null);
                holder = new ViewHolder();
                holder.simpleDraweeView = (SimpleDraweeView) convertView.findViewById(R.id.simpleDraweeView);
                holder.text_name = (TextView) convertView.findViewById(R.id.text_name);
                holder.text_time = (TextView) convertView.findViewById(R.id.text_time);
                holder.text_content = (TextView) convertView.findViewById(R.id.text_content);
                holder.img_comment = (SimpleDraweeView) convertView.findViewById(R.id.img_comment);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            YyMobileActivityComment comment = data.get(position);
            if (comment != null) {
                Uri uri = Uri.parse(comment.getUserPic() == null ? "" : comment.getUserPic());
                holder.simpleDraweeView.setImageURI(uri);
                holder.text_name.setText(comment.getUsername());
                holder.text_content.setText(comment.getContent());
                holder.text_time.setText(comment.getCreateDate());
                if (!TextUtils.isEmpty(comment.getPicPath())) {
                    holder.img_comment.setVisibility(View.VISIBLE);
                    holder.img_comment.setImageURI(Uri.parse(comment.getPicPath()));
                } else {
                    holder.img_comment.setVisibility(View.GONE);
                }
            }

            return convertView;
        }

    }

    class ViewHolder {
        SimpleDraweeView simpleDraweeView;
        TextView text_name;
        TextView text_time;
        TextView text_content;
        SimpleDraweeView img_comment;
    }


    String imgNameByCamera;

    @Override
    public void onPicturePopClick(int status) {
        switch (status) {
            case PopSelectPicture.OnPicturePopClickListener.TakeCamera:
                imgNameByCamera = CameraUtil.getImageName();
                CameraUtil.getImageFromCamera(DetailActivity.this, imgNameByCamera);
                popSelectPicture.dismiss();
                break;
            case PopSelectPicture.OnPicturePopClickListener.PickFromPics:
                CameraUtil.getImageFromAlbum(DetailActivity.this);
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
            img1.setImageBitmap(CameraUtil.getBitmapFromUri(DetailActivity.this, uri));
            resultUri = uri;
        }
        if (uri != null) {
            Log.e("uri", uri.toString());
            ll_imgs.setVisibility(View.VISIBLE);
            img1.setImageBitmap(CameraUtil.getBitmapFromUri(DetailActivity.this, uri));
            resultUri = uri;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
