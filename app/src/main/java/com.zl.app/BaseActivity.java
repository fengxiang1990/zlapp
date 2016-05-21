package com.zl.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;

import cn.jpush.android.api.JPushInterface;


public class BaseActivity extends AppCompatActivity {

    protected SharedPreferences preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppManager.activities.add(this);
        super.onCreate(savedInstanceState);
        preference = getSharedPreferences(AppConfig.PREFERENCE_NAME, Context.MODE_PRIVATE);
        AppManager.setPreferences(preference);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.activities.remove(this);

    }

    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }
}
