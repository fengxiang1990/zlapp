package com.zl.app.activity.activities;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.zl.app.model.activity.YyMobileActivity;
import com.zl.app.model.activity.YyMobileActivityComment;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengxiang on 2016/4/28.
 */
public class DetailActivity extends BaseActivityWithToolBar {

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
    String uid;
    int activityId;
    ActivityService activityService;
    YyMobileActivity activity;
    String title;
    int isjoin;
    List<YyMobileActivityComment> data;
    MyAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_detail);
        uid = AppConfig.getUid(preference);
        data = new ArrayList<YyMobileActivityComment>();
        adapter = new MyAdapter(data);
        activityService = new ActivityService();
        activityId = getIntent().getIntExtra("activityId", 0);
        title = getIntent().getStringExtra("title");
        setTitle(title);
        setBtnLeft1Enable(true);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.simpleDraweeView);
        text_name = (TextView) findViewById(R.id.text_name);
        text_join = (TextView) findViewById(R.id.text_join);
        text_location = (TextView) findViewById(R.id.text_location);
        text_time = (TextView) findViewById(R.id.text_time);
        text_count = (TextView) findViewById(R.id.text_count);
        text_content = (TextView) findViewById(R.id.text_content);
        edit_content = (EditText) findViewById(R.id.edit_content);
        text_comment = (TextView) findViewById(R.id.text_comment);
        text_comment_count = (TextView) findViewById(R.id.text_comment_count);
        loadData();

        text_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentContent = String.valueOf(edit_content.getText());
                if (TextUtils.isEmpty(commentContent)) {
                    Toast.makeText(DetailActivity.this, "评论内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                activityService.comment(uid, activityId + "", activity.getUserId() + "", commentContent, "", new DefaultResponseListener<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        if (response != null) {
                            if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                                edit_content.setText("");
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
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            YyMobileActivityComment comment = data.get(position);
            if (comment != null) {
                Uri uri = Uri.parse(comment.getPicPath() == null ? "" : comment.getPicPath());
                holder.simpleDraweeView.setImageURI(uri);
                holder.text_name.setText(comment.getUsername());
                holder.text_content.setText(comment.getContent());
                holder.text_time.setText(comment.getCreateDate());
            }

            return convertView;
        }

    }

    class ViewHolder {
        SimpleDraweeView simpleDraweeView;
        TextView text_name;
        TextView text_time;
        TextView text_content;
    }

}
