package com.zl.app.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.zl.app.BaseFragment;
import com.zl.app.R;
import com.zl.app.adapter.NewsAdapter;
import com.zl.app.data.home.HomeService;
import com.zl.app.data.home.HomeServiceImpl;
import com.zl.app.data.home.model.YyMobileAdvt;
import com.zl.app.data.news.model.YyMobileNews;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.RequestURL;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 2015/12/28.
 */

@EFragment(R.layout.fragment_a)
public class FragmentA extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private final String TAG = "FragmentA";

    @ViewById(R.id.recyclerView)
    RecyclerView recyclerView;

    @ViewById(R.id.id_swipe_ly)
    SwipeRefreshLayout swipeRefreshLayout;

    LinearLayoutManager mlinearLayoutManager;
    List<YyMobileNews> newsList;
    NewsAdapter newsAdapter;
    ConvenientBanner convenientBanner;
    public int pageNo = 1;
    public int pageSize = 10;

    HomeService homeService;

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


}
