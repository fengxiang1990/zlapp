package com.zl.app.activity.mine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.zl.app.R;
import com.zl.app.activity.activities.HisActivitiesActivity;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.mine.MineService;
import com.zl.app.data.mine.MineServiceImpl;
import com.zl.app.data.model.user.YyMobileUserFans;
import com.zl.app.data.user.UserServiceImpl;
import com.zl.app.util.AppConfig;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengxiang on 2016/4/25.
 */
public class FrendsActivity extends BaseActivityWithToolBar {

    ListView listView;
    MineService mineService;
    String uid;
    MyAdapter adapter;
    List<YyMobileUserFans> data;
    int pageNo = 1;
    int pageSize = 1000;
    IntentIntegrator intentIntegrator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frends);
        listView = (ListView) findViewById(R.id.listView);
        setBtnLeft1Enable(true);
        setTitle("我的朋友");
        // setTextRight1Enable(true);
        // setTextRight1Val("扫一扫");
        setBtnRight2ImageResource(R.mipmap.find_search_icon);
        setBtnRight1ImageResource(R.mipmap.qr_code_scan);
        setBtnRight1Enable(true);
        setBtnRight2Enable(true);
        mineService = new MineServiceImpl();
        data = new ArrayList<YyMobileUserFans>();
        adapter = new MyAdapter(data);
        listView.setAdapter(adapter);
        uid = AppConfig.getUid(preference);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final YyMobileUserFans fans = data.get(position);
                new AlertDialog.Builder(FrendsActivity.this)
                        .setTitle("删除好友")
                        .setMessage("是否要删除好友?")
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                new MineServiceImpl().deleteFrend(uid, fans.getPersonId() + "", new DefaultResponseListener<BaseResponse>() {
                                    @Override
                                    public void onSuccess(BaseResponse response) {
                                        if (response != null) {
                                            loadData();
                                            ToastUtil.show(FrendsActivity.this, response.getMessage());
                                            dialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onError(VolleyError error) {
                                        ToastUtil.show(FrendsActivity.this, error.getMessage());
                                    }
                                });
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                        .show();
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                YyMobileUserFans fans = data.get(position);
                Intent intent = new Intent(FrendsActivity.this, HisActivitiesActivity.class);
                intent.putExtra("userId", fans.getPersonId());
                intent.putExtra("userName", fans.getPersonName());
                startActivity(intent);
            }
        });
        loadData();
        intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setCaptureActivity(CaptureActivityAnyOrientation.class);
        intentIntegrator.setOrientationLocked(false);


    }

    void loadData() {
        mineService.getFrends(uid, pageNo, pageSize, new DefaultResponseListener<BaseResponse<List<YyMobileUserFans>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileUserFans>> response) {
                if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                    data.clear();
                    data.addAll(response.getResult());
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtil.show(FrendsActivity.this, response.getMessage());
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });

    }

    @Override
    protected void onBtnRight1Click() {
        super.onBtnRight1Click();
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onBtnRight2Click() {
        super.onBtnRight2Click();
        Intent intent = new Intent(FrendsActivity.this, FrendSearchActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                // Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                // Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                Log.e("FrendsActivity", "Scanned: " + result.getContents());
                String url = result.getContents();
                url += "&uid=" + uid;
                url += "&gztype=2";
                new UserServiceImpl().addFrend(url, new DefaultResponseListener<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        if (response != null && response.getStatus().equals(AppConfig.HTTP_OK)) {
                            ToastUtil.show(FrendsActivity.this, "已添加好友");
                            loadData();
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    class MyAdapter extends BaseAdapter {

        List<YyMobileUserFans> data;

        public MyAdapter(List<YyMobileUserFans> data) {
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
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_frend, null);
                holder = new ViewHolder();
                holder.img_header = (SimpleDraweeView) convertView.findViewById(R.id.img_header);
                holder.text_name = (TextView) convertView.findViewById(R.id.text_name);
                holder.text_activity = (TextView) convertView.findViewById(R.id.text_activity);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final YyMobileUserFans fans = data.get(position);
            if (fans != null) {
                holder.img_header.setImageURI(Uri.parse(fans.getPersonPic()));
                holder.text_name.setText(fans.getPersonName());
                holder.text_activity.setText(fans.getHdheadline());
            }
            return convertView;
        }

    }

    class ViewHolder {
        SimpleDraweeView img_header;
        TextView text_name;
        TextView text_activity;
    }
}
