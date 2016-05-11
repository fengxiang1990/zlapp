package com.zl.app.util.adapter_util;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by CQ on 2016/5/11 0011.
 */
public class ViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;
    private int mPosition;

    public ViewHolder(Context context, int position, int layoutId, ViewGroup parent) {
        mPosition = position;
        mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
    }

    public static ViewHolder getViewHolder(Context context, int position,
                                           View convertView, int layoutId, ViewGroup parent){
        if (convertView == null){
            return new ViewHolder(context, position, layoutId, parent);
        }else {
            ViewHolder viewHolder = (ViewHolder)convertView.getTag();
            viewHolder.mPosition = position;
            return viewHolder;
        }
    }

    /**
     * 通过viewId获取容器中的控件
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if ( view == null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T)view;
    }

    public View getConvertView() {
        return mConvertView;
    }
}
