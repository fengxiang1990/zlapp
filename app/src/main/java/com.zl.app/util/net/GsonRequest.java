/**
 * Copyright 2013 Mani Selvaraj
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zl.app.util.net;

import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.zl.app.activity.user.LoginActivity_;
import com.zl.app.util.AppManager;
import com.zl.app.util.StringUtil;

import java.io.UnsupportedEncodingException;
import java.util.Map;


/**
 * Custom implementation of Request<T> class which converts the HttpResponse
 * obtained to Java class objects. Uses GSON library, to parse the response
 * obtained. Ref - JsonRequest<T>
 *
 * @author Mani Selvaraj
 */

public class GsonRequest<T> extends Request<T> {
    private static final String TAG = "GsonRequest";
    /**
     * Charset for request.
     */
    private static final String PROTOCOL_CHARSET = "UTF-8";

    /**
     * Content type for request.
     */
    private static final String PROTOCOL_CONTENT_TYPE = String.format("application/x-www-form-urlencoded; charset=%s", PROTOCOL_CHARSET);

    private final Listener<T> mListener;

    private Gson mGson;
    private TypeToken<T> mTypeToken;
    private Map<String, String> mParams;
    private Map<String, String> mHeaders;

    public GsonRequest(int method, String url, Map<String, String> params, Map<String, String> headers, TypeToken<T> typeToken, Listener<T> listener, ErrorListener errorListener) {
        super(method, url, errorListener);
        mGson = new Gson();
        mTypeToken = typeToken;
        mListener = listener;
        mParams = params;
        mHeaders = headers;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders == null ? super.getHeaders() : mHeaders;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String jsonString = null;
        try {
            jsonString = new String(response.data, PROTOCOL_CHARSET);
            T parsedGSON = mGson.fromJson(jsonString, mTypeToken.getType());
            return Response.success(parsedGSON, HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(new VolleyError(e));
        } finally {
//            Log.e(TAG, "uid:" + mParams.get("uid"));
//            Log.e(TAG, "url:" + getUrl());
//            Log.e(TAG, "the response is:" + StringUtil.replaceAllInvalid(jsonString));
            if (StringUtil.replaceAllInvalid(jsonString).contains("请先登录账号")) {
                Intent intent = new Intent(AppManager.context, LoginActivity_.class);
                AppManager.context.startActivity(intent);
            }
        }
    }

    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

}
