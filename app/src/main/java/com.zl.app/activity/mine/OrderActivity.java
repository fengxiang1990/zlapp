package com.zl.app.activity.mine;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.mine.MineService;
import com.zl.app.data.mine.MineServiceImpl;
import com.zl.app.data.model.customer.YyMobileContract;
import com.zl.app.util.AppConfig;
import com.zl.app.util.CameraUtil;
import com.zl.app.util.OpenFiles;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengxiang on 2016/4/25.
 */
public class OrderActivity extends BaseActivityWithToolBar {

    String tag = "OrderActivity";
    ListView listView;
    DownloadManager manager;
    MineService mineService;
    String uid;
    MyAdapter adapter;
    List<YyMobileContract> data;
    DownloadCompleteReceiver receiver;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        listView = (ListView) findViewById(R.id.listView);
        setBtnLeft1Enable(true);
        setTitle("我的订单");
        manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        receiver = new DownloadCompleteReceiver();
        mineService = new MineServiceImpl();
        data = new ArrayList<YyMobileContract>();
        adapter = new MyAdapter(data);
        listView.setAdapter(adapter);
        uid = AppConfig.getUid(preference);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                YyMobileContract yyMobileContract = data.get(position);
                Log.e(tag, yyMobileContract.getFilePath());
                if(TextUtils.isEmpty(yyMobileContract.getFilePath())){
                    return;
                }
                DownloadManager.Request down = new DownloadManager.Request(Uri.parse(yyMobileContract.getFilePath()));
                down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                down.setShowRunningNotification(true);
                down.setVisibleInDownloadsUi(false);
                //设置标题
                down.setTitle(yyMobileContract.getHeadline());
                //显示下载界面
                down.setVisibleInDownloadsUi(true);
                //设置下载路径  /mnt/sosspad/download
                String filePath = yyMobileContract.getFilePath();
                if (!TextUtils.isEmpty(filePath)) {
                    String subfix = filePath.substring(filePath.lastIndexOf("."));
                    Log.e(tag,subfix);
                    down.setDestinationInExternalPublicDir("/download/", yyMobileContract.getHeadline() + subfix);
                    //down.setDestinationInExternalFilesDir(OrderActivity.this, null,"ZLDownload");
                    manager.enqueue(down);
                    ToastUtil.show(OrderActivity.this, "开始下载文档");
                }

            }
        });
        mineService.getOrders(uid, new DefaultResponseListener<BaseResponse<List<YyMobileContract>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileContract>> response) {
                if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                    data.addAll(response.getResult());
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtil.show(OrderActivity.this, response.getMessage());
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    //接受下载完成后的intent
    class DownloadCompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                Uri uri = manager.getUriForDownloadedFile(downId);
                File currentPath = CameraUtil.getFileByUri(OrderActivity.this, uri);
                ToastUtil.show(context, "下载完成");
                if (currentPath != null && currentPath.isFile()) {
                    String fileName = currentPath.toString();
                    if (OpenFiles.checkEndsWithInStringArray(fileName, getResources().
                            getStringArray(R.array.fileEndingImage))) {
                        intent = OpenFiles.getImageFileIntent(currentPath);
                        startActivity(intent);
                    } else if (OpenFiles.checkEndsWithInStringArray(fileName, getResources().
                            getStringArray(R.array.fileEndingWebText))) {
                        intent = OpenFiles.getHtmlFileIntent(currentPath);
                        startActivity(intent);
                    } else if (OpenFiles.checkEndsWithInStringArray(fileName, getResources().
                            getStringArray(R.array.fileEndingPackage))) {
                        intent = OpenFiles.getApkFileIntent(currentPath);
                        startActivity(intent);

                    } else if (OpenFiles.checkEndsWithInStringArray(fileName, getResources().
                            getStringArray(R.array.fileEndingAudio))) {
                        intent = OpenFiles.getAudioFileIntent(currentPath);
                        startActivity(intent);
                    } else if (OpenFiles.checkEndsWithInStringArray(fileName, getResources().
                            getStringArray(R.array.fileEndingVideo))) {
                        intent = OpenFiles.getVideoFileIntent(currentPath);
                        startActivity(intent);
                    } else if (OpenFiles.checkEndsWithInStringArray(fileName, getResources().
                            getStringArray(R.array.fileEndingText))) {
                        intent = OpenFiles.getTextFileIntent(currentPath);
                        startActivity(intent);
                    } else if (OpenFiles.checkEndsWithInStringArray(fileName, getResources().
                            getStringArray(R.array.fileEndingPdf))) {
                        intent = OpenFiles.getPdfFileIntent(currentPath);
                        startActivity(intent);
                    } else if (OpenFiles.checkEndsWithInStringArray(fileName, getResources().
                            getStringArray(R.array.fileEndingWord))) {
                        intent = OpenFiles.getWordFileIntent(currentPath);
                        startActivity(intent);
                    } else if (OpenFiles.checkEndsWithInStringArray(fileName, getResources().
                            getStringArray(R.array.fileEndingExcel))) {
                        intent = OpenFiles.getExcelFileIntent(currentPath);
                        startActivity(intent);
                    } else if (OpenFiles.checkEndsWithInStringArray(fileName, getResources().
                            getStringArray(R.array.fileEndingPPT))) {
                        intent = OpenFiles.getPPTFileIntent(currentPath);
                        startActivity(intent);
                    } else {
                        ToastUtil.show(OrderActivity.this, "无法打开，请安装相应的软件！");
                    }
                } else {
                    ToastUtil.show(OrderActivity.this, "对不起，这不是文件！");
                }
            }
        }
    }

    @Override
    protected void onResume() {
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (receiver != null) unregisterReceiver(receiver);
        super.onDestroy();
    }

    class MyAdapter extends BaseAdapter {

        List<YyMobileContract> data;

        public MyAdapter(List<YyMobileContract> data) {
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
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_order, null);
                holder = new ViewHolder();
                holder.text_class_name = (TextView) convertView.findViewById(R.id.text_class_name);
                holder.text_time = (TextView) convertView.findViewById(R.id.text_time);
                holder.text_sum = (TextView) convertView.findViewById(R.id.text_sum);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final YyMobileContract order = data.get(position);
            if (order != null) {
                holder.text_class_name.setText(order.getHeadline());
                holder.text_time.setText(order.getCreateDate());
                holder.text_sum.setText(TextUtils.isEmpty(order.getCharge()) ? "0" : order.getCharge());
            }
            return convertView;
        }

    }

    class ViewHolder {
        TextView text_class_name;
        TextView text_time;
        TextView text_sum;

    }
}
