package com.zl.app.fragment;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zl.app.BaseFragment;
import com.zl.app.R;
import com.zl.app.activity.mine.MyYuyueActivity;
import com.zl.app.util.AppConfig;
import com.zl.app.util.AppManager;
import com.zl.app.util.RequestURL;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * 我的
 * Created by fengxiang on 2016/3/28.
 */
@EFragment(R.layout.fragment_mine)
public class FragmentMine extends BaseFragment implements View.OnClickListener {

    String tag = "FragmentMine";

    @ViewById(R.id.simpleDraweeView)
    SimpleDraweeView simpleDraweeView;

    @ViewById(R.id.text_name)
    TextView text_name;

    @ViewById(R.id.text_yuyue)
    ImageView text_yuyue;


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
        text_yuyue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_yuyue:
                Intent intent = new Intent(getActivity(), MyYuyueActivity.class);
                startActivity(intent);
                break;
        }
    }
}
