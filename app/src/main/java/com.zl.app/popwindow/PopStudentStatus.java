package com.zl.app.popwindow;

import android.app.Service;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zl.app.R;
import com.zl.app.data.CourseService;

/**
 * Created by fengxiang on 2016/4/22.
 */
public class PopStudentStatus extends PopupWindow {

    LayoutInflater inflater;
    Context context;
    View view;
    Handler handler = new Handler();


    public OnSelectedListener getListener() {
        return listener;
    }

    public void setListener(OnSelectedListener listener) {
        this.listener = listener;
    }

    private OnSelectedListener listener;


    public PopStudentStatus(Context context) {
        super(context);
        Log.e("pop", "create  PopSelectPicture");
        this.setWidth(350);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        BitmapDrawable drawable = new BitmapDrawable();
        this.setBackgroundDrawable(drawable);// 这样设置才能点击屏幕外dismiss窗口
        this.setOutsideTouchable(true);
        this.setFocusable(true);
        inflater = (LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        super.update();
        initView();
    }


    public void initView() {
        view = inflater.inflate(R.layout.pop_course_student, null);
        setContentView(view);
        TextView btn_chuxi = (TextView) view.findViewById(R.id.btn_chuxi);
        TextView btn_qingjia = (TextView) view.findViewById(R.id.btn_qingjia);
        TextView btn_bujia = (TextView) view.findViewById(R.id.btn_bujia);
        TextView btn_yishangke = (TextView) view.findViewById(R.id.btn_yishangke);

        btn_chuxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onSelected(CourseService.CourseStatusP.ZHENGCHANG);
                    dismiss();
                }
            }
        });

        btn_qingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onSelected(CourseService.CourseStatusP.QINGJIA);
                    dismiss();
                }
            }
        });

        btn_bujia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onSelected(CourseService.CourseStatusP.BUJIA);
                    dismiss();
                }
            }
        });

        btn_yishangke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onSelected(CourseService.CourseStatusP.YISHANGKE);
                    dismiss();
                }
            }
        });

    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        if (isShowing()) {
            dismiss();
            return;
        }
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animation = new TranslateAnimation(0, 0, 300, 0);
        animation.setDuration(250);
        //view.setAnimation(animation);
        //animation.start();
        super.showAtLocation(parent, gravity, x, y);
    }


    @Override
    public void dismiss() {
        /**
         TranslateAnimation animation = new TranslateAnimation(0, 0, 0, 250);
         animation.setDuration(200);
         //view.startAnimation(animation);
         view.setVisibility(View.GONE);
         handler.postDelayed(new Runnable() {

        @Override public void run() {
        dismiss2();
        }
        }, 0);
         **/
        super.dismiss();
    }

    public void dismiss2() {
        super.dismiss();
    }


    public interface OnSelectedListener {

        void onSelected(int status);
    }
}