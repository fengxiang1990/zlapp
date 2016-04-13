package com.zl.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zl.app.BaseFragment;
import com.zl.app.R;
import com.zl.app.data.news.model.YyMobileBase;
import com.zl.app.data.news.model.YyMobileNews;
import com.zl.app.fragment.FragmentHome;

import java.util.List;

/**
 * Created by fengxiang on 2016/2/17.
 */
public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder> {

    List<YyMobileBase> data;
    BaseFragment fragment;

    public TypeAdapter(BaseFragment fragment,List<YyMobileBase> data) {
        this.fragment = fragment;
        this.data = data;
    }

    @Override
    public TypeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_type1, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(TypeAdapter.ViewHolder holder, int position) {
        final YyMobileBase yyMobileBase = data.get(position);
        holder.mTextView.setText(yyMobileBase.getName());
        holder.mTextView.setClickable(true);
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(fragment instanceof FragmentHome){
                   ((FragmentHome)fragment).loadType(yyMobileBase.getValue());
               }
            }
        });
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
        public TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.text_news_type_name);
        }
    }
}
