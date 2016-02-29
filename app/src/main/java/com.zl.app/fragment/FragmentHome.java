package com.zl.app.fragment;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.zl.app.BaseFragment;
import com.zl.app.R;
import com.zl.app.adapter.NewsAdapter;
import com.zl.app.adapter.TypeAdapter;
import com.zl.app.adapter.TypeAdapter2;
import com.zl.app.data.home.HomeService;
import com.zl.app.data.home.HomeServiceImpl;
import com.zl.app.data.home.model.YyMobileAdvt;
import com.zl.app.data.news.NewsService;
import com.zl.app.data.news.NewsServiceImpl;
import com.zl.app.data.news.model.YyMobileBase;
import com.zl.app.data.news.model.YyMobileNews;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.RequestURL;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.view.LoadingDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 2015/12/28.
 */

@EFragment(R.layout.fragment_a)
public class FragmentHome extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private final String TAG = "FragmentA";

    @ViewById(R.id.recyclerView)
    RecyclerView recyclerView;

    @ViewById(R.id.id_swipe_ly)
    SwipeRefreshLayout swipeRefreshLayout;

    @ViewById(R.id.left_menu)
    LinearLayout leftMenu;

    @ViewById(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @ViewById(R.id.recyclerView1)
    RecyclerView recyclerView1;

    @ViewById(R.id.recyclerView2)
    RecyclerView recyclerView2;

    LinearLayoutManager layoutManager1;
    LinearLayoutManager layoutManager2;

    List<YyMobileBase> type_list1;
    List<YyMobileBase> type_list2;

    TypeAdapter typeAdapter1;
    TypeAdapter2 typeAdapter2;


    LinearLayoutManager mlinearLayoutManager;
    List<YyMobileNews> newsList;
    NewsAdapter newsAdapter;
    ConvenientBanner convenientBanner;

    public int pageNo = 1;
    public int pageSize = 10;

    HomeService homeService;
    NewsService newsService;
    String uid;

    public boolean isLoadMore = false;

    @AfterViews
    void afterViews() {
        swipeRefreshLayout.setOnRefreshListener(this);
        convenientBanner = (ConvenientBanner) getView().findViewById(R.id.convenientBanner);
        mlinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mlinearLayoutManager);
        //设置每个item 高度固定
        recyclerView.setHasFixedSize(true);
        newsList = new ArrayList<YyMobileNews>();
        newsAdapter = new NewsAdapter(this, newsList);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                swipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        homeService = new HomeServiceImpl();
        loadData();
        initNewsMenus();
    }

    public void initNewsMenus() {
        uid = AppConfig.getUid(AppManager.getPreferences());
        newsService = new NewsServiceImpl();
        ;
        //一级分类
        layoutManager1 = new LinearLayoutManager(getActivity());
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView1.setLayoutManager(layoutManager1);
        type_list1 = new ArrayList<YyMobileBase>();
        typeAdapter1 = new TypeAdapter(this, type_list1);
        recyclerView1.setAdapter(typeAdapter1);

        //二级分类
        layoutManager2 = new LinearLayoutManager(getActivity());
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView2.setLayoutManager(layoutManager2);
        type_list2 = new ArrayList<YyMobileBase>();
        typeAdapter2 = new TypeAdapter2(this, type_list2);
        recyclerView2.setAdapter(typeAdapter2);
        LoadingDialog.getInstance(getActivity()).show();
        newsService.getNewsType(uid, null, new DefaultResponseListener<BaseResponse<List<YyMobileBase>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileBase>> response) {
                LoadingDialog.getInstance(getActivity()).dismiss();
                List<YyMobileBase> list = response.getResult();
                type_list1.addAll(list);
                typeAdapter1.notifyDataSetChanged();
                //默认加载第一个分类下的子分类
                YyMobileBase yyMobileBase = list.get(0);
                loadType(yyMobileBase.getValue());
            }

            @Override
            public void onError(VolleyError error) {
                LoadingDialog.getInstance(getActivity()).dismiss();
            }
        });
    }

    public void loadData() {
        homeService.getHomeAds(AppConfig.getUid(AppManager.getPreferences()), new DefaultResponseListener<BaseResponse<List<YyMobileAdvt>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileAdvt>> response) {
                Log.e(TAG, response.toString());
                List<YyMobileAdvt> ads = response.getResult();
                YyMobileAdvt ad = ads.get(0);
                ads.add(ad);
                convenientBanner.setPages(
                        new CBViewHolderCreator<LocalImageHolderView>() {
                            @Override
                            public LocalImageHolderView createHolder() {
                                return new LocalImageHolderView();
                            }
                        }, ads);
                if (ads.size() <= 1) {
                    convenientBanner.setManualPageable(false);
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
        loadNews();

    }

    public void loadNews() {
        if (isLoadMore) {
            swipeRefreshLayout.setRefreshing(true);
        }

        homeService.getHomeNews(AppConfig.getUid(AppManager.getPreferences()), pageNo, pageSize, new DefaultResponseListener<BaseResponse<List<YyMobileNews>>>() {

            @Override
            public void onSuccess(BaseResponse<List<YyMobileNews>> response) {
                swipeRefreshLayout.setRefreshing(false);
                Log.e(TAG, response.toString());
                List<YyMobileNews> news = response.getResult();
                if (!isLoadMore) {
                    newsList.clear();
                }
                newsList.addAll(news);
                newsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        isLoadMore = false;
        loadData();
    }


    public class LocalImageHolderView implements Holder<YyMobileAdvt> {
        private NetworkImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new NetworkImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, YyMobileAdvt ad) {
            Log.e(TAG, "position:" + position);
            String imgUrl = RequestURL.SERVER + ad.getPicPath();
            imageView.setImageUrl(imgUrl, AppManager.getImageLoader());
        }
    }

    /**
     * 查询上一级分类下的子类
     *
     * @param code 上一级分类的value
     */
    public void loadType(String code) {
        LoadingDialog.getInstance(getActivity()).show();
        newsService.getNewsType(uid, code, new DefaultResponseListener<BaseResponse<List<YyMobileBase>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileBase>> response) {
                LoadingDialog.getInstance(getActivity()).dismiss();
                List<YyMobileBase> list = response.getResult();
                type_list2.clear();
                type_list2.addAll(list);
                typeAdapter2.notifyDataSetChanged();

                //默认加载第一个小分类下的新闻
                YyMobileBase yyMobileBase = list.get(0);
                //loadData(pageNo, pageSize, yyMobileBase.getCode(), yyMobileBase.getValue());
            }

            @Override
            public void onError(VolleyError error) {
                LoadingDialog.getInstance(getActivity()).dismiss();
            }
        });
    }


    public void searchNews(int pageNo, int pageSize, String code, String value) {
        newsService.getNewsByType(uid, pageNo, pageSize, code, value, new DefaultResponseListener<BaseResponse<List<YyMobileNews>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileNews>> response) {
                List<YyMobileNews> list = response.getResult();
                newsList.clear();
                newsList.addAll(list);
                newsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }
}
