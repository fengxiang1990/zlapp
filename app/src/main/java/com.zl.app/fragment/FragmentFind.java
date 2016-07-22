package com.zl.app.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.zl.app.BaseFragment;
import com.zl.app.MyApplication;
import com.zl.app.R;
import com.zl.app.activity.org.OrgListActivity_;
import com.zl.app.adapter.OrgAdapter;
import com.zl.app.data.home.HomeService;
import com.zl.app.data.home.HomeServiceImpl;
import com.zl.app.data.model.customer.YyMobileCompany;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.view.LoadingDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.loadingviewfinal.OnLoadMoreListener;
import cn.finalteam.loadingviewfinal.RecyclerViewFinal;
import cn.finalteam.loadingviewfinal.SwipeRefreshLayoutFinal;

/**
 * 发现
 * Created by fengxiang on 2016/3/28.
 */
@EFragment(R.layout.fragment_initiation)
public class FragmentFind extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener, OrgAdapter.OnHeaderMenuClickListener, BDLocationListener {


    String tag = FragmentFind.class.getName();

    @ViewById(R.id.swipe)
    SwipeRefreshLayoutFinal id_swipe_ly;

    @ViewById(R.id.recyclerView)
    RecyclerViewFinal recyclerView;

    LinearLayoutManager layoutManager;
    OrgAdapter adapter;
    List<YyMobileCompany> data;

    HomeService homeService;
    int pageSize = 20;
    int pageNumber = 1;
    String uid;
    public boolean isLoadMore = false;

    @AfterViews
    void afterViews() {
        recyclerView.setOnLoadMoreListener(this);
        recyclerView.setHasLoadMore(true);
        id_swipe_ly.setOnRefreshListener(this);
        data = new ArrayList<YyMobileCompany>();
        adapter = new OrgAdapter(getActivity(), data);
        adapter.setListener(this);
        adapter.isShowHeader = true;
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        homeService = new HomeServiceImpl();
//        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            //用来标记是否正在向最后一个滑动，既是否向右滑动或向下滑动
//            boolean isSlidingToLast = false;
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                // 当不滚动时
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    //获取最后一个完全显示的ItemPosition
//                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
//                    int totalItemCount = manager.getItemCount();
//
//                    // 判断是否滚动到底部，并且是向右滚动
//                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
//                        //加载更多功能的代码
//                        isLoadMore = true;
//                        pageNumber += 1;
//                        loadData();
//                    }
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                int topRowVerticalPosition =
//                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
//                id_swipe_ly.setEnabled(topRowVerticalPosition >= 0);
//                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
//                //大于0表示，正在向右滚动
////小于等于0 表示停止或向左滚动
//                isSlidingToLast = dy > 0;
//            }
//        });
        mLocationClient = ((MyApplication) getActivity().getApplication()).mLocationClient;
        mLocationClient.registerLocationListener(this);
        uid = AppConfig.getUid(AppManager.getPreferences());
        id_swipe_ly.setRefreshing(true);
        LoadingDialog.getInstance(getActivity()).show();
        loadData();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public LocationClient mLocationClient = null;

    public void loadData() {
        mLocationClient.start();
    }

    @Override
    public void onRefresh() {
        isLoadMore = false;
        pageNumber = 1;
        loadData();
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.textView1:
                intent = new Intent(getActivity(), OrgListActivity_.class);
                intent.putExtra("TYPE_ID", 3);
                startActivity(intent);
                break;
            case R.id.textView2:
                intent = new Intent(getActivity(), OrgListActivity_.class);
                intent.putExtra("TYPE_ID", 4);
                startActivity(intent);
                break;
            case R.id.textView3:
                intent = new Intent(getActivity(), OrgListActivity_.class);
                intent.putExtra("TYPE_ID", 9);
                startActivity(intent);
                break;
            case R.id.textView4:
                intent = new Intent(getActivity(), OrgListActivity_.class);
                intent.putExtra("TYPE_ID", 5);
                startActivity(intent);
                break;
            case R.id.textView5:
                intent = new Intent(getActivity(), OrgListActivity_.class);
                intent.putExtra("TYPE_ID", 6);
                startActivity(intent);
                break;
            case R.id.textView6:
                intent = new Intent(getActivity(), OrgListActivity_.class);
                intent.putExtra("TYPE_ID", 8);
                startActivity(intent);
                break;
            case R.id.textView7:
                intent = new Intent(getActivity(), OrgListActivity_.class);
                intent.putExtra("TYPE_ID", 7);
                startActivity(intent);
                break;
            case R.id.textView8:
                intent = new Intent(getActivity(), OrgListActivity_.class);
                intent.putExtra("TYPE_ID", -1);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        mLocationClient.stop();
        Log.i(tag, "addr:" + bdLocation.getAddrStr());
        Log.i(tag, "longitude:" + bdLocation.getLongitude());
        Log.i(tag, "latitude:" + bdLocation.getLatitude());
        AppConfig.latitude = String.valueOf(bdLocation.getLatitude());
        AppConfig.longitude = String.valueOf(bdLocation.getLongitude());
        homeService.getHomeCompany(AppConfig.getUid(AppManager.getPreferences()), pageNumber, pageSize, new DefaultResponseListener<BaseResponse<List<YyMobileCompany>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileCompany>> response) {
                LoadingDialog.getInstance(getActivity()).dismiss();
                id_swipe_ly.onRefreshComplete();
                recyclerView.onLoadMoreComplete();
                if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                    List<YyMobileCompany> list = response.getResult();
                    if (!isLoadMore) {
                        data.clear();
                        data.add(0, null);
                    }
                    data.addAll(list);
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtil.show(getActivity(), response.getMessage());
                }
            }

            @Override
            public void onError(VolleyError error) {
                LoadingDialog.getInstance(getActivity()).dismiss();
                id_swipe_ly.onRefreshComplete();
                recyclerView.onLoadMoreComplete();
            }
        });
    }

    @Override
    public void loadMore() {
        isLoadMore = true;
        pageNumber += 1;
        loadData();
    }
}
