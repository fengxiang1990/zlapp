package com.zl.app.fragment;

import android.util.Log;

import com.android.volley.VolleyError;
import com.zl.app.BaseFragment;
import com.zl.app.R;
import com.zl.app.data.home.HomeService;
import com.zl.app.data.home.HomeServiceImpl;
import com.zl.app.model.customer.YyMobileCompany;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import java.util.List;

/**
 * 发现
 * Created by fengxiang on 2016/3/28.
 */
@EFragment(R.layout.fragment_find)
public class FragmentFind extends BaseFragment{


    String tag = FragmentFind.class.getName();
    HomeService homeService;
    int pageSize =20;
    int pageNumber =1;
    String uid;
    @AfterViews
    void afterViews(){
        homeService=new HomeServiceImpl();
        uid = AppConfig.getUid(AppManager.getPreferences());
        homeService.getHomeCompany(uid, new DefaultResponseListener<BaseResponse<List<YyMobileCompany>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileCompany>> response) {
                if(response.getStatus().equals(AppConfig.HTTP_OK)){
                    List<YyMobileCompany> list  = response.getResult();
                    for(YyMobileCompany yyMobileCompany:list){
                        Log.e(tag,yyMobileCompany.toString());
                    }
                }else{
                    ToastUtil.show(getActivity(),response.getMessage());
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

}
