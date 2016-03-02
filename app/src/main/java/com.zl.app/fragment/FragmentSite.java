package com.zl.app.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.BaseFragment;
import com.zl.app.R;
import com.zl.app.activity.user.UserInfoActivity_;
import com.zl.app.adapter.SiteMessageAdapter;
import com.zl.app.data.news.model.YyMobileBase;
import com.zl.app.data.news.model.YyMobileUserComment;
import com.zl.app.data.site.SiteService;
import com.zl.app.data.site.SiteServiceImpl;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.RequestURL;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.view.LoadingDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/12/28.
 */

@EFragment(R.layout.fragment_site)
public class FragmentSite extends BaseFragment {


    @ViewById(R.id.img_user_header)
    SimpleDraweeView img_user_header;

    @ViewById(R.id.recyclerView)
    RecyclerView recyclerView;

    LinearLayoutManager layoutManager;

    List<YyMobileUserComment> comments;

    SiteMessageAdapter adapter;

    SiteService siteService;

    public int pageNo = 1;
    public int pageSize = 100;
    String uid;
    int userId;
    public boolean isLoadMore = false;

    @AfterViews
    void afterViews() {
        siteService = new SiteServiceImpl();
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        comments = new ArrayList<YyMobileUserComment>();
        adapter = new SiteMessageAdapter(this,comments);
        uid = AppConfig.getUid(AppManager.getPreferences());
        userId = AppConfig.getUserId(AppManager.getPreferences());
        String url = AppConfig.getUserHeadImg(AppManager.getPreferences());
        Uri uri = Uri.parse(RequestURL.SERVER + url);
        img_user_header.setImageURI(uri);
        loadUserComments();
    }

    public void loadUserComments(){
        LoadingDialog.getInstance(getActivity()).show();
        Log.e("uid:", uid);
        Log.e("userId:",String.valueOf(userId));
        siteService.getCommentList(uid, String.valueOf(userId), pageNo, pageSize, new DefaultResponseListener<BaseResponse<List<YyMobileUserComment>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileUserComment>> response) {
                LoadingDialog.getInstance(getActivity()).dismiss();
                if(!isLoadMore){
                    comments.clear();
                }
                if(response.getResult()!=null){
                    comments.addAll(response.getResult());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    @Click(R.id.img_user_header)
    void userHeaderClick() {
        Intent intent = new Intent(getActivity(), UserInfoActivity_.class);
        startActivity(intent);
    }


}
