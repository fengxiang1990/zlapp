package com.zl.app.fragment.course;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.zl.app.BaseFragment;
import com.zl.app.R;
import com.zl.app.activity.course.CourseSearchResultActivity_;
import com.zl.app.activity.course.SearchCourceOrgActivity;
import com.zl.app.activity.course.StatusChoiceActivity;
import com.zl.app.data.model.customer.YyMobileCompany;
import com.zl.app.data.model.user.YyMobileStudent;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.GsonUtil;
import com.zl.app.util.ToastUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by fxa on 2016/5/23.
 */
@EFragment(R.layout.fragment_course_search)
public class FragmentCourseSearch extends BaseFragment {

    int role = 3;

    @ViewById
    TextView text_child;

    @ViewById
    TextView text_org;

    @ViewById
    TextView text_time_from;

    @ViewById
    TextView text_time_end;

    @ViewById
    TextView text_status;

    MyReceiver receiver;
    MyReceiver2 receiver2;

    @AfterViews
    void afterViews() {
        role = AppConfig.getLoginType(AppManager.getPreferences());
        receiver = new MyReceiver();
        receiver2 = new MyReceiver2();
        getActivity().registerReceiver(receiver, new IntentFilter(SearchCourceOrgActivity.ACTION));
        getActivity().registerReceiver(receiver2, new IntentFilter(StatusChoiceActivity.CHOICE_STATUS_ACTION));
    }

    @Click(R.id.btn_search)
    void btnSearchClick() {
        Intent intent = new Intent(getActivity(), CourseSearchResultActivity_.class);
        intent.putExtra("studentId", yyMobileStudent == null ? null : yyMobileStudent.getStudentId());
        intent.putExtra("companyId", yyMobileCompany == null ? null : yyMobileCompany.getCompanyId());
        intent.putExtra("startDate", String.valueOf(text_time_from.getText()));
        intent.putExtra("endDate", String.valueOf(text_time_end.getText()));
        intent.putExtra("endDate", String.valueOf(text_time_end.getText()));
        intent.putExtra("statusP",statusP==null?null:statusP.type);
        intent.putExtra("statusT",statusT==null?null:statusT.type);

        if (!TextUtils.isEmpty(String.valueOf(text_time_from.getText()))
                && TextUtils.isEmpty(String.valueOf(text_time_end.getText()))) {
            ToastUtil.show(getActivity(), "结束时间不能为空!");

            return;
        }
        if (TextUtils.isEmpty(String.valueOf(text_time_from.getText())) && !TextUtils.isEmpty(String.valueOf(text_time_end.getText()))) {
            ToastUtil.show(getActivity(), "开始时间不能为空!");
            return;
        }
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
        getActivity().unregisterReceiver(receiver2);
    }

    YyMobileCompany yyMobileCompany;
    YyMobileStudent yyMobileStudent;

    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int op = intent.getIntExtra("op", 1);
            String data = intent.getStringExtra("data");
            if (op == 1 || op == 2) {
                yyMobileCompany = GsonUtil.getJsonObject(data, YyMobileCompany.class);
                text_org.setText(yyMobileCompany.getCompanyname());
            } else if (op == 3) {
                yyMobileStudent = GsonUtil.getJsonObject(data, YyMobileStudent.class);
                text_child.setText(yyMobileStudent.getName());
            }
        }
    }

    CourseStatus statusP;
    CourseStatus statusT;
    class MyReceiver2 extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int type = intent.getIntExtra("type",0);
            if(type == 3){
                statusP = (CourseStatus) intent.getSerializableExtra("status");
                text_status.setText(statusP.name);
            }else if(type == 5){
                statusT = (CourseStatus) intent.getSerializableExtra("status");
                text_status.setText(statusT.name);
            }

        }
    }

    @Click(R.id.ll1)
    void ll1Click() {
        Intent intent = new Intent(getActivity(), SearchCourceOrgActivity.class);
        //家长
        if (role == 3) {
            intent.putExtra("op", 2);
        }
        //老师
        else if (role == 5) {
            intent.putExtra("op", 1);
        }
        startActivity(intent);
    }


    @Click(R.id.ll2)
    void ll2Click() {
        Intent intent = new Intent(getActivity(), SearchCourceOrgActivity.class);
        intent.putExtra("op", 3);
        startActivity(intent);
    }

    @Click(R.id.ll3)
    void ll3Click() {
        Intent intent = new Intent(getActivity(), StatusChoiceActivity.class);
        startActivity(intent);
    }

    @Click(R.id.text_time_from)
    void text_time_fromClick() {
        showDateTimePickerDialog(text_time_from);
    }


    @Click(R.id.text_time_end)
    void text_time_endClick() {
        showDateTimePickerDialog(text_time_end);
    }


    private void showDateTimePickerDialog(final TextView textview) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_update_baby_birthday, null);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.datepicker);
        builder.setTitle("选择日期");
        builder.setView(view);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int year = datePicker.getYear();
                String month = datePicker.getMonth() + 1 < 10 ? "0" + (datePicker.getMonth() + 1) : (datePicker.getMonth() + 1) + "";
                String day = datePicker.getDayOfMonth() < 10 ? "0" + datePicker.getDayOfMonth() : datePicker.getDayOfMonth() + "";
                String dateStr = year + "-" + month + "-" + day;
                textview.setText(dateStr);
            }
        });
        builder.create().show();
    }
}
