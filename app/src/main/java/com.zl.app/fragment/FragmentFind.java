package com.zl.app.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
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
public class FragmentFind extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    String tag = FragmentFind.class.getName();

    @ViewById
    SwipeRefreshLayout id_swipe_ly;

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
        id_swipe_ly.setOnRefreshListener(this);
        data = new ArrayList<YyMobileCompany>();
        adapter = new OrgAdapter(this,data);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        homeService=new HomeServiceImpl();
        uid = AppConfig.getUid(AppManager.getPreferences());

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                id_swipe_ly.setEnabled(topRowVerticalPosition >= 0);
            }
        });

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


    @Override
    public void onRefresh() {

    }
}
