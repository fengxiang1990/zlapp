package com.zl.app.activity.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.CameraActivity;
import com.zl.app.R;
import com.zl.app.activity.mine.babyupdate.BabyQRActivity;
import com.zl.app.activity.mine.babyupdate.ChildRalationShipChoiceActicity;
import com.zl.app.activity.mine.babyupdate.UpdateBabyBirthdateActivity;
import com.zl.app.activity.mine.babyupdate.UpdateBabyNameOrIdActivity;
import com.zl.app.data.mine.MineServiceImpl;
import com.zl.app.data.model.user.YyMobileStudent;
import com.zl.app.data.user.UserServiceImpl;
import com.zl.app.util.AppConfig;
import com.zl.app.util.GsonUtil;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.view.LoadingDialog;

import java.io.File;

/**
 * Created by fengxiang on 2016/4/19.
 */
public class EditChildActivity extends CameraActivity implements View.OnClickListener {

    String tag = "EditChildActivity";
    View root;
    TextView text_name;
    TextView text_id_number;
    TextView text_birthday;
    TextView text_ralationship;
    SimpleDraweeView img_header;
    String childStr;
    String uid;
    YyMobileStudent student;
    LinearLayout ll_name_update,ll_qr, ll_id_update, ll_birthday_update, ll_ralationship_edit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_child);
        setBtnLeft1Enable(true);
        setTitle("我的宝贝");
        uid = AppConfig.getUid(preference);
        root = findViewById(R.id.root);
        img_header = (SimpleDraweeView) findViewById(R.id.img_header);
        text_name = (TextView) findViewById(R.id.text_name);
        text_id_number = (TextView) findViewById(R.id.text_id_number);
        text_birthday = (TextView) findViewById(R.id.text_birthday);
        text_ralationship = (TextView) findViewById(R.id.text_ralationship);
        ll_name_update = (LinearLayout) findViewById(R.id.ll_name_edit);
        ll_id_update = (LinearLayout) findViewById(R.id.ll_id_edit);
        ll_birthday_update = (LinearLayout) findViewById(R.id.ll_birthday_edit);
        ll_ralationship_edit = (LinearLayout) findViewById(R.id.ll_ralationship_edit);
        ll_qr = (LinearLayout) findViewById(R.id.ll_qr);
        ll_qr.setOnClickListener(this);
        ll_name_update.setOnClickListener(this);
        ll_id_update.setOnClickListener(this);
        ll_birthday_update.setOnClickListener(this);
        ll_ralationship_edit.setOnClickListener(this);
        childStr = getIntent().getStringExtra("child");
        if (!TextUtils.isEmpty(childStr)) {
            student = GsonUtil.getJsonObject(childStr, YyMobileStudent.class);
        }
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new MineServiceImpl().getChildDetail(uid, student.getStudentId(), new DefaultResponseListener<BaseResponse<YyMobileStudent>>() {
            @Override
            public void onSuccess(BaseResponse<YyMobileStudent> response) {
                student = response.getResult();
                if (student != null) {
                    text_name.setText(student.getName());
                    text_id_number.setText(student.getIdCard());
                    text_birthday.setText(student.getBirthday());
                    String relationStr = "";
                    switch (student.getRelation()) {
                        case 1:
                            relationStr = "爸爸";
                            break;
                        case 2:
                            relationStr = "妈妈";
                            break;
                        case 3:
                            relationStr = "爷爷";
                            break;
                        case 4:
                            relationStr = "奶奶";
                            break;
                        case 5:
                            relationStr = "外公";
                            break;
                        case 6:
                            relationStr = "外婆";
                            break;
                    }
                    text_ralationship.setText(relationStr);
                    img_header.setImageURI(Uri.parse(student.getPhoto()));
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }


    /**
     * 修改信息状态
     */
    public interface UpdateStatus {
        int TYPE_UP_NANME = 1;//修改姓名
        int TYPE_UP_IDCARD = 2;//修改身份证
        int TYPE_UP_BIRTHDAY = 3;//修改生日
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent;
        switch (v.getId()) {
            case R.id.ll_name_edit:
                intent = new Intent(EditChildActivity.this, UpdateBabyNameOrIdActivity.class);
                intent.putExtra("text", student.getName());
                intent.putExtra("type", UpdateStatus.TYPE_UP_NANME);
                intent.putExtra("id", student.getStudentId());
                startActivity(intent);
                break;
            case R.id.ll_id_edit:
                intent = new Intent(EditChildActivity.this, UpdateBabyNameOrIdActivity.class);
                intent.putExtra("text", student.getIdCard());
                intent.putExtra("type", UpdateStatus.TYPE_UP_IDCARD);
                intent.putExtra("id", student.getStudentId());
                startActivity(intent);
                break;
            case R.id.ll_birthday_edit:
                intent = new Intent(EditChildActivity.this, UpdateBabyBirthdateActivity.class);
                intent.putExtra("text", student.getBirthday());
                intent.putExtra("id", student.getStudentId());
                startActivity(intent);
                break;
            case R.id.ll_ralationship_edit:
                intent = new Intent(EditChildActivity.this, ChildRalationShipChoiceActicity.class);
                intent.putExtra("id", student.getStudentId());
                intent.putExtra("type", student.getRelation());
                startActivity(intent);
                break;
            case R.id.ll_qr:
                intent = new Intent(EditChildActivity.this, BabyQRActivity.class);
                intent.putExtra("id", student.getStudentId());
                intent.putExtra("title", student.getName()+"的名片");
                startActivity(intent);
                break;

        }
    }

    @Override
    protected void onGetImageSuccess(File file) {
        Log.e(tag, file == null ? "file is null" : file.getAbsolutePath());
        LoadingDialog.getInstance(EditChildActivity.this).setTitle("正在上传头像...").show();
        new UserServiceImpl().uploadUserHeadImg(AppConfig.getUid(preference), file, new DefaultResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                LoadingDialog.getInstance(EditChildActivity.this).dismiss();
                if (!TextUtils.isEmpty(response)) {
                    BaseResponse baseResponse = GsonUtil.getJsonObject(response, BaseResponse.class);
                    final String imgUrl = baseResponse.getMessage();
                    Log.e("EditChildActivity", "imgUrl--->" + imgUrl);
                    new MineServiceImpl().updateStudent(uid, student.getStudentId(), imgUrl, null, null, null, 0, new DefaultResponseListener<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse response) {
                            if (response != null && response.getStatus().equals(AppConfig.HTTP_OK)) {
                                img_header.setImageURI(Uri.parse(imgUrl));
                            }
                            ToastUtil.show(EditChildActivity.this, response.getMessage());
                        }

                        @Override
                        public void onError(VolleyError error) {
                        }
                    });
                }
            }

            @Override
            public void onError(VolleyError error) {
                ToastUtil.show(EditChildActivity.this, error.getMessage());
                LoadingDialog.getInstance(EditChildActivity.this).dismiss();
            }
        });
    }


    void initEvent() {
        img_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popSelectPicture.showAtLocation(root, Gravity.BOTTOM, 0, 0);
            }
        });
    }
}
