package com.zl.app.activity.course;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.CourseService;
import com.zl.app.data.model.customer.YyMobileCompany;
import com.zl.app.data.model.user.YyMobileStudent;
import com.zl.app.util.AppConfig;
import com.zl.app.util.GsonUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 精确搜索 老师（家长）的机构列表接口
 * Created by fxa on 2016/6/5.
 */
public class SearchCourceOrgActivity extends BaseActivityWithToolBar {

    public static String ACTION = "com.zl.app.coursesearch";
    MyAdapter adapter;
    List<Object> data;
    ListView listView;

    //1:老师机构列表
    //2:家长的机构列表
    //3:家长的孩子列表
    int op = 1;
    String uid;
    CourseService courseService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_list);
        data = new ArrayList<>();
        courseService = new CourseService();
        adapter = new MyAdapter(data);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        op = getIntent().getIntExtra("op", 1);
        uid = AppConfig.getUid(preference);
        setBtnLeft1Enable(true);
        if (op == 1 || op == 2) {
            setTitle("机构列表");
        }
        if (op == 3) {
            setTitle("孩子列表");
        }
        loadData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ACTION);
                Object object = data.get(position);
                intent.putExtra("op",op);
                if(object instanceof YyMobileCompany){
                    YyMobileCompany yyMobileCompany = (YyMobileCompany) object;
                    intent.putExtra("data", GsonUtil.gson.toJson(yyMobileCompany));
                }else if(object instanceof  YyMobileStudent){
                    YyMobileStudent yyMobileStudent = (YyMobileStudent) object;
                    intent.putExtra("data", GsonUtil.gson.toJson(yyMobileStudent));
                }
                sendBroadcast(intent);
                finish();
            }
        });
    }

    void loadData() {
        if (op == 1) {
            courseService.getCourseTeacherOrg(uid, new DefaultResponseListener<BaseResponse<List<YyMobileCompany>>>() {
                @Override
                public void onSuccess(BaseResponse<List<YyMobileCompany>> response) {
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
        } else if (op == 2) {
            courseService.getCourseParentOrg(uid, new DefaultResponseListener<BaseResponse<List<YyMobileCompany>>>() {
                @Override
                public void onSuccess(BaseResponse<List<YyMobileCompany>> response) {
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
        } else if (op == 3) {
            courseService.getParentChildren(uid, new DefaultResponseListener<BaseResponse<List<YyMobileStudent>>>() {
                @Override
                public void onSuccess(BaseResponse<List<YyMobileStudent>> response) {
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
    }

    class MyAdapter extends BaseAdapter {
        List<Object> data;

        public MyAdapter(List<Object> data) {
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
                holder = new ViewHolder();
                convertView = LayoutInflater.from(SearchCourceOrgActivity.this).inflate(R.layout.item_course_search, null);
                holder.text_content = (TextView) convertView.findViewById(R.id.text_content);
                holder.img_header = (SimpleDraweeView) convertView.findViewById(R.id.img_header);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Object object = data.get(position);
            if (object instanceof YyMobileCompany) {
                YyMobileCompany yyMobileCompany = (YyMobileCompany) object;
                holder.img_header.setImageURI(Uri.parse(yyMobileCompany.getPicPath()));
                holder.text_content.setText(yyMobileCompany.getCompanyname());
            } else if (object instanceof YyMobileStudent) {
                YyMobileStudent yyMobileStudent = (YyMobileStudent) object;
                holder.img_header.setImageURI(Uri.parse(yyMobileStudent.getPhoto()));
                holder.text_content.setText(yyMobileStudent.getName());
            }
            return convertView;
        }
    }

class ViewHolder {
    SimpleDraweeView img_header;
    TextView text_content;
}
}
