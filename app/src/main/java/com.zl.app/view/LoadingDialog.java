package com.zl.app.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zl.app.R;


public class LoadingDialog {

    String TAG = LoadingDialog.class.getName();
    static Context context;
    Dialog dialog;
    Window window;
    LinearLayout outter;
    private ProgressBar progressBar;
    private TextView titleView;

    private static LoadingDialog loadingDialog;

    private LoadingDialog(Context context) {
        this.context = context;
        build();
    }

    public static LoadingDialog getInstance(Context context) {
        if(loadingDialog == null){
            loadingDialog = new LoadingDialog(context);
        }
        //上下文发生改变 重新创建dialog
        if(LoadingDialog.context!=null && LoadingDialog.context!=context){
            Log.e("LoadingDialog","context had been changed");
            loadingDialog = new LoadingDialog(context);
        }
        return loadingDialog;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public TextView getTitleView() {
        return titleView;
    }

    public void setTitleView(TextView titleView) {
        this.titleView = titleView;
    }

    protected void build() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialog = builder.create();
        dialog.show();
        window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable());
        window.setContentView(R.layout.loading_dialog_layout);
        outter = (LinearLayout) window.findViewById(R.id.outter);
        progressBar = (ProgressBar) window.findViewById(R.id.progressBar);
        titleView = (TextView) window.findViewById(R.id.text_title);
        outter.setAlpha(0.1f);
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.FILL);
        lp.width = LayoutParams.MATCH_PARENT; // 宽度
        lp.height = LayoutParams.MATCH_PARENT;
        ; // 高度
        window.setAttributes(lp);

    }

    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            try {
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public LoadingDialog setTitle(String title) {
        getTitleView().setText(title);
        return loadingDialog;
    }

    Handler handler = new Handler();

}
