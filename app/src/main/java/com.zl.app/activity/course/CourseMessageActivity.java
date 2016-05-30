package com.zl.app.activity.course;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.CameraActivity;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.CourseService;
import com.zl.app.data.mine.MineServiceImpl;
import com.zl.app.data.model.customer.YyMobilePeriod;
import com.zl.app.data.model.customer.YyMobilePeriodBbs;
import com.zl.app.data.user.UserServiceImpl;
import com.zl.app.popwindow.PopSelectPicture;
import com.zl.app.util.AppConfig;
import com.zl.app.util.GsonUtil;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.view.LoadingDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengxiang on 2016/5/6.
 */
public class CourseMessageActivity extends CameraActivity implements View.OnClickListener {

    ListView listView;
    TextView textTime;
    TextView textTeacher;
    View  root;
    TextView textSend;
    EditText editMsg;
    LinearLayout ll2;
    ImageView img_take_pic;
    CourseService courseService;
    YyMobilePeriod yyMobilePeriod;
    int periodId;
    String uid;
    PopSelectPicture popSelectPicture;
    MyAdapter adapter;
    List<YyMobilePeriodBbs> data;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_inner_msg);
        initView();
        initEvent();
        initData();
        loadData();
    }

    @Override
    protected void onGetImageSuccess(File file) {
        popSelectPicture.dismiss();
        LoadingDialog.getInstance(CourseMessageActivity.this).setTitle("上传图片中...").show();
        new UserServiceImpl().uploadUserHeadImg(AppConfig.getUid(preference), file, new DefaultResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                LoadingDialog.getInstance(CourseMessageActivity.this).dismiss();
                if (!TextUtils.isEmpty(response)) {
                    BaseResponse baseResponse = GsonUtil.getJsonObject(response, BaseResponse.class);
                    final String imgUrl = baseResponse.getMessage();
                    Log.e("EditChildActivity", "imgUrl--->" + imgUrl);
                    image1 = imgUrl;
                    courseService.sendCourseMessage(uid, periodId, null, yyuserId, image1, image2, image3, image4, new DefaultResponseListener<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse response) {
                            if (response != null) {
                                if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                                    editMsg.setText("");
                                    loadData();
                                }
                                ToastUtil.show(CourseMessageActivity.this, response.getMessage());
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
                ToastUtil.show(CourseMessageActivity.this, error.getMessage());
                LoadingDialog.getInstance(CourseMessageActivity.this).dismiss();
            }
        });
    }

    public void initData() {
        data = new ArrayList<YyMobilePeriodBbs>();
        adapter = new MyAdapter(data);
        listView.setAdapter(adapter);
        setBtnLeft1Enable(true);
        uid = AppConfig.getUid(preference);
        courseService = new CourseService();
        yyMobilePeriod = GsonUtil.getJsonObject(getIntent().getStringExtra("course"), YyMobilePeriod.class);
        if (yyMobilePeriod != null) {
            periodId = yyMobilePeriod.getPeriodId();
            setTitle(yyMobilePeriod.getClassname());
            textTime.setText(yyMobilePeriod.getClasstime());
            textTeacher.setText(yyMobilePeriod.getTeachername());
        }
    }

    public void initView() {
        root = findViewById(R.id.root);
        popSelectPicture = new PopSelectPicture(this);
        popSelectPicture.setOnPicturePopClickListener(this);
        listView = (ListView) findViewById(R.id.listView5);
        textTime = (TextView) findViewById(R.id.text_time);
        textTeacher = (TextView) findViewById(R.id.text_teacher);
        textSend = (TextView) findViewById(R.id.text_send);
        editMsg = (EditText) findViewById(R.id.edit_msg);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        img_take_pic = (ImageView) findViewById(R.id.img_take_pic);
    }

    public void initEvent() {
        img_take_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popSelectPicture.showAtLocation(root, Gravity.BOTTOM,0,0);
            }
        });
        textSend.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    public void loadData() {
         courseService.commentList(uid, periodId, new DefaultResponseListener<BaseResponse<List<YyMobilePeriodBbs>>>() {
             @Override
             public void onSuccess(BaseResponse<List<YyMobilePeriodBbs>> response) {
                 if(response != null && response.getResult()!=null){
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


    int yyuserId = 0;
    String image1, image2, image3, image4;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.text_send:
                String content = String.valueOf(editMsg.getText());
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.show(CourseMessageActivity.this, "内容不能为空");
                    return;
                }
                Log.e("uid", uid);
                Log.e("periodId", periodId + "");
                courseService.sendCourseMessage(uid, periodId, content, yyuserId, image1, image2, image3, image4, new DefaultResponseListener<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        if (response != null) {
                            if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                                editMsg.setText("");
                                loadData();
                            }
                            ToastUtil.show(CourseMessageActivity.this, response.getMessage());
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
                break;
        }
    }

    class MyAdapter extends BaseAdapter{

        List<YyMobilePeriodBbs> data;
        public MyAdapter( List<YyMobilePeriodBbs> data){
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
            ViewHolder viewHolder;
            if(convertView == null){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(CourseMessageActivity.this).inflate(R.layout.item_course_comment,null);
                viewHolder.img_course_comment = (SimpleDraweeView) convertView.findViewById(R.id.img_course_comment);
                viewHolder.text_user = (TextView) convertView.findViewById(R.id.text_user);
                viewHolder.text_time = (TextView) convertView.findViewById(R.id.text_time);
                viewHolder.text_content = (TextView) convertView.findViewById(R.id.text_content);
                viewHolder.image1 = (SimpleDraweeView) convertView.findViewById(R.id.img_comment1);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            YyMobilePeriodBbs yyMobilePeriodBbs = data.get(position);
            if(yyMobilePeriodBbs!=null){
                viewHolder.img_course_comment.setImageURI(Uri.parse(yyMobilePeriodBbs.getPicPath()));
                viewHolder.text_user.setText(yyMobilePeriodBbs.getUsername());
                viewHolder.text_time.setText(yyMobilePeriodBbs.getCreateDate());
                viewHolder.text_content.setText(yyMobilePeriodBbs.getContent());
                if(!TextUtils.isEmpty(yyMobilePeriodBbs.getImage1())){
                    viewHolder.image1.setVisibility(View.VISIBLE);
                    viewHolder.image1.setImageURI(Uri.parse(yyMobilePeriodBbs.getImage1()));
                }else{
                    viewHolder.image1.setVisibility(View.GONE);
                }
            }
            return convertView;
        }
    }

    class ViewHolder{
        SimpleDraweeView img_course_comment;
        SimpleDraweeView image1;
        TextView text_user;
        TextView text_time;
        TextView text_content;
    }

}
