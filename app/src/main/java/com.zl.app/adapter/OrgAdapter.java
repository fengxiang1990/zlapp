package com.zl.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.R;
import com.zl.app.activity.org.OrgWeiSiteActivity;
import com.zl.app.data.model.customer.YyMobileCompany;
import com.zl.app.util.StringUtil;

import java.util.List;

/**
 * Created by fengxiang on 2016/2/17.
 */
public class OrgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<YyMobileCompany> data;
    Context context;

    public OrgAdapter(Context context, List<YyMobileCompany> data) {
        this.context = context;
        this.data = data;
    }

    public boolean isShowHeader = false;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 10) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_find_header, parent, false);
            return new ViewHolderHeader(view);
        } else if (viewType == 20) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_org, parent, false);
            return new ViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        if (position == 0 && isShowHeader) {
            return 10; //菜单
        }
        return 20; //机构
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        if (holder1 instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) holder1;
            final YyMobileCompany org = data.get(position);
            if (StringUtil.isEmpty(org.getPicPath())) {
                holder.img_org.setVisibility(View.GONE);
            } else {
                holder.img_org.setVisibility(View.VISIBLE);
                Uri uri = Uri.parse(org.getPicPath());
                holder.img_org.setImageURI(uri);
            }
            holder.text_org_name.setText(org.getCompanyname());
            holder.text_score.setText(org.getGrade() + "");
            holder.text_type.setText(org.getTypeName());
            holder.text_juli.setText(org.getDistance().contains("千米") ? org.getDistance().replace("千米", "km") : org.getDistance());
            holder.text_area1.setText(org.getAddress());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, OrgWeiSiteActivity.class);
                    intent.putExtra("companyId", org.getCompanyId() + "");
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text_org_name;
        public TextView text_score;
        public SimpleDraweeView img_org;
        public TextView text_juli;
        public TextView text_type;
        public TextView text_area1;
        public TextView text_area2;

        public ViewHolder(View view) {
            super(view);
            text_org_name = (TextView) view.findViewById(R.id.text_org_name);
            text_score = (TextView) view.findViewById(R.id.text_score);
            img_org = (SimpleDraweeView) view.findViewById(R.id.img_org);
            text_type = (TextView) view.findViewById(R.id.text_type);
            text_juli = (TextView) view.findViewById(R.id.text_juli);
            text_area1 = (TextView) view.findViewById(R.id.text_area1);
            text_area2 = (TextView) view.findViewById(R.id.text_area2);
        }
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    class ViewHolderHeader extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;
        public TextView textView4;
        public TextView textView5;
        public TextView textView6;
        public TextView textView7;
        public TextView textView8;

        public ViewHolderHeader(View view) {
            super(view);
            textView1 = (TextView) view.findViewById(R.id.textView1);
            textView2 = (TextView) view.findViewById(R.id.textView2);
            textView3 = (TextView) view.findViewById(R.id.textView3);
            textView4 = (TextView) view.findViewById(R.id.textView4);
            textView5 = (TextView) view.findViewById(R.id.textView5);
            textView6 = (TextView) view.findViewById(R.id.textView6);
            textView7 = (TextView) view.findViewById(R.id.textView7);
            textView8 = (TextView) view.findViewById(R.id.textView8);
            textView1.setOnClickListener(this);
            textView2.setOnClickListener(this);
            textView3.setOnClickListener(this);
            textView4.setOnClickListener(this);
            textView5.setOnClickListener(this);
            textView6.setOnClickListener(this);
            textView7.setOnClickListener(this);
            textView8.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onClick(v);
            }
        }
    }

    private OnHeaderMenuClickListener listener;

    public interface OnHeaderMenuClickListener {
        void onClick(View v);
    }

    public OnHeaderMenuClickListener getListener() {
        return listener;
    }

    public void setListener(OnHeaderMenuClickListener listener) {
        this.listener = listener;
    }
}
