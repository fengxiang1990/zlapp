package com.zl.app.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.zl.app.data.user.model.YyMobileUser;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.RequestURL;
import com.zl.app.util.StringUtil;
import com.zl.app.util.ToastUtil;
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

    String tag = "YyMobileUser";

    @ViewById(R.id.img_user_header)
    SimpleDraweeView img_user_header;

    @ViewById(R.id.recyclerView)
    RecyclerView recyclerView;

    @ViewById(R.id.ll5)
    LinearLayout linearLayoutEdit;//回复内容面板

    @ViewById(R.id.edit_comment)
    EditText edit_comment;//回复内容面板

    @ViewById(R.id.llyinyong)
    LinearLayout  llyinyong; //引用内容面板

    @ViewById(R.id.ll4)
    LinearLayout ll4;

    @ViewById(R.id.text_yy_username)
    TextView  text_yy_username; //引用用户名

    @ViewById(R.id.text_yy_content)
    TextView  text_yy_conteng; //引用内容

    @ViewById(R.id.btn_cancel_yiyiong)
    TextView  btn_cancel_yiyiong; //取消引用

    @ViewById(R.id.text_qianming)
    TextView  text_qianming;

    @ViewById(R.id.text_gz_count)
    TextView  text_gz_count; //关注数

    @ViewById(R.id.text_user_name)
    TextView  text_user_name; //用户名

    @ViewById(R.id.text_funs_count)
    TextView  text_funs_count; //粉丝数

    @ViewById(R.id.text_topic_count)
    TextView  text_topic_count; //文章数

    @ViewById(R.id.text_read_count)
    TextView  text_read_count; //阅读数

    @ViewById(R.id.text_comment_count)
    TextView  text_comment_count; //评论数

    @ViewById(R.id.text_sc_count)
    TextView  text_sc_count; //收藏数

    @ViewById(R.id.text_dz_count)
    TextView  text_dz_count; //点赞数

    @ViewById(R.id.text_activities_count)
    TextView  text_activities_count; //活动数



    LinearLayoutManager layoutManager;

    List<YyMobileUserComment> comments;

    SiteMessageAdapter adapter;

    SiteService siteService;

    public int pageNo = 1;
    public int pageSize = 100000;
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
        recyclerView.setAdapter(adapter);
        uid = AppConfig.getUid(AppManager.getPreferences());
        userId = AppConfig.getUserId(AppManager.getPreferences());
        String url = AppConfig.getUserHeadImg(AppManager.getPreferences());
        Uri uri = Uri.parse(RequestURL.SERVER + url);
        img_user_header.setImageURI(uri);
        loadData();
    }

    public void loadData(){
        LoadingDialog.getInstance(getActivity()).show();
        loadUserInfo();
        loadUserComments();
    }
    public void loadUserInfo(){
        siteService.getUserInfo(uid, String.valueOf(userId), new DefaultResponseListener<BaseResponse<YyMobileUser>>() {
            @Override
            public void onSuccess(BaseResponse<YyMobileUser> response) {
                YyMobileUser user = response.getResult();
                if(user!= null){
                    text_user_name.setText(user.getNickName());
                    if(!StringUtil.isEmpty(user.getIntroduce())){
                        text_qianming.setText(user.getIntroduce());
                    }
                    text_gz_count.setText(String.valueOf(user.getGzgr()+user.getGzjg()));
                    text_topic_count.setText(String.valueOf(user.getFbno()));
                    text_read_count.setText(String.valueOf(user.getLrno()));
                    text_comment_count.setText(String.valueOf(user.getPlno()));
                    text_sc_count.setText(String.valueOf(user.getScno()));
                    text_dz_count.setText(String.valueOf(user.getDzno()));
                    //活动数量 粉丝数量缺少
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    public void loadUserComments(){
        siteService.getCommentList(uid, String.valueOf(userId), pageNo, pageSize, new DefaultResponseListener<BaseResponse<List<YyMobileUserComment>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileUserComment>> response) {
                LoadingDialog.getInstance(getActivity()).dismiss();
                if (!isLoadMore) {
                    comments.clear();
                }
                if (response.getResult() != null) {
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

    @Click(R.id.btn_send)
    void send(){
        String yyuserId=null;
        String yycontent=null;
        String yydate=null;
        if(llyinyong.getVisibility() == View.VISIBLE && yyMobileUserComment!=null){
            yyuserId = String.valueOf(yyMobileUserComment.getUserId());
            yycontent = yyMobileUserComment.getContent();
            yydate = yyMobileUserComment.getCreateDate();
        }
        String content = String.valueOf(edit_comment.getText());
        LoadingDialog.getInstance(getActivity()).show();
        siteService.reply(uid, String.valueOf(userId), yyuserId, yycontent, yydate, content, new DefaultResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                LoadingDialog.getInstance(getActivity()).dismiss();
                ToastUtil.show(getActivity(), response.getMessage());
                ll4.setVisibility(View.VISIBLE);
                linearLayoutEdit.setVisibility(View.GONE);
                edit_comment.setText("");
                loadUserComments();
            }

            @Override
            public void onError(VolleyError error) {
                LoadingDialog.getInstance(getActivity()).dismiss();
            }
        });
    }

    @Click(R.id.btn_reply)
    void openReply(){
        ll4.setVisibility(View.GONE);
        linearLayoutEdit.setVisibility(View.VISIBLE);
    }

    @Click(R.id.btn_close)
    void close(){
        edit_comment.setText("");
        ll4.setVisibility(View.VISIBLE);
        linearLayoutEdit.setVisibility(View.GONE);
        llyinyong.setVisibility(View.GONE);
        yyMobileUserComment = null;
    }

    @Click(R.id.btn_cancel_yiyiong)
    void cancelYinyong(){
        llyinyong.setVisibility(View.GONE);
        yyMobileUserComment = null;
    }

    YyMobileUserComment yyMobileUserComment = null;
    //打开引用面板
    public void openYyPanel(YyMobileUserComment yyMobileUserComment){
        ll4.setVisibility(View.GONE);
        linearLayoutEdit.setVisibility(View.VISIBLE);
        llyinyong.setVisibility(View.VISIBLE);
        this.yyMobileUserComment = yyMobileUserComment;
        text_yy_username.setText(yyMobileUserComment.getFormusername());
        text_yy_conteng.setText(yyMobileUserComment.getContent());
    }
}
