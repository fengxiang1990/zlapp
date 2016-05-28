package com.zl.app.activity.mine.babyupdate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.mine.MineServiceImpl;
import com.zl.app.util.AppConfig;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

/**
 * Created by fxa on 2016/5/28.
 */
public class UpdateBabyBirthdateActivity extends BaseActivityWithToolBar {

    EditText editText;
    String uid;
    int sid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nickename);
        editText = (EditText) findViewById(R.id.editText);
        editText.setText(getIntent().getStringExtra("text"));
        uid = AppConfig.getUid(preference);
        sid = getIntent().getIntExtra("id", 0);
        setTitle("修改生日");
        setBtnLeft1Enable(true);
        setTextRight1Enable(true);
        setTextRight1Val("保存");
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePickerDialog();
            }
        });
    }

    private void showDateTimePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateBabyBirthdateActivity.this);
        View view = LayoutInflater.from(UpdateBabyBirthdateActivity.this).inflate(R.layout.dialog_update_baby_birthday, null);
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
                String month = datePicker.getMonth() + 1 < 10 ? "0" + (datePicker.getMonth() + 1) : (datePicker.getMonth()+1) + "";
                String day = datePicker.getDayOfMonth() < 10 ? "0" + datePicker.getDayOfMonth() : datePicker.getDayOfMonth() + "";
                String dateStr = year + "-" + month + "-" + day;
                editText.setText(dateStr);
            }
        });
        builder.create().show();
    }

    @Override
    protected void onTextRight1Click() {
        super.onTextRight1Click();
        new MineServiceImpl().updateStudent(uid, sid, null, null,String.valueOf(editText.getText()), null, 0, new DefaultResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                if (response != null && response.getStatus().equals(AppConfig.HTTP_OK)) {
                    finish();
                }
                ToastUtil.show(UpdateBabyBirthdateActivity.this, response.getMessage());
            }

            @Override
            public void onError(VolleyError error) {
            }
        });
    }
}
