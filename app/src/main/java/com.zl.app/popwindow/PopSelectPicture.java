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


/**
 * Created by fengxiang on 2016/3/21.
 */
public class PopSelectPicture extends PopupWindow {

    LayoutInflater inflater;
    Context context;
    View view;
    Handler handler = new Handler();

    public OnPicturePopClickListener getOnPicturePopClickListener() {
        return onPicturePopClickListener;
    }

    public void setOnPicturePopClickListener(OnPicturePopClickListener onPicturePopClickListener) {
        this.onPicturePopClickListener = onPicturePopClickListener;
    }

    private OnPicturePopClickListener onPicturePopClickListener;


    public PopSelectPicture(Context context) {
        super(context);
        Log.e("pop", "create  PopSelectPicture");
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
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
        view = inflater.inflate(R.layout.pop_take_picture, null);
        setContentView(view);
        TextView btn_take = (TextView) view.findViewById(R.id.btn_take);
        TextView btn_pick = (TextView) view.findViewById(R.id.btn_pick);
        View btn_close = view.findViewById(R.id.btn_close);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btn_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPicturePopClickListener != null) {
                    onPicturePopClickListener.onPicturePopClick(OnPicturePopClickListener.PickFromPics);
                }
            }
        });

        btn_take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPicturePopClickListener != null) {
                    onPicturePopClickListener.onPicturePopClick(OnPicturePopClickListener.TakeCamera);
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
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, 300);
        animation.setDuration(250);
        //view.startAnimation(animation);
        view.setVisibility(View.GONE);
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                dismiss2();
            }
        }, 0);

    }

    public void dismiss2() {
        super.dismiss();
    }


    public interface OnPicturePopClickListener {

        int TakeCamera = 1;

        int PickFromPics = 2;

        void onPicturePopClick(int status);
    }
}
