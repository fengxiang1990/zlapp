package com.zl.app.activity.course;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.fragment.course.CourseStatus;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fxa on 2016/6/6.
 */

public class StatusChoiceActivity extends BaseActivityWithToolBar {

    public static String CHOICE_STATUS_ACTION = "com.zl.app.choicestatus";
    ListView listView;
    int role = 3;
    List<CourseStatus> data;
    ArrayAdapter<CourseStatus> adapter;
    List<CourseStatus> statusesP, statusesT;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_list);
        role = AppConfig.getLoginType(AppManager.getPreferences());
        listView = (ListView) findViewById(R.id.listView);
        setTitle("课程状态选择");
        setBtnLeft1Enable(true);
        data = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, data);
        listView.setAdapter(adapter);
        statusesP = new ArrayList<>();
        statusesT = new ArrayList<>();
        statusesP.add(new CourseStatus(1, "请假"));
        statusesP.add(new CourseStatus(2, "待出席"));
        statusesP.add(new CourseStatus(3, "补假"));
        statusesP.add(new CourseStatus(4, "已上课"));

        statusesT.add(new CourseStatus(2, "正常"));
        statusesT.add(new CourseStatus(3, "取消"));
        statusesT.add(new CourseStatus(4, "已上课"));
        if (role == 3) {
            data.addAll(statusesP);
        } else if (role == 5) {
            data.addAll(statusesT);
        }
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CourseStatus status = data.get(position);
                Intent intent = new Intent(CHOICE_STATUS_ACTION);
                intent.putExtra("type",role);
                intent.putExtra("status", status);
                sendBroadcast(intent);
                finish();
            }
        });
    }

}
