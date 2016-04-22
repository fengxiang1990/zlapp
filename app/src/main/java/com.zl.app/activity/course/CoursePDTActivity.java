package com.zl.app.activity.course;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.CourseService;
import com.zl.app.model.customer.YyMobilePeriod;
import com.zl.app.model.customer.YyMobilePeriodStudent;
import com.zl.app.popwindow.PopStudentStatus;
import com.zl.app.util.AppConfig;
import com.zl.app.util.GsonUtil;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 班级学生动态 家长
 * Created by fengxiang on 2016/4/21.
 */
public class CoursePDTActivity extends BaseActivityWithToolBar implements View.OnClickListener {

    TextView text_time;
    TextView text_teacher;
    YyMobilePeriod yyMobilePeriod;
    ListView listView;
    TextView text_student_status;
    TextView text_student;
    TextView text_send;
    EditText edit_content;
    List<YyMobilePeriodStudent> data;
    MyAdapter adapter;
    String uid;
    Integer periodId;
    PopStudentStatus popStudentStatus;
    int selectedStatus = 0;
    int relationId = 0;
    View lastView = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_p);
        setBtnLeft1Enable(true);
        popStudentStatus = new PopStudentStatus(this);
        popStudentStatus.setListener(new PopStudentStatus.OnSelectedListener() {
            @Override
            public void onSelected(int status) {
                selectedStatus = status;
                String statusStr = "";
                switch (status) {
                    case CourseService.CourseStatusP.QINGJIA:
                        statusStr = "请假";
                        text_student_status.setTextColor(getResources().getColor(R.color.red));
                        break;
                    case CourseService.CourseStatusP.ZHENGCHANG:
                        statusStr = "出席";
                        text_student_status.setTextColor(getResources().getColor(R.color.green));
                        break;
                    case CourseService.CourseStatusP.BUJIA:
                        statusStr = "补假";
                        text_student_status.setTextColor(getResources().getColor(R.color.red));
                        break;
                    case CourseService.CourseStatusP.YISHANGKE:
                        statusStr = "已上课";
                        break;
                }
                text_student_status.setText(statusStr);
            }
        });
        uid = AppConfig.getUid(preference);
        data = new ArrayList<YyMobilePeriodStudent>();
        adapter = new MyAdapter(data);
        text_time = (TextView) findViewById(R.id.text_time);
        text_teacher = (TextView) findViewById(R.id.text_teacher);
        listView = (ListView) findViewById(R.id.listView);
        text_student_status = (TextView) findViewById(R.id.text_student_status);
        text_student = (TextView) findViewById(R.id.text_student);
        text_send = (TextView) findViewById(R.id.text_send);
        edit_content = (EditText) findViewById(R.id.edit_content);
        text_student_status.setOnClickListener(this);
        text_student.setOnClickListener(this);
        text_send.setOnClickListener(this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (lastView != null) {
                    lastView.setBackgroundColor(Color.WHITE);
                }
                view.setBackgroundColor(Color.LTGRAY);
                lastView = view;

                YyMobilePeriodStudent student = data.get(position);
                relationId = student == null ? 0 : student.getRelationId();
                text_student.setText(student == null ? "" : student.getStudentName());
            }
        });
        yyMobilePeriod = GsonUtil.getJsonObject(getIntent().getStringExtra("course"), YyMobilePeriod.class);
        if (yyMobilePeriod != null) {
            periodId = yyMobilePeriod.getPeriodId();
            setTitle(yyMobilePeriod.getClassname());
            loadData();
        }
    }

    public void loadData() {
        new CourseService().getCourseStudentsDT(uid, String.valueOf(periodId), new DefaultResponseListener<BaseResponse<List<YyMobilePeriodStudent>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobilePeriodStudent>> response) {
                if (response != null) {
                    List<YyMobilePeriodStudent> list = response.getResult();
                    if (list != null && list.size() > 0) {
                        YyMobilePeriodStudent student = list.get(0);
                        text_time.setText(student.getClasstime());
                        text_teacher.setText(student.getTeacherName());
                        data.clear();
                        data.addAll(list);
                        adapter.notifyDataSetChanged();
                    }
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
            case R.id.text_student_status:
                popStudentStatus.showAtLocation(text_student_status, Gravity.LEFT | Gravity.BOTTOM, 0, 100);
                break;
            case R.id.text_send:
                String content = String.valueOf(edit_content.getText());
                if (relationId == 0) {
                    ToastUtil.show(getApplicationContext(), "请选择一个学生");
                    return;
                }
                if (selectedStatus == 0) {
                    ToastUtil.show(getApplicationContext(), "请设置学生状态");
                    return;
                }
                new CourseService().submitCourseStudentsDT(uid, String.valueOf(relationId), String.valueOf(selectedStatus),
                        content, new DefaultResponseListener<BaseResponse>() {
                            @Override
                            public void onSuccess(BaseResponse response) {
                                if (response != null) {
                                    ToastUtil.show(getApplicationContext(), response.getMessage());
                                    edit_content.setText("");
                                    edit_content.setText("");
                                    loadData();
                                }
                            }

                            @Override
                            public void onError(VolleyError error) {

                            }
                        });
                break;
        }
    }

    class MyAdapter extends BaseAdapter {

        List<YyMobilePeriodStudent> data;

        public MyAdapter(List<YyMobilePeriodStudent> data) {
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
                convertView = LayoutInflater.from(CoursePDTActivity.this).inflate(R.layout.item_course_dt_p, null);
                holder = new ViewHolder();
                holder.simpleDraweeView = (SimpleDraweeView) convertView.findViewById(R.id.simpleDraweeView);
                holder.text_student_name = (TextView) convertView.findViewById(R.id.text_student_name);
                holder.text_content = (TextView) convertView.findViewById(R.id.text_content);
                holder.text_status = (TextView) convertView.findViewById(R.id.text_status);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final YyMobilePeriodStudent period = data.get(position);
            if (period != null) {
                Uri uri = Uri.parse(period.getPicPath() == null ? "" : period.getPicPath());
                holder.simpleDraweeView.setImageURI(uri);
                holder.text_student_name.setText(period.getStudentName());
                holder.text_content.setText(period.getContent());
                String statusStr = "";
                switch (period.getType()) {
                    case CourseService.CourseStatusP.QINGJIA:
                        statusStr = "请假";
                        holder.text_status.setTextColor(getResources().getColor(R.color.red));
                        break;
                    case CourseService.CourseStatusP.ZHENGCHANG:
                        statusStr = "正常";
                        holder.text_status.setTextColor(getResources().getColor(R.color.green));
                        break;
                    case CourseService.CourseStatusP.BUJIA:
                        statusStr = "补假";
                        holder.text_status.setTextColor(getResources().getColor(R.color.red));
                        break;
                    case CourseService.CourseStatusP.YISHANGKE:
                        statusStr = "已上课";
                        holder.text_status.setTextColor(getResources().getColor(R.color.green));
                        break;
                }
                holder.text_status.setText(statusStr);
            }
            return convertView;
        }

    }

    class ViewHolder {
        SimpleDraweeView simpleDraweeView;
        TextView text_student_name;
        TextView text_content;
        TextView text_status;
    }

}