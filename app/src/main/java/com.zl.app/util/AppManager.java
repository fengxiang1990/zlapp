package com.zl.app.util;

import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.google.gson.Gson;
import com.zl.app.BaseActivity;
import com.zl.app.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class AppManager {

    public static List<BaseActivity> activities = new ArrayList<BaseActivity>();
    public static List<BaseFragment> fragments = new ArrayList<BaseFragment>();


    private static ImageLoader imageLoader;

    private static SharedPreferences preferences;

    private static RequestQueue requestQueue;

    private static Gson gson;

    public static SharedPreferences getPreferences() {
        return preferences;
    }

    public static void setPreferences(SharedPreferences preferences) {
        AppManager.preferences = preferences;
    }

    public static ImageLoader getImageLoader() {
        return imageLoader;
    }

    public static void setImageLoader(ImageLoader imageLoader) {
        AppManager.imageLoader = imageLoader;
    }


    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public static void setRequestQueue(RequestQueue requestQueue) {
        AppManager.requestQueue = requestQueue;
    }

    public static Gson getGson() {
        return gson;
    }

    public static void setGson(Gson gson) {
        AppManager.gson = gson;
    }

    public static void finishAll(){
        for (BaseActivity baseActivity : activities){
            if (!baseActivity.isFinishing()){
                baseActivity.finish();
            }
        }
    }

}
