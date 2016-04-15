package com.zl.app.fragment;

import android.net.Uri;
import android.util.Log;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.BaseFragment;
import com.zl.app.R;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.RequestURL;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * 我的
 * Created by fengxiang on 2016/3/28.
 */
@EFragment(R.layout.fragment_mine)
public class FragmentMine extends BaseFragment{

    String tag = "FragmentMine";

    @ViewById(R.id.simpleDraweeView)
    SimpleDraweeView simpleDraweeView;

    @ViewById(R.id.text_name)
    TextView text_name;

    String uid;

    @AfterViews
    void afterViews() {
        uid = AppConfig.getUid(AppManager.getPreferences());
        String picPath = AppConfig.getUserHeadImg(AppManager.getPreferences());
        picPath = RequestURL.SERVER + picPath;
        Log.e(tag, picPath);
        Uri uri = Uri.parse(picPath);
        simpleDraweeView.setImageURI(uri);
        String username = AppManager.getPreferences().getString(AppConfig.USER_NAME, "");
        text_name.setText(username);
    }
}
