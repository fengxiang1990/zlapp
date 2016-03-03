package com.zl.app.data.site;

import com.zl.app.data.news.model.YyMobileUserComment;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;

import java.util.List;

/**
 * Created by admin on 2016/3/2.
 */
public interface SiteService {

    /**
     * 获取微站留言列表
     * @param uid
     * @param ids  查看用户的userId ？？
     * @param pageNo
     * @param pageSize
     * @param listener
     */
    public void getCommentList(String uid,String ids,int pageNo,int pageSize,DefaultResponseListener<BaseResponse<List<YyMobileUserComment>>> listener);


    /**
     * 回复
     * @param uid
     * @param ids         用户ID
     * @param yyuserId   引用人用户id
     * @param yycontent  引用内容
     * @param yydate     引用的内容发布日期
     * @param content    评论内容
     * @param listener
     */
    public void reply(String uid,String ids,String yyuserId,String yycontent,String yydate,String content,DefaultResponseListener<BaseResponse> listener);
}
