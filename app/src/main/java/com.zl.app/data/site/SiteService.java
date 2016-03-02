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

}
