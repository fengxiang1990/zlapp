package app.fxa.com.appframework;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import app.fxa.com.appframework.util.AppConfig;
import app.fxa.com.appframework.util.AppManager;


public class BaseActivity extends FragmentActivity {

    protected SharedPreferences preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppManager.activities.add(this);
        super.onCreate(savedInstanceState);
        preference = getSharedPreferences(AppConfig.PREFERENCE_NAME, Context.MODE_PRIVATE);
        AppManager.setPreferences(preference);
    }

}
