package com.zl.app.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.zl.app.R;
import com.zl.app.activity.mine.AdviceImage;
import com.zl.app.util.adapter_util.CommonAdapter;
import com.zl.app.util.adapter_util.ViewHolder;

import java.util.List;

/**
 * Created by CQ on 2016/5/11 0011.
 */
public class AdviceImageAdapter extends CommonAdapter<AdviceImage>{

    public AdviceImageAdapter(Context context, int layoutId, List<AdviceImage> images) {
        super(context, layoutId, images);
    }

    @Override
    public void convert(ViewHolder viewHolder, AdviceImage image) {
        ImageView image1 = (ImageView) viewHolder.getView(R.id.image_item1);
        ImageView image2 = (ImageView) viewHolder.getView(R.id.image_item2);

        if ((image.getBitmap() != null) && (image.getResourceId() == 0)) {
            image1.setImageBitmap(image.getBitmap());
            image1.setVisibility(View.VISIBLE);
            image2.setVisibility(View.GONE);
        }else if ((image.getBitmap() == null) && (image.getResourceId() != 0)){
            image2.setImageResource(image.getResourceId());
            image2.setVisibility(View.VISIBLE);
            image1.setVisibility(View.GONE);
        }
    }
}
