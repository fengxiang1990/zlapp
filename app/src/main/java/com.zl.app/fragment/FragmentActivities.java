package com.zl.app.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.VolleyError;
import com.zl.app.BaseFragment;
import com.zl.app.R;
import com.zl.app.adapter.NewsAdapter;
import com.zl.app.data.news.NewsService;
import com.zl.app.data.news.NewsServiceImpl;
import com.zl.app.data.news.model.YyMobileNews;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
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

@EFragment(R.layout.fragment_b)
public class FragmentActivities extends BaseFragment {

    String TAG = "FragmentB";

    @ViewById(R.id.recyclerViewNews)
    RecyclerView recyclerViewNews;

    NewsService newsService;

    public int pageNo = 1;
    public int pageSize = 20;

    String uid;

    LinearLayoutManager layoutManagerNews;

    List<YyMobileNews> data;
    NewsAdapter newsAdapter;

    @AfterViews
    void afterViews() {

        //数据
        layoutManagerNews = new LinearLayoutManager(getActivity());
        layoutManagerNews.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewNews.setLayoutManager(layoutManagerNews);
        data = new ArrayList<YyMobileNews>();
        newsAdapter = new NewsAdapter(this, data);
        recyclerViewNews.setAdapter(newsAdapter);

        newsService = new NewsServiceImpl();
        uid = AppConfig.getUid(AppManager.getPreferences());
        Log.e(TAG, "uid:" + uid);
        //loadData(pageNo,pageSize,yyMobileBase.getCode(),yyMobileBase.getValue());
    }


    public void loadData(int pageNo, int pageSize, String code, String value) {
        newsService.getNewsByType(uid, pageNo, pageSize, code, value, new DefaultResponseListener<BaseResponse<List<YyMobileNews>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileNews>> response) {
                List<YyMobileNews> list = response.getResult();
                data.clear();
                data.addAll(list);
                newsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }
}
