package com.zl.app.activity.org;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.R;
import com.zl.app.base.BaseActivityWithToolBar;
import com.zl.app.data.home.HomeService;
import com.zl.app.data.home.HomeServiceImpl;
import com.zl.app.model.customer.YyMobileCompany;
import com.zl.app.model.customer.YyMobileCompanyGrade;
import com.zl.app.util.AppConfig;
import com.zl.app.util.DateUtil;
import com.zl.app.util.RequestURL;
import com.zl.app.util.StringUtil;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.view.LoadingDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.maxwin.view.XListView;

/**
 * Created by fengxiang on 2016/4/14.
 */
public class OrgWeiSiteActivity extends BaseActivityWithToolBar implements XListView.IXListViewListener, View.OnClickListener {

    SimpleDraweeView img_org;
    TextView text_name;
    TextView text_desc;
    TextView text_zixun;
    TextView text_score;
    TextView text_pl_count;
    RadioButton rb1;
    RadioButton rb2;
    RadioButton rb3;
    XListView listView;
    HomeService homeService;
    String uid;
    String companyId = "";
    List<YyMobileCompanyGrade> data;
    GradeCommentsAdapter adapter;
    Context context;
    RatingBar ratingBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_site);
        context = this;
        setBtnLeft1Enable(true);
        setTextRight1Enable(true);
        setTextRight1Val("评价");
        setTitle("机构");
        data = new ArrayList<YyMobileCompanyGrade>();
        adapter = new GradeCommentsAdapter(data);
        img_org = (SimpleDraweeView) findViewById(R.id.img_org);
        text_desc = (TextView) findViewById(R.id.text_desc);
        text_name = (TextView) findViewById(R.id.text_name);
        text_zixun = (TextView) findViewById(R.id.text_zixun);
        text_score = (TextView) findViewById(R.id.text_score);
        text_pl_count = (TextView) findViewById(R.id.text_pl_count);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
        rb3 = (RadioButton) findViewById(R.id.rb3);
        rb1.setOnClickListener(this);
        rb2.setOnClickListener(this);
        rb3.setOnClickListener(this);
        listView = (XListView) findViewById(R.id.listview);
        listView.setXListViewListener(this);
        listView.setAdapter(adapter);
        homeService = new HomeServiceImpl();
        uid = AppConfig.getUid(preference);
        companyId = getIntent().getStringExtra("companyId");
        homeService.getOrgSite(uid, companyId, new DefaultResponseListener<BaseResponse<YyMobileCompany>>() {
            @Override
            public void onSuccess(BaseResponse<YyMobileCompany> response) {
                if (response != null) {
                    YyMobileCompany yyMobileCompany = response.getResult();
                    if (yyMobileCompany != null) {
                        if (StringUtil.isEmpty(yyMobileCompany.getPicPath())) {
                            img_org.setVisibility(View.GONE);
                        } else {
                            img_org.setVisibility(View.VISIBLE);
                            Uri uri = Uri.parse(RequestURL.SERVER + yyMobileCompany.getPicPath());
                            img_org.setImageURI(uri);
                        }
                        text_desc.setText(yyMobileCompany.getZhaiyao());
                        text_score.setText(yyMobileCompany.getGrade() + "");
                        int hpno = yyMobileCompany.getHpno();
                        int cpno = yyMobileCompany.getCpno();
                        text_pl_count.setText((hpno + cpno) + "条评论");
                        text_name.setText(yyMobileCompany.getCompanyname());

                        double rating = ratingBar.getNumStars() * yyMobileCompany.getGrade() / 10;
                        ratingBar.setRating(Float.parseFloat(String.valueOf(rating)));
                    }
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
        loadGradeComments();
    }


    int pageNo = 1;
    int pageSize = 1000;
    int type = 0;//0 全部 1 差评 2 中评 3好评
    boolean isLaodMore = false;

    void loadGradeComments() {
        LoadingDialog.getInstance(context).show();
        homeService.getOrgsGradeComments(uid, companyId, type, pageNo, pageSize, new DefaultResponseListener<BaseResponse<List<YyMobileCompanyGrade>>>() {
            @Override
            public void onSuccess(BaseResponse<List<YyMobileCompanyGrade>> response) {
                LoadingDialog.getInstance(context).dismiss();
                listView.stopLoadMore();
                listView.stopRefresh();
                if (response != null) {
                    List<YyMobileCompanyGrade> list = response.getResult();
                    if (!isLaodMore) {
                        data.clear();
                    }
                    if (list != null) {
                        data.addAll(list);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(VolleyError error) {
                LoadingDialog.getInstance(context).dismiss();
            }
        });
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        isLaodMore = false;
        listView.setRefreshTime(DateUtil.DateToString(new Date(), DateUtil.DateStyle.YYYY_MM_DD_HH_MM));
        loadGradeComments();
    }

    @Override
    public void onLoadMore() {

    }

    class GradeCommentsAdapter extends BaseAdapter {

        List<YyMobileCompanyGrade> data;

        public GradeCommentsAdapter(List<YyMobileCompanyGrade> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_company_grade, null);
                holder = new ViewHolder();
                holder.draweeView = (SimpleDraweeView) convertView.findViewById(R.id.simpleDraweeView);
                holder.text_content = (TextView) convertView.findViewById(R.id.text_content);
                holder.text_time = (TextView) convertView.findViewById(R.id.text_time);
                holder.text_score = (TextView) convertView.findViewById(R.id.text_score);
                holder.text_user_name = (TextView) convertView.findViewById(R.id.text_user_name);
                holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            YyMobileCompanyGrade grade = data.get(position);
            if (grade != null) {
                if (StringUtil.isEmpty(grade.getPicPath())) {
                    holder.draweeView.setVisibility(View.GONE);
                } else {
                    holder.draweeView.setVisibility(View.VISIBLE);
                    Uri uri = Uri.parse(RequestURL.SERVER + grade.getPicPath());
                    holder.draweeView.setImageURI(uri);
                }
                holder.text_content.setText(grade.getContent());
                holder.text_score.setText(grade.getGrade() + "");
                holder.text_time.setText(grade.getCreateDate());
                holder.text_user_name.setText(grade.getUsername());
                int rating = holder.ratingBar.getNumStars() * grade.getGrade() / 10;
                holder.ratingBar.setRating(rating);
            }
            return convertView;
        }

    }

    class ViewHolder {
        RatingBar ratingBar;
        SimpleDraweeView draweeView;
        TextView text_content;
        TextView text_time;
        TextView text_user_name;
        TextView text_score;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rb1:
                type = 0;//全部
                loadGradeComments();
                break;
            case R.id.rb2:
                type = 3;//好评
                loadGradeComments();
                break;
            case R.id.rb3:
                type = 1;//差评
                loadGradeComments();
                break;
        }
    }
}
