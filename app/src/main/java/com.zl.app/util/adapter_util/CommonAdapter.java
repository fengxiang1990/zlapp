package com.zl.app.util.adapter_util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by CQ on 2016/5/11 0011.
 */
public abstract class CommonAdapter<T> extends BaseAdapter{

    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mLayoutInflater;
    protected int mLayoutId;

    public CommonAdapter(Context context, int layoutId, List<T> datas) {
        mContext = context;
        mDatas = datas;
        mLayoutInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder = ViewHolder.getViewHolder(mContext, position,
                convertView,  mLayoutId, parent);
        convert(viewHolder, getItem(position));

        return viewHolder.getConvertView();
    }

    public abstract void convert(ViewHolder viewHolder, T  t);
}
