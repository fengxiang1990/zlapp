package com.zl.app.activity.org;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.home.HomeService;
import com.zl.app.data.home.HomeServiceImpl;
import com.zl.app.data.home.model.OrgType;
import com.zl.app.model.customer.YyMobileCompany;
import com.zl.app.model.user.YyMobileCity;
import com.zl.app.util.AppConfig;
import com.zl.app.util.GsonUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengxiang on 2016/5/5.
 */
public class ClassSelectActivity extends BaseActivityWithToolBar {

    ListView listView;
    List<OrgType> data;
    ArrayAdapter<OrgType> adapter;
    HomeService homeService;
    String uid;
    public static final String classSelectionBroadcast = "com.zl.app.org.class.selected";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_select);
        listView = (ListView) findViewById(R.id.listView4);
        setTitle("选择分类");
        uid = AppConfig.getUid(preference);
        homeService = new HomeServiceImpl();
        setBtnLeft1Enable(true);
        data = new ArrayList<OrgType>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
        loadData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrgType orgType = data.get(position);
                Intent intent = new Intent();
                intent.setAction(classSelectionBroadcast);
                intent.putExtra("org_type", GsonUtil.gson.toJson(orgType));
                sendBroadcast(intent);
                finish();
            }
        });
    }

    public void loadData() {
        homeService.getOrgTypes(uid, new DefaultResponseListener<BaseResponse<List<OrgType>>>() {
            @Override
            public void onSuccess(BaseResponse<List<OrgType>> response) {
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
