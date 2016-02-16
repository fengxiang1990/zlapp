package com.zl.app.data.home;

import com.zl.app.data.home.model.Ad;
import com.zl.app.data.home.model.News;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import java.util.List;

/**
 * Created by fengxiang on 2016/2/16.
 */
public interface HomeService {

    /**
     *
     * @param uid
     * @param listener
     */
    public void getHomeAds(String uid,DefaultResponseListener<BaseResponse<List<Ad>>> listener);

    /**
     *
     * @param uid
     * @param pageNo
     * @param pageSize
     * @param listener
     */
    public void getHomeNews(String uid,int pageNo,int pageSize,DefaultResponseListener<BaseResponse<List<News>>> listener);

}
