package com.zl.app;

import android.app.Application;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.zl.app.util.AppManager;
import com.zl.app.util.BitmapLruCache;


public class MyApplication extends Application {

    public ImageLoader imageLoader;
    public RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("applicayion", "onCreate");
        requestQueue = Volley.newRequestQueue(this);
        imageLoader = new ImageLoader(requestQueue, new BitmapLruCache());
        AppManager.setRequestQueue(requestQueue);
        AppManager.setImageLoader(imageLoader);
        Fresco.initialize(this);

    }

}
