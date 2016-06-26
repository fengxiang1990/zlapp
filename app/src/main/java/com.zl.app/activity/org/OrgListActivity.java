package com.zl.app.activity.org;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.zl.app.R;
import com.zl.app.adapter.OrgAdapter;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.home.HomeService;
import com.zl.app.data.home.HomeServiceImpl;
import com.zl.app.data.home.model.OrgType;
import com.zl.app.data.model.customer.YyMobileCompany;
import com.zl.app.data.model.user.YyMobileCity;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.GsonUtil;
import com.zl.app.util.ToastUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.view.LoadingDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.SystemService;
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

    @ViewById(R.id.edit_search)
    EditText searchTitleView;

    @ViewById(R.id.searchView)
    View searchView;

    @ViewById(R.id.titleView)
    TextView titleView;

    @ViewById(R.id.text_area)
    TextView text_area;

    @ViewById(R.id.text_class)
    TextView text_class;

    @ViewById
    ImageView leftBtn1;

    @ViewById
    ImageView rightBtn1;

    @ViewById
    LinearLayout ll_selection;

    @SystemService
    WindowManager windowManager;

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

    Receiver receiver;

    Receiver2 receiver2;

    @AfterViews
    void afterviews() {
        searchView.setVisibility(View.VISIBLE);
        rightBtn1.setImageResource(R.mipmap.find_select_icon);
        titleView.setVisibility(View.GONE);
        searchTitleView.setVisibility(View.VISIBLE);
        leftBtn1.setVisibility(View.VISIBLE);
        rightBtn1.setVisibility(View.VISIBLE);
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

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNumber = 1;
                isLoadMore = false;
                companyname = searchTitleView.getText().toString();
                loadData();
            }
        });

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

        receiver = new Receiver();
        registerReceiver(receiver, new IntentFilter(CitySelectActivity.addressSelectionBroadcast));

        receiver2 = new Receiver2();
        registerReceiver(receiver2, new IntentFilter(ClassSelectActivity.classSelectionBroadcast));


    }

    @Click(R.id.leftBtn1)
    void leftBtnClick() {
        finish();
    }

    @Click(R.id.rightBtn1)
    void rightBtn1Click() {
        ll_selection.setVisibility(View.VISIBLE);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        TranslateAnimation animation = new TranslateAnimation(screenWidth, 0, 0, 0);
        animation.setDuration(300);
        ll_selection.startAnimation(animation);

    }

    @Click(R.id.btn_confrim)
    void btnConfirmClick() {
        ll_selection.setVisibility(View.GONE);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        TranslateAnimation animation = new TranslateAnimation(0, screenWidth, 0, 0);
        animation.setDuration(300);
        ll_selection.startAnimation(animation);
        isLoadMore = false;
        companyname = searchTitleView.getText().toString();
        loadData();
    }

    @Click(R.id.btn_cancel)
    void btnCancelClick() {
        clearSelections();
        ll_selection.setVisibility(View.GONE);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        TranslateAnimation animation = new TranslateAnimation(0, screenWidth, 0, 0);
        animation.setDuration(300);
        ll_selection.startAnimation(animation);
    }

    @Click(R.id.text_area)
    void text_areaClick() {
        Intent intent = new Intent(OrgListActivity.this, CitySelectActivity.class);
        startActivity(intent);
    }

    @Click(R.id.text_class)
    void text_classClick() {
        Intent intent = new Intent(OrgListActivity.this, ClassSelectActivity.class);
        startActivity(intent);
    }

    public void loadData() {
        //swipe.setRefreshing(true);
        LoadingDialog.getInstance(OrgListActivity.this).show();
        homeService.getOrgs(uid, pageNumber, pageSize, typeId, companyname,
                province_cityId, city_cityId, district_cityId, street_cityId, orderName,
                new DefaultResponseListener<BaseResponse<List<YyMobileCompany>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<YyMobileCompany>> response) {
                        swipe.setRefreshing(false);
                        LoadingDialog.getInstance(OrgListActivity.this).dismiss();
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
                        //swipe.setRefreshing(false);
                        LoadingDialog.getInstance(OrgListActivity.this).dismiss();
                    }
                });
    }

    @Override
    public void onRefresh() {
        isLoadMore = false;
        pageNumber = 1;
        clearSelections();
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        if (receiver2 != null) {
            unregisterReceiver(receiver2);
        }
    }


    @Click(R.id.rb1)
    void rb1Click() {
        //全部
        orderName = null;
    }

    @Click(R.id.rb2)
    void rb2Click() {
        orderName = "grade";
    }

    @Click(R.id.rb3)
    void rb3Click() {
        orderName = "dimensions";
    }

    void clearSelections() {
        province_cityId = 0;
        city_cityId = 0;
        district_cityId = 0;
        street_cityId = 0;
        typeId = 0;
        text_class.setText("全部");
        text_area.setText("选择街道地址");
        orderName = null;
    }

    String orderName = null;
    int province_cityId = 0;//省
    int city_cityId = 0; //市
    int district_cityId = 0;//区县
    int street_cityId = 0;//街道

    class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String city_address = intent.getExtras().getString("city_address");
            if (!TextUtils.isEmpty(city_address)) {
                List<YyMobileCity> cities = GsonUtil.getJsonObject(city_address, new TypeToken<List<YyMobileCity>>() {
                });
                if (cities != null && cities.size() == 4) {
                    YyMobileCity level1 = cities.get(0);
                    YyMobileCity level2 = cities.get(1);
                    YyMobileCity level3 = cities.get(2);
                    YyMobileCity level4 = cities.get(3);
                    String address = level1.getName() + level2.getName() + level3.getName() + level4.getName();
                    text_area.setText(address);
                    province_cityId = level1.getCityId();
                    city_cityId = level2.getCityId();
                    district_cityId = level3.getCityId();
                    street_cityId = level4.getCityId();
                }
                if (cities != null && cities.size() == 1) {
                    YyMobileCity level1 = cities.get(0);
                    String address = level1.getName();
                    text_area.setText(address);
                    province_cityId = level1.getCityId();
                }
                if (cities != null && cities.size() == 2) {
                    YyMobileCity level1 = cities.get(0);
                    YyMobileCity level2 = cities.get(1);

                    String address = level1.getName() + level2.getName();
                    text_area.setText(address);
                    province_cityId = level1.getCityId();
                    city_cityId = level2.getCityId();
                }
                if (cities != null && cities.size() == 3) {
                    YyMobileCity level1 = cities.get(0);
                    YyMobileCity level2 = cities.get(1);
                    YyMobileCity level3 = cities.get(2);
                    String address = level1.getName() + level2.getName() + level3.getName();
                    text_area.setText(address);
                    province_cityId = level1.getCityId();
                    city_cityId = level2.getCityId();
                    district_cityId = level3.getCityId();
                }
            }
        }

    }

    class Receiver2 extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String org_type = intent.getExtras().getString("org_type");
            if (!TextUtils.isEmpty(org_type)) {
                OrgType orgType = GsonUtil.getJsonObject(org_type, OrgType.class);
                if (orgType != null) {
                    text_class.setText(orgType.getName());
                    typeId = orgType.getTypeId();
                }
            }
        }

    }
}
