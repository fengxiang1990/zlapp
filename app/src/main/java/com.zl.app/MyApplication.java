package com.zl.app;

import android.app.Application;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.BitmapLruCache;
import com.zl.app.util.net.MultiPartStack;

import org.androidannotations.annotations.EApplication;

import cn.jpush.android.api.JPushInterface;


@EApplication
public class MyApplication extends Application {

    public ImageLoader imageLoader;
    public RequestQueue requestQueue;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    String TAG = MyApplication.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("applicayion", "onCreate");
        requestQueue = Volley.newRequestQueue(this,new MultiPartStack());
        imageLoader = new ImageLoader(requestQueue, new BitmapLruCache());
        AppManager.setRequestQueue(requestQueue);
        AppManager.setImageLoader(imageLoader);
        Fresco.initialize(this);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initLocation();
        JPushInterface.init(this);
        String registrationId = JPushInterface.getRegistrationID(this);
        Log.e(TAG,"registrationId-->"+registrationId);
        AppConfig.JPUSH_ID = registrationId;
    }


    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 0;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            mLocationClient.stop();
            Log.i(TAG, "addr:" + location.getAddrStr());
            Log.i(TAG, "longitude:" + location.getLongitude());
            Log.i(TAG, "latitude:" + location.getLatitude());
        }
    }

}
