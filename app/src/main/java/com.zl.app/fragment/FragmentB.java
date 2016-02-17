package com.zl.app.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.VolleyError;
import com.zl.app.BaseFragment;
import com.zl.app.R;
import com.zl.app.adapter.NewsAdapter;
import com.zl.app.adapter.TypeAdapter;
import com.zl.app.adapter.TypeAdapter2;
import com.zl.app.data.news.NewsService;
import com.zl.app.data.news.NewsServiceImpl;
import com.zl.app.data.news.model.YyMobileBase;
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
public class FragmentB extends BaseFragment {

    String TAG = "FragmentB";

    @ViewById(R.id.recyclerView1)
    RecyclerView recyclerView1;

    @ViewById(R.id.recyclerView2)
    RecyclerView recyclerView2;

    @ViewById(R.id.recyclerViewNews)
    RecyclerView recyclerViewNews;

    NewsService newsService;

    public int pageNo = 1;
    public int pageSize = 20;

    String uid;
    LinearLayoutManager layoutManager1;
    LinearLayoutManager layoutManager2;
    LinearLayoutManager layoutManagerNews;

    List<YyMobileBase> type_list1;
    List<YyMobileBase> type_list2;
    TypeAdapter typeAdapter1;
    TypeAdapter2 typeAdapter2;

    List<YyMobileNews> data;
    NewsAdapter newsAdapter;

    @AfterViews
    void afterViews(){
        //一级分类
        layoutManager1 = new LinearLayoutManager(getActivity());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView1.setLayoutManager(layoutManager1);
        type_list1 = new ArrayList<YyMobileBase>();
        typeAdapter1 = new TypeAdapter(this,type_list1);
        recyclerView1.setAdapter(typeAdapter1);

        //二级分类
        layoutManager2 = new LinearLayoutManager(getActivity());
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setLayoutManager(layoutManager2);
        type_list2 = new ArrayList<YyMobileBase>();
        typeAdapter2 = new TypeAdapter2(this,type_list2);
        recyclerView2.setAdapter(typeAdapter2);

        //数据
        layoutManagerNews = new LinearLayoutManager(getActivity());
        layoutManagerNews.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewNews.setLayoutManager(layoutManagerNews);
        data = new ArrayList<YyMobileNews>();
        newsAdapter = new NewsAdapter(data);
        recyclerViewNews.setAdapter(newsAdapter);

        newsService = new NewsServiceImpl();
        uid = AppConfig.getUid(AppManager.getPreferences());
        Log.e(TAG,"uid:"+uid);
        newsService.getNewsType(uid, null, new DefaultResponseListener<BaseResponse<List<YyMobileBase>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileBase>> response) {
                List<YyMobileBase> list = response.getResult();
                type_list1.addAll(list);
                typeAdapter1.notifyDataSetChanged();
                //默认加载第一个分类下的子分类
                YyMobileBase yyMobileBase = list.get(0);
                loadType(yyMobileBase.getValue());
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    /**
     * 查询上一级分类下的子类
     * @param code 上一级分类的value
     */
    public void loadType(String code){
        newsService.getNewsType(uid, code, new DefaultResponseListener<BaseResponse<List<YyMobileBase>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileBase>> response) {
                List<YyMobileBase> list = response.getResult();
                type_list2.clear();
                type_list2.addAll(list);
                typeAdapter2.notifyDataSetChanged();

                //默认加载第一个小分类下的新闻
                YyMobileBase yyMobileBase = list.get(0);
                loadData(pageNo,pageSize,yyMobileBase.getCode(),yyMobileBase.getValue());
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    public void loadData(int pageNo,int pageSize,String code,String value){
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
