package com.zl.app.fragment.course;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.BaseFragment;
import com.zl.app.R;
import com.zl.app.activity.course.CoursePDTActivity;
import com.zl.app.activity.course.CourseTDTActivity;
import com.zl.app.data.CourseService;
import com.zl.app.fragment.FragmentClass;
import com.zl.app.fragment.FragmentFind;
import com.zl.app.model.customer.YyMobilePeriod;
import com.zl.app.model.user.YyMobileStudent;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.GsonUtil;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengxiang on 2016/4/20.
 */
@EFragment(R.layout.fragment_course)
public class FragmentCourse extends BaseFragment {

    @ViewById(R.id.list_course)
    ListView listView;

    List<YyMobilePeriod> data;
    MyAdapter adapter;

    CourseService courseService;

    public int type = -1;//上周 -1  0 本周  1 下周   2 其他

    String uid;
    Integer studentId;
    Integer companyId;
    Integer courseStatusP;
    Integer courseStatusT;

    @AfterViews
    void afterViews() {
        courseService = new CourseService();
        uid = AppConfig.getUid(AppManager.getPreferences());
        data = new ArrayList<YyMobilePeriod>();
        adapter = new MyAdapter(data);
        listView.setAdapter(adapter);
        loadCourse();
        /**
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (AppConfig.getLoginType(AppManager.getPreferences()) == 3) {
                    Intent intent = new Intent(getActivity(), CoursePDTActivity.class);
                    intent.putExtra("course", GsonUtil.gson.toJson(data.get(position)));
                    startActivity(intent);
                } else if (AppConfig.getLoginType(AppManager.getPreferences()) == 5) {
                    Intent intent = new Intent(getActivity(), CourseTDTActivity.class);
                    intent.putExtra("course", GsonUtil.gson.toJson(data.get(position)));
                    startActivity(intent);
                }
            }
        });
         **/
    }

    void loadCourse() {
        //如果是家长登录
        if (AppConfig.getLoginType(AppManager.getPreferences()) == 3) {
            String startDate = "";
            String endDate = "";
            if (type == -1) {
                startDate = FragmentClass.datemap.get("last_week_1");
                endDate = FragmentClass.datemap.get("last_week_7");
            } else if (type == 0) {
                startDate = FragmentClass.datemap.get("this_week_1");
                endDate = FragmentClass.datemap.get("this_week_7");
            } else if (type == 1) {
                startDate = FragmentClass.datemap.get("next_week_1");
                endDate = FragmentClass.datemap.get("next_week_7");
            } else if (type == 2) {

            }
            courseService.getCoursePList(uid, startDate, endDate, courseStatusP, studentId, companyId, new DefaultResponseListener<BaseResponse<List<YyMobilePeriod>>>() {
                @Override
                public void onSuccess(BaseResponse<List<YyMobilePeriod>> response) {
                    if (response != null) {
                        if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                            data.addAll(response.getResult() == null ? new ArrayList<YyMobilePeriod>() : response.getResult());
                            adapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.show(getActivity(), response.getMessage());
                        }
                    }

                }

                @Override
                public void onError(VolleyError error) {

                }
            });

        }
        //机构用户登录（教师）
        else if (AppConfig.getLoginType(AppManager.getPreferences()) == 5) {
            String startDate = "";
            String endDate = "";
            if (type == -1) {
                startDate = FragmentClass.datemap.get("last_week_1");
                endDate = FragmentClass.datemap.get("last_week_7");
            } else if (type == 0) {
                startDate = FragmentClass.datemap.get("this_week_1");
                endDate = FragmentClass.datemap.get("this_week_7");
            } else if (type == 1) {
                startDate = FragmentClass.datemap.get("next_week_1");
                endDate = FragmentClass.datemap.get("next_week_7");
            } else if (type == 2) {

            }
            courseService.getCourseTList(uid, startDate, endDate, courseStatusT, companyId, new DefaultResponseListener<BaseResponse<List<YyMobilePeriod>>>() {
                @Override
                public void onSuccess(BaseResponse<List<YyMobilePeriod>> response) {
                    if (response != null) {
                        if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                            data.addAll(response.getResult() == null ? new ArrayList<YyMobilePeriod>() : response.getResult());
                            adapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.show(getActivity(), response.getMessage());
                        }
                    }

                }

                @Override
                public void onError(VolleyError error) {

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
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_course, null);
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
                holder.text_course_name.setText(period.getClassname());
                holder.text_course_time.setText(period.getClasstime());
                holder.btn_dt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (AppConfig.getLoginType(AppManager.getPreferences()) == 3) {
                            Intent intent = new Intent(getActivity(), CoursePDTActivity.class);
                            intent.putExtra("course", GsonUtil.gson.toJson(period));
                            startActivity(intent);
                        } else if (AppConfig.getLoginType(AppManager.getPreferences()) == 5) {
                            Intent intent = new Intent(getActivity(), CourseTDTActivity.class);
                            intent.putExtra("course", GsonUtil.gson.toJson(period));
                            startActivity(intent);
                        }
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
