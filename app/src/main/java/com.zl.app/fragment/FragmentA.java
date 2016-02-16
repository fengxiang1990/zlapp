package com.zl.app.fragment;

import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zl.app.BaseFragment;
import com.zl.app.R;
import com.zl.app.data.home.HomeService;
import com.zl.app.data.home.HomeServiceImpl;
import com.zl.app.data.home.model.Ad;
import com.zl.app.data.home.model.News;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by admin on 2015/12/28.
 */

@EFragment(R.layout.fragment_a)
public class FragmentA extends BaseFragment {

    private final String TAG = "FragmentA";
    @ViewById(R.id.container)
    LinearLayout linearLayout;

    List<View> views;
    int pageNo = 1;
    int pageSize = 10;

    HomeService homeService;
    @AfterViews
    void afterViews() {
        homeService = new HomeServiceImpl();
        homeService.getHomeAds(AppConfig.getUid(AppManager.getPreferences()), new DefaultResponseListener<BaseResponse<List<Ad>>>() {
            @Override
            public void onSuccess(BaseResponse<List<Ad>> response) {
                Log.e(TAG,response.toString());
                List<Ad> ads = response.getResult();
                for(Ad ad:ads){
                    Log.e(TAG,ad.toString());
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });

        homeService.getHomeNews(AppConfig.getUid(AppManager.getPreferences()),pageNo,pageSize,new DefaultResponseListener<BaseResponse<List<News>>>(){

            @Override
            public void onSuccess(BaseResponse<List<News>> response) {
                Log.e(TAG,response.toString());
                List<News> news = response.getResult();
                for(News item:news){
                    Log.e(TAG,item.toString());
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }


}
