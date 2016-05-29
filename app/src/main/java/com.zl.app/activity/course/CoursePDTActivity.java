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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.CourseService;
import com.zl.app.data.model.customer.YyMobilePeriod;
import com.zl.app.data.model.customer.YyMobilePeriodStudent;
import com.zl.app.popwindow.PopStudentStatus;
import com.zl.app.util.AppConfig;
import com.zl.app.util.GsonUtil;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.ViewUtil;
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
    TextView text_teacher_lable;
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
    LinearLayout ll2;
    int role = 3;
    CourseService courseService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_p);
        setBtnLeft1Enable(true);
        courseService = new CourseService();
        role = AppConfig.getLoginType(preference);
        //role = 5;
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
                        statusStr = "待出席";
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
        text_teacher_lable = (TextView) findViewById(R.id.text_teacher_lable);
        listView = (ListView) findViewById(R.id.listView);
        text_student_status = (TextView) findViewById(R.id.text_student_status);
        text_student = (TextView) findViewById(R.id.text_student);
        text_send = (TextView) findViewById(R.id.text_send);
        edit_content = (EditText) findViewById(R.id.edit_content);
        text_student_status.setOnClickListener(this);
        text_student.setOnClickListener(this);
        text_send.setOnClickListener(this);
        listView.setAdapter(adapter);
        ll2 = (LinearLayout) findViewById(R.id.ll2);

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
                if (student.getParentId() > 0 && student.getTotype() == 0) {
                    ll2.setVisibility(View.VISIBLE);
                } else {
                    ll2.setVisibility(View.GONE);
                }
            }
        });
        yyMobilePeriod = GsonUtil.getJsonObject(getIntent().getStringExtra("course"), YyMobilePeriod.class);
        if (yyMobilePeriod != null) {
            periodId = yyMobilePeriod.getPeriodId();
            setTitle(yyMobilePeriod.getPeriodname());
            if (role == 5) {
                ll2.setVisibility(View.GONE);
                text_teacher_lable.setVisibility(View.GONE);
                text_teacher.setVisibility(View.GONE);
                loadDataTeacher();
            } else if (role == 3) {
                loadData();
            }
        }
    }

    public void loadDataTeacher() {
        new CourseService().getCourseStudentsDT(uid, String.valueOf(periodId), new DefaultResponseListener<BaseResponse<List<YyMobilePeriodStudent>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobilePeriodStudent>> response) {
                if (response != null) {
                    List<YyMobilePeriodStudent> list = response.getResult();
                    if (list != null && list.size() > 0) {
                        YyMobilePeriodStudent student = list.get(0);
                        text_time.setText(yyMobilePeriod.getClasstime());
                        text_teacher.setText(yyMobilePeriod.getTeachername());
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

    public void loadData() {
        new CourseService().getCourseStudentsDT(uid, String.valueOf(periodId), new DefaultResponseListener<BaseResponse<List<YyMobilePeriodStudent>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobilePeriodStudent>> response) {
                if (response != null) {
                    List<YyMobilePeriodStudent> list = response.getResult();
                    if (list != null && list.size() > 0) {
                        YyMobilePeriodStudent student = list.get(0);
                        text_time.setText(yyMobilePeriod.getClasstime());
                        text_teacher.setText(yyMobilePeriod.getTeachername());
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
                holder.ll_teacher = (LinearLayout) convertView.findViewById(R.id.ll_teacher);
                holder.text_confirm = (TextView) convertView.findViewById(R.id.text_confirm);
                holder.text_shangke = (TextView) convertView.findViewById(R.id.text_shangke);
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
                if(role == 3){
                    // 家长在学生动态里判断totype 如果有值 不能操作 ，显示{状态}申请中。
                        switch (period.getTotype()) {
                            case CourseService.CourseStatusP.QINGJIA:
                                statusStr = "请假申请中";
                                holder.text_status.setTextColor(getResources().getColor(R.color.red));
                                ll2.setVisibility(View.GONE);
                                break;
                            case CourseService.CourseStatusP.ZHENGCHANG:
                                statusStr = "待出席申请中";
                                holder.text_status.setTextColor(getResources().getColor(R.color.red));
                                ll2.setVisibility(View.GONE);
                                break;
                            case CourseService.CourseStatusP.BUJIA:
                                statusStr = "补假申请中";
                                holder.text_status.setTextColor(getResources().getColor(R.color.red));
                                ll2.setVisibility(View.GONE);
                                break;
                            case CourseService.CourseStatusP.YISHANGKE:
                                statusStr = "已上课申请中";
                                holder.text_status.setTextColor(getResources().getColor(R.color.red));
                                ll2.setVisibility(View.GONE);
                                break;
                            default:
                                if(period.getType() == CourseService.CourseStatusP.QINGJIA){
                                    statusStr = "请假";
                                    holder.text_status.setTextColor(getResources().getColor(R.color.green));
                                } else if(period.getType() == CourseService.CourseStatusP.ZHENGCHANG) {
                                    statusStr = "待出席";
                                    holder.text_status.setTextColor(getResources().getColor(R.color.green));
                                } else if(period.getType() == CourseService.CourseStatusP.BUJIA) {
                                    statusStr = "补假";
                                    holder.text_status.setTextColor(getResources().getColor(R.color.green));
                                }else if(period.getType() == CourseService.CourseStatusP.YISHANGKE) {
                                    statusStr = "已上课";
                                    holder.text_status.setTextColor(getResources().getColor(R.color.green));
                                }
                                break;
                        }
                        holder.text_status.setText(statusStr);
                    }

                if (role == 5) {
                    switch (period.getTotype()) {
                        case CourseService.CourseStatusP.QINGJIA:
                            statusStr = "请假申请中";
                            holder.text_status.setTextColor(getResources().getColor(R.color.red));
                            break;
                        case CourseService.CourseStatusP.ZHENGCHANG:
                            statusStr = "待出席申请中";
                            holder.text_status.setTextColor(getResources().getColor(R.color.red));
                            break;
                        case CourseService.CourseStatusP.BUJIA:
                            statusStr = "补假申请中";
                            holder.text_status.setTextColor(getResources().getColor(R.color.red));
                            break;
                        case CourseService.CourseStatusP.YISHANGKE:
                            statusStr = "已上课申请中";
                            holder.text_status.setTextColor(getResources().getColor(R.color.red));
                            break;
                        default:
                            if(period.getType() == CourseService.CourseStatusP.QINGJIA){
                                statusStr = "请假";
                                holder.text_status.setTextColor(getResources().getColor(R.color.green));
                            } else if(period.getType() == CourseService.CourseStatusP.ZHENGCHANG) {
                                statusStr = "待出席";
                                holder.text_status.setTextColor(getResources().getColor(R.color.green));
                            } else if(period.getType() == CourseService.CourseStatusP.BUJIA) {
                                statusStr = "补假";
                                holder.text_status.setTextColor(getResources().getColor(R.color.green));
                            }else if(period.getType() == CourseService.CourseStatusP.YISHANGKE) {
                                statusStr = "已上课";
                                holder.text_status.setTextColor(getResources().getColor(R.color.green));
                            }
                            break;
                    }
                    holder.text_status.setText(statusStr);
                    //判断是否需要审核
                    if (period.getTotype() == 0) {
                        ViewUtil.hide(holder.ll_teacher);
                    } else {
                        ViewUtil.show(holder.ll_teacher);
                    }
                    holder.text_confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            courseService.teacherChecked(uid, period.getRelationId(), period.getTotype(), new DefaultResponseListener<BaseResponse>() {
                                @Override
                                public void onSuccess(BaseResponse response) {
                                    if (response != null) {
                                        loadDataTeacher();
                                        ToastUtil.show(CoursePDTActivity.this, response.getMessage());
                                    }
                                }

                                @Override
                                public void onError(VolleyError error) {

                                }
                            });
                        }
                    });
                    holder.text_shangke.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            courseService.teacherChecked(uid, period.getRelationId(), 2, new DefaultResponseListener<BaseResponse>() {
                                @Override
                                public void onSuccess(BaseResponse response) {
                                    if (response != null) {
                                        loadDataTeacher();
                                        ToastUtil.show(CoursePDTActivity.this, response.getMessage());
                                    }
                                }

                                @Override
                                public void onError(VolleyError error) {

                                }
                            });
                        }
                    });
                }
            }
            return convertView;
        }

    }

    class ViewHolder {
        SimpleDraweeView simpleDraweeView;
        TextView text_student_name;
        TextView text_content;
        TextView text_status;
        LinearLayout ll_teacher;
        TextView text_confirm;
        TextView text_shangke;
    }

}
