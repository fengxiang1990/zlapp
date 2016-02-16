package com.zl.app.fragment;

import android.content.Context;
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
import com.zl.app.data.home.HomeService;
import com.zl.app.data.home.HomeServiceImpl;
import com.zl.app.data.home.model.Ad;
import com.zl.app.data.home.model.News;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.RequestURL;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;


/**
 * Created by admin on 2015/12/28.
 */

@EFragment(R.layout.fragment_a)
public class FragmentA extends BaseFragment {

    private final String TAG = "FragmentA";
    @ViewById(R.id.container)
    LinearLayout linearLayout;

    ConvenientBanner convenientBanner;
    List<View> views;
    int pageNo = 1;
    int pageSize = 10;

    HomeService homeService;

    @AfterViews
    void afterViews() {
        convenientBanner = (ConvenientBanner) getView().findViewById(R.id.convenientBanner);
        homeService = new HomeServiceImpl();
        homeService.getHomeAds(AppConfig.getUid(AppManager.getPreferences()), new DefaultResponseListener<BaseResponse<List<Ad>>>() {
            @Override
            public void onSuccess(BaseResponse<List<Ad>> response) {
                Log.e(TAG, response.toString());
                List<Ad> ads = response.getResult();
                Ad ad = ads.get(0);
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

        homeService.getHomeNews(AppConfig.getUid(AppManager.getPreferences()), pageNo, pageSize, new DefaultResponseListener<BaseResponse<List<News>>>() {

            @Override
            public void onSuccess(BaseResponse<List<News>> response) {
                Log.e(TAG, response.toString());
                List<News> news = response.getResult();
                for (News item : news) {
                    Log.e(TAG, item.toString());
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }


    public class LocalImageHolderView implements Holder<Ad> {
        private NetworkImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new NetworkImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, Ad ad) {
            Log.e(TAG,"position:"+position);
            String imgUrl = RequestURL.SERVER + ad.getPicPath();
            imageView.setImageUrl(imgUrl, AppManager.getImageLoader());
        }
    }

}
