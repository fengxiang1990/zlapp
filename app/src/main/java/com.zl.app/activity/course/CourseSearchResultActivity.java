package com.zl.app.activity.course;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.CourseService;
import com.zl.app.data.model.customer.YyMobilePeriod;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.GsonUtil;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.XListView;

/**
 * Created by fxa on 2016/6/6.
 */
@EActivity(R.layout.activity_course)
public class CourseSearchResultActivity extends BaseActivityWithToolBar {

    String tag = "FragmentCourse";
    @ViewById(R.id.list_course)
    XListView listView;

    Context context;

    List<YyMobilePeriod> data;
    MyAdapter adapter;

    CourseService courseService;

    String uid;
    Integer studentId;
    Integer companyId;
    Integer courseStatusP;
    Integer courseStatusT;
    String startDate;
    String endDate;
    int role = 3;
    TextView textView;
    boolean isLoadMore = false;
    int page = 1;
    int pageSize = 20;

    @AfterViews
    void afterViews() {
        role = AppConfig.getLoginType(AppManager.getPreferences());
        if (role == 3) {
            setTitle("搜索结果(家长)");
        }
        if (role == 5) {
            setTitle("搜索结果(老师)");
        }
        setBtnLeft1Enable(true);
        studentId = getIntent().getIntExtra("studentId", 0);
        companyId = getIntent().getIntExtra("companyId", 0);
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        courseStatusP = getIntent().getIntExtra("statusP", 0);
        courseStatusT = getIntent().getIntExtra("statusT", 0);
        if (studentId == 0) {
            studentId = null;
        }
        if (companyId == 0) {
            companyId = null;
        }
        if (courseStatusP == 0) {
            courseStatusP = null;
        }
        if (courseStatusT == 0) {
            courseStatusT = null;
        }
        context = CourseSearchResultActivity.this;
        courseService = new CourseService();
        uid = AppConfig.getUid(AppManager.getPreferences());
        data = new ArrayList<YyMobilePeriod>();
        adapter = new MyAdapter(data);
        listView.setAdapter(adapter);
        textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(0, 10, 0, 10);
        listView.addHeaderView(textView);
        listView.setPullLoadEnable(true);
        loadCourse();

        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                isLoadMore = false;
                page = 1;
                loadCourse();
            }

            @Override
            public void onLoadMore() {
                isLoadMore = true;
                page += 1;
                loadCourse();
            }
        });
    }

    void onLoad() {
        listView.stopLoadMore();
        listView.stopRefresh();
    }

    public void loadCourse() {
        //如果是家长登录
        if (role == 3) {
            Log.e(tag, "load parent data");
            courseService.getCoursePList(page + "", pageSize + "", uid, startDate, endDate, courseStatusP, studentId, companyId, new DefaultResponseListener<BaseResponse<List<YyMobilePeriod>>>() {
                @Override
                public void onSuccess(BaseResponse<List<YyMobilePeriod>> response) {
                    Log.e("response", response.toString());
                    onLoad();
                    if (response != null) {
                        if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                            if (!isLoadMore) {
                                data.clear();
                            }
                            data.addAll(response.getResult() == null ? new ArrayList<YyMobilePeriod>() : response.getResult());
                            adapter.notifyDataSetChanged();
                            textView.setText("一共找到" + response.getMessage() + "条数据");
                        } else {
                            ToastUtil.show(context, response.getMessage());
                        }
                    }

                }

                @Override
                public void onError(VolleyError error) {
                    onLoad();
                }
            });

        }
        //机构用户登录（教师）
        else if (role == 5) {
            Log.e(tag, "load teacher data");
            courseService.getCourseTList(page + "", pageSize + "",uid, startDate, endDate, courseStatusT, companyId, new DefaultResponseListener<BaseResponse<List<YyMobilePeriod>>>() {
                @Override
                public void onSuccess(BaseResponse<List<YyMobilePeriod>> response) {
                    onLoad();
                    if (response != null) {
                        if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                            if (!isLoadMore) {
                                data.clear();
                            }
                            data.addAll(response.getResult() == null ? new ArrayList<YyMobilePeriod>() : response.getResult());
                            adapter.notifyDataSetChanged();
                            textView.setText("一共找到" + response.getMessage() + "条数据");
                        } else {
                            ToastUtil.show(context, response.getMessage());
                        }
                    }

                }

                @Override
                public void onError(VolleyError error) {
                    onLoad();
                }
            });

        }
    }


    class MyAdapter extends BaseAdapter {

        List<YyMobilePeriod> data;

        public MyAdapter(List<YyMobilePeriod> data) {
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
                convertView = LayoutInflater.from(context).inflate(R.layout.item_course, null);
                holder = new ViewHolder();
                holder.img_course = (SimpleDraweeView) convertView.findViewById(R.id.img_course);
                holder.text_course_name = (TextView) convertView.findViewById(R.id.text_course_name);
                holder.text_course_time = (TextView) convertView.findViewById(R.id.text_course_time);
                holder.btn_dt = (ImageView) convertView.findViewById(R.id.btn_dt);
                holder.btn_inner = (ImageView) convertView.findViewById(R.id.btn_inner_teacher);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final YyMobilePeriod period = data.get(position);
            if (period != null) {
                Uri uri = Uri.parse(period.getPicPath() == null ? "" : period.getPicPath());
                holder.img_course.setImageURI(uri);
                holder.text_course_name.setText(period.getPeriodname());
                holder.text_course_time.setText(period.getClasstime());
                holder.btn_inner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, CourseMessageActivity.class);
                        intent.putExtra("course", GsonUtil.gson.toJson(period));
                        startActivity(intent);
                    }
                });
                holder.btn_dt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, CoursePDTActivity.class);
                        intent.putExtra("course", GsonUtil.gson.toJson(period));
                        startActivity(intent);
                    }
                });
            }
            return convertView;
        }

    }

    class ViewHolder {
        SimpleDraweeView img_course;
        TextView text_course_name;
        TextView text_course_time;
        ImageView btn_dt;
        ImageView btn_inner;
    }

}
