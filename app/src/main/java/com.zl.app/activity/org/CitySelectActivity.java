package com.zl.app.activity.org;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.home.HomeService;
import com.zl.app.data.home.HomeServiceImpl;
import com.zl.app.data.model.user.YyMobileCity;
import com.zl.app.util.AppConfig;
import com.zl.app.util.GsonUtil;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengxiang on 2016/5/5.
 */
public class CitySelectActivity extends BaseActivityWithToolBar {

    ListView listView;
    List<YyMobileCity> data;
    ArrayAdapter<YyMobileCity> adapter;
    HomeService homeService;
    public static final String addressSelectionBroadcast = "com.zl.app.org.city.selected";
    String uid;
    YyMobileCity level1;//省
    YyMobileCity level2;//市
    YyMobileCity level3;//区
    YyMobileCity level4;//街道

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_select);
        setTitle("选择地址");
        uid = AppConfig.getUid(preference);
        homeService = new HomeServiceImpl();
        setBtnLeft1Enable(true);
        data = new ArrayList<YyMobileCity>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
        listView = (ListView) findViewById(R.id.listView3);
        listView.setAdapter(adapter);
        loadData(0);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                YyMobileCity yyMobileCity = data.get(position);
                if(yyMobileCity.getName().equals("全部")){
                    Intent intent = new Intent();
                    intent.setAction(addressSelectionBroadcast);
                    List<YyMobileCity> cities = new ArrayList<YyMobileCity>();
                    if(level1!=null){
                        cities.add(level1);
                    }
                    if(level2!=null){
                        cities.add(level2);
                    }
                    if(level3!=null){
                        cities.add(level3);
                    }
                    intent.putExtra("city_address", GsonUtil.gson.toJson(cities));
                    sendBroadcast(intent);
                    finish();
                    return;
                }
                if (level1 == null) {
                    level1 = yyMobileCity;
                    loadData(level1.getCityId());
                    setTitle(level1.getName());
                } else if (level2 == null) {
                    level2 = yyMobileCity;
                    loadData(level2.getCityId());
                    setTitle(level2.getName());
                } else if (level3 == null) {
                    level3 = yyMobileCity;
                    loadData(level3.getCityId());
                    setTitle(level3.getName());
                } else {
                    level4 = yyMobileCity;
                }
                if (level1 != null && level2 != null && level3 != null && level4 != null) {
                    Intent intent = new Intent();
                    intent.setAction(addressSelectionBroadcast);
                    List<YyMobileCity> cities = new ArrayList<YyMobileCity>();
                    cities.add(level1);
                    cities.add(level2);
                    cities.add(level3);
                    cities.add(level4);
                    intent.putExtra("city_address", GsonUtil.gson.toJson(cities));
                    sendBroadcast(intent);
                    finish();
                }
            }
        });
    }

    public void loadData(final int cityId) {
        homeService.getCityAddress(uid, cityId, new DefaultResponseListener<BaseResponse<List<YyMobileCity>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileCity>> response) {
                if (response != null && response.getResult() != null) {
                    data.clear();
                    if(cityId!=0) {
                        YyMobileCity total = new YyMobileCity();
                        total.setName("全部");
                        total.setCityId(cityId);
                        data.add(total);
                    }
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
