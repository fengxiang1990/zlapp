package com.zl.app.activity.org;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.zl.app.BaseActivity;
import com.zl.app.R;
import com.zl.app.adapter.OrgAdapter;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.home.HomeService;
import com.zl.app.data.home.HomeServiceImpl;
import com.zl.app.model.customer.YyMobileCompany;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengxiang on 2016/4/12.
 */
@EActivity(R.layout.activity_org_list)
public class OrgListActivity extends BaseActivityWithToolBar implements SwipeRefreshLayout.OnRefreshListener {
    String tag = OrgListActivity.class.getName();
    @ViewById(R.id.swipe)
    SwipeRefreshLayout swipe;
    @ViewById(R.id.recyclerView)
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    OrgAdapter adapter;
    List<YyMobileCompany> data;
    int typeId = 0;
    HomeService homeService;
    int pageSize = 20;
    int pageNumber = 1;
    String uid;
    public boolean isLoadMore = true;
    String companyname = "";

    @AfterViews
    void afterviews() {
        swipe.setOnRefreshListener(this);
        homeService = new HomeServiceImpl();
        data = new ArrayList<YyMobileCompany>();
        adapter = new OrgAdapter(OrgListActivity.this, data);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        typeId = getIntent().getIntExtra("TYPE_ID", 0);
        uid = AppConfig.getUid(AppManager.getPreferences());

        String title = "";
        if (typeId == 3) {
            title = "英语";
        } else if (typeId == 4) {
            title = "语文";
        } else if (typeId == 9) {
            title = "数学";
        } else if (typeId == 5) {
            title = "美术";
        } else if (typeId == 6) {
            title = "音乐";
        } else if (typeId == 8) {
            title = "乐高";
        } else if (typeId == 7) {
            title = "舞蹈";
        } else if (typeId == -1) {
            title = "更多";
        }

        setTitle(title);
        setBtnLeft1Enable(true);
        setSearchTitleViewEnable(true);
        setBtnRight1Enable(true);
        setBtnRight1ImageResource(R.mipmap.find_select_icon);
        searchTitleView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    pageNumber = 1;
                    isLoadMore = false;
                    companyname = searchTitleView.getText().toString();
                    loadData();
                }
                return false;
            }
        });

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
                swipe.setEnabled(topRowVerticalPosition >= 0);
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                //大于0表示，正在向右滚动
//小于等于0 表示停止或向左滚动
                isSlidingToLast = dy > 0;
            }
        });
        loadData();
    }

    public void loadData() {
        swipe.setRefreshing(true);
        homeService.getOrgs(uid, pageNumber, pageSize, typeId, companyname,
                new DefaultResponseListener<BaseResponse<List<YyMobileCompany>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<YyMobileCompany>> response) {
                        swipe.setRefreshing(false);
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
                            ToastUtil.show(OrgListActivity.this, response.getMessage());
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {
                        swipe.setRefreshing(false);
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
