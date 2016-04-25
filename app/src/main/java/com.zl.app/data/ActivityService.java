package com.zl.app.data;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;
import com.zl.app.util.AppManager;
import com.zl.app.util.RequestURL;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.util.net.GsonRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fxa on 2016/4/26.
 */
public class ActivityService {

    //发布活动
    public void sendActivity(String uid, String hdDateString, String headline, String address, int ispublic,DefaultResponseListener<BaseResponse> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("hdDateString", hdDateString);
        params.put("headline", headline);
        params.put("address", address);
        params.put("ispublic", ispublic+"");
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_SEND_ACTIVITY, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }
}
