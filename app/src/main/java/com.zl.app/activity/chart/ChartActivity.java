package com.zl.app.activity.chart;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.CameraActivity;
import com.zl.app.R;
import com.zl.app.data.ChartService;
import com.zl.app.data.model.user.YyMobileLetter;
import com.zl.app.data.user.UserServiceImpl;
import com.zl.app.popwindow.PopSelectPicture;
import com.zl.app.util.AppConfig;
import com.zl.app.util.GsonUtil;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.ViewUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.view.LoadingDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.maxwin.view.XListView;

/**
 * 聊天页面
 * Created by fxa on 2016/5/31.
 */
public class ChartActivity extends CameraActivity {

    String tag = "ChartActivity";
    int userId;
    int mUserId;
    String userName;
    ChartService chartService;
    XListView listView;
    LinearLayout ll_bootom;
    ImageView img_take_pic;
    EditText edit_content;
    TextView text_send;
    String uid;
    int maxContentId = 0;
    int minContentId = 0;
    PopSelectPicture popSelectPicture;
    List<YyMobileLetter> data;
    ChartAdapter adapter;
    boolean isLoadNew = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uid = AppConfig.getUid(preference);
        mUserId = AppConfig.getUserId(preference);
        setContentView(R.layout.activity_chart);
        chartService = new ChartService();
        popSelectPicture = new PopSelectPicture(this);
        popSelectPicture.setOnPicturePopClickListener(this);
        userId = getIntent().getIntExtra("userId", 0);
        userName = getIntent().getStringExtra("userName");
        setTitle(userName);
        setBtnLeft1Enable(true);
        listView = (XListView) findViewById(R.id.listView);
        ll_bootom = (LinearLayout) findViewById(R.id.ll_bottom);
        img_take_pic = (ImageView) findViewById(R.id.img_take_pic);
        edit_content = (EditText) findViewById(R.id.edit_content);
        text_send = (TextView) findViewById(R.id.text_send);

        listView.setPullLoadEnable(false);
        data = new ArrayList<>();
        adapter = new ChartAdapter(data);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.e("scrollState",scrollState+"");
                if(scrollState == 0 && isLoadNew){
                   // listView.setSelection(adapter.getCount()-1);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                isLoadNew = false;
                minContentId = data.get(0).getContentId();
                loadLastPageMessages();
            }

            @Override
            public void onLoadMore() {

            }
        });

        img_take_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popSelectPicture.showAtLocation(ll_bootom, Gravity.BOTTOM, 0, 0);
            }
        });
        text_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send(null);
            }
        });

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(isLoadNew){
                    loadCurrentPageMessages();
                }
            }
        }, 0,1500);
    }

    @Override
    protected void onGetImageSuccess(File file) {
        popSelectPicture.dismiss();
        LoadingDialog.getInstance(ChartActivity.this).setTitle("发送图片中...").show();
        new UserServiceImpl().uploadUserHeadImg(AppConfig.getUid(preference), file, new DefaultResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                LoadingDialog.getInstance(ChartActivity.this).dismiss();
                if (!TextUtils.isEmpty(response)) {
                    BaseResponse baseResponse = GsonUtil.getJsonObject(response, BaseResponse.class);
                    final String imgUrl = baseResponse.getMessage();
                    Log.e("ChartActivity", "imgUrl--->" + imgUrl);
                    send(imgUrl);
                }
            }

            @Override
            public void onError(VolleyError error) {
                ToastUtil.show(ChartActivity.this, error.getMessage());
                LoadingDialog.getInstance(ChartActivity.this).dismiss();
            }
        });
    }


    public void send(String picPath) {
        if (TextUtils.isEmpty(String.valueOf(edit_content.getText()))
                && TextUtils.isEmpty(picPath)) {
            return;
        }
        chartService.send(uid, userId + "", String.valueOf(edit_content.getText()), picPath, new DefaultResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                if (response != null) {
                    if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                        edit_content.setText("");
                    } else {
                        ToastUtil.show(ChartActivity.this, response.getMessage());
                    }
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    public void loadLastPageMessages() {
        chartService.getOldList(uid, userId + "", minContentId, new DefaultResponseListener<BaseResponse<List<YyMobileLetter>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileLetter>> response) {
                if (response != null && response.getResult() != null) {
                    data.addAll(0,response.getResult());
                    adapter.notifyDataSetChanged();
                    listView.stopRefresh();
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }


    public void loadCurrentPageMessages() {
        chartService.getNewList(uid, userId + "", maxContentId, new DefaultResponseListener<BaseResponse<List<YyMobileLetter>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileLetter>> response) {
                if (response != null && response.getResult() != null) {
                    data.clear();
                    data.addAll(response.getResult());
                    adapter.notifyDataSetChanged();
                   // if(isLoadNew){
                        //listView.setSelection(adapter.getCount()-1);
                  //  }
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }


    class ChartAdapter extends BaseAdapter {
        List<YyMobileLetter> data;

        public ChartAdapter(List<YyMobileLetter> data) {
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
                convertView = LayoutInflater.from(ChartActivity.this).inflate(R.layout.item_char_msg, null);
                holder.left = (LinearLayout) convertView.findViewById(R.id.left);
                holder.right = (LinearLayout) convertView.findViewById(R.id.right);
                holder.img_header_left = (SimpleDraweeView) convertView.findViewById(R.id.img_header_left);
                holder.img_header_right = (SimpleDraweeView) convertView.findViewById(R.id.img_header_right);
                holder.text_left_content = (TextView) convertView.findViewById(R.id.text_left_content);
                holder.text_right_content = (TextView) convertView.findViewById(R.id.text_right_content);
                holder.img_left = (SimpleDraweeView) convertView.findViewById(R.id.img_left_comment);
                holder.img_right = (SimpleDraweeView) convertView.findViewById(R.id.img_right_comment);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if(position == data.size()-1){
                isLoadNew = true;
            }
            YyMobileLetter yyMobileLetter = data.get(position);
            //这条记录是好友的
            if (yyMobileLetter.getCreateBy() == userId) {
                ViewUtil.show(holder.left);
                ViewUtil.hide(holder.right);
                holder.img_header_left.setImageURI(Uri.parse(yyMobileLetter.getPicPath()));
                holder.text_left_content.setText(yyMobileLetter.getContent());
                if (!TextUtils.isEmpty(yyMobileLetter.getContentPic())) {
                    ViewUtil.show(holder.img_left);
                    holder.img_left.setImageURI(Uri.parse(yyMobileLetter.getContentPic()));
                }else{
                    ViewUtil.hide(holder.img_left);
                }
            }
            //这条记录是我发的
            if (yyMobileLetter.getCreateBy() == mUserId) {
                ViewUtil.show(holder.right);
                ViewUtil.hide(holder.left);
                holder.img_header_right.setImageURI(Uri.parse(yyMobileLetter.getPicPath()));
                holder.text_right_content.setText(yyMobileLetter.getContent());
                if (!TextUtils.isEmpty(yyMobileLetter.getContentPic())) {
                    ViewUtil.show(holder.img_right);
                    holder.img_right.setImageURI(Uri.parse(yyMobileLetter.getContentPic()));
                }else{
                    ViewUtil.hide(holder.img_right);
                }
            }
            return convertView;
        }
    }

    class ViewHolder {
        LinearLayout left;
        LinearLayout right;
        SimpleDraweeView img_header_left;
        SimpleDraweeView img_header_right;
        TextView text_left_content;
        TextView text_right_content;
        SimpleDraweeView img_left;
        SimpleDraweeView img_right;
    }
}
