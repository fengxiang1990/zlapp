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
            //用来标记是否正在向最后一个滑动，既是否向右滑动或向下滑动
            boolean isSlidingToLast = false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部，并且是向右滚动
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        //加载更多功能的代码
                        isLoadMore = true;
                        pageNumber += 1;
                        loadData();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                id_swipe_ly.setEnabled(topRowVerticalPosition >= 0);
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                //大于0表示，正在向右滚动
//小于等于0 表示停止或向左滚动
                isSlidingToLast = dy > 0;
            }
        });
        loadData();
    }


    public void loadData() {
        id_swipe_ly.setRefreshing(true);
        homeService.getHomeCompany(uid, pageNumber, pageSize, new DefaultResponseListener<BaseResponse<List<YyMobileCompany>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileCompany>> response) {
                id_swipe_ly.setRefreshing(false);
                if (response.getStatus().equals(AppConfig.HTTP_OK)) {
                    List<YyMobileCompany> list = response.getResult();
                    if (!isLoadMore) {
                        data.clear();
                    }
                    data.addAll(list);
                    adapter.notifyDataSetChanged();
                    for (YyMobileCompany yyMobileCompany : list) {
                        Log.e(tag, yyMobileCompany.toString());
                    }
                } else {
                    ToastUtil.show(getActivity(), response.getMessage());
                }
            }

            @Override
            public void onError(VolleyError error) {
                id_swipe_ly.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        isLoadMore = false;
        pageNumber = 1;
        loadData();
    }
}
