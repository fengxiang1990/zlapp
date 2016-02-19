package com.zl.app.data.news;

import com.zl.app.data.news.model.YyMobileBase;
import com.zl.app.data.news.model.YyMobileNews;
import com.zl.app.data.news.model.YyMobileNewsComment;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import java.util.List;

/**
 * Created by fengxiang on 2016/2/17.
 */
public interface NewsService {

     public static String DEFAULT_CODE = "jiaoyu";

    /**
     *
     * @param uid
     * @param code
     * @param listener
     */
     public void getNewsType(String uid,String code,DefaultResponseListener<BaseResponse<List<YyMobileBase>>> listener);

    /**
     *
     * @param uid
     * @param pageNo
     * @param pageSize
     * @param code  大分类
     * @param value 小分类
     * @param listener
     */
    public void getNewsByType(String uid,int pageNo,int pageSize,String code,String value,DefaultResponseListener<BaseResponse<List<YyMobileNews>>> listener);

    /**
     *
     * @param uid
     * @param url  新闻唯一码标识新闻唯一码标识
     * @param listener
     */
    public void getNewsDetail(String uid,String url,DefaultResponseListener<BaseResponse<YyMobileNews>> listener);


    /**
     * 获取新闻评论列表
     * @param uid
     * @param url 新闻唯一码标识新闻唯一码标识
     * @param pageNo
     * @param pageSize
     * @param listener
     */
    public void getNewsComments(String uid,String url,int pageNo,int pageSize,DefaultResponseListener<BaseResponse<List<YyMobileNewsComment>>> listener);

}
