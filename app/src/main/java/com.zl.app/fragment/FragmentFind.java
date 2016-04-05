package com.zl.app.fragment;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.zl.app.BaseFragment;
import com.zl.app.R;
import com.zl.app.adapter.OrgAdapter;
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
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现
 * Created by fengxiang on 2016/3/28.
 */
@EFragment(R.layout.fragment_find)
public class FragmentFind extends BaseFragment{


    String tag = FragmentFind.class.getName();

    @ViewById(R.id.recyclerView)
    RecyclerView recyclerView;

    LinearLayoutManager layoutManager;
    OrgAdapter adapter;
    List<YyMobileCompany> data;

    HomeService homeService;
    int pageSize =20;
    int pageNumber =1;
    String uid;
    public boolean isLoadMore = true;
    @AfterViews
    void afterViews(){
        data = new ArrayList<YyMobileCompany>();
        adapter = new OrgAdapter(this,data);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        homeService=new HomeServiceImpl();
        uid = AppConfig.getUid(AppManager.getPreferences());
        homeService.getHomeCompany(uid, new DefaultResponseListener<BaseResponse<List<YyMobileCompany>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileCompany>> response) {
                if(response.getStatus().equals(AppConfig.HTTP_OK)){
                    List<YyMobileCompany> list  = response.getResult();
                    if(!isLoadMore){
                        data.clear();
                    }
                    data.addAll(list);
                    adapter.notifyDataSetChanged();
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
