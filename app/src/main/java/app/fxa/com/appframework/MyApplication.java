package app.fxa.com.appframework;

import android.app.Application;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;

import app.fxa.com.appframework.util.AppManager;
import app.fxa.com.appframework.util.BitmapLruCache;


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
