package com.zl.app.activity.mine;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by CQ on 2016/5/11 0011.
 */
public class AdviceImage {
    private Uri uri;
    private Bitmap bitmap;
    private int resourceId;

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getResourceId() {
        return resourceId;
    }
}
