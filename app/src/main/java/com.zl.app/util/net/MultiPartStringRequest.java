/**
 * Copyright 2013 Mani Selvaraj
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zl.app.util.net;

import android.util.Log;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.reflect.TypeToken;

/**
 * MultipartRequest - To handle the large file uploads.
 * Extended from JSONRequest. You might want to change to StringRequest based on your response type.
 * @author Mani Selvaraj
 *
 */
public class MultiPartStringRequest<T> extends Request<T> implements MultiPartRequest{

	private final Listener<T> mListener;
	/* To hold the parameter name and the File to upload */
	private Map<String,File> fileUploads = new HashMap<String,File>();
	
	/* To hold the parameter name and the string content to upload */
	private Map<String,String> stringUploads = new HashMap<String,String>();
	
    /**
     * Creates a new request with the given method.
     *
     * @param method the request {@link Method} to use
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public MultiPartStringRequest(int method, String url,Listener<T> listener,
            ErrorListener errorListener) {
        super(method,url,errorListener);
        mListener = listener;
        setRetryPolicy(new DefaultRetryPolicy(10000,
                5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    public void addFileUpload(String param,File file) {
    	fileUploads.put(param,file);
    }
    
    public void addStringUpload(String param,String content) {
    	stringUploads.put(param,content);
    }
    
    /**
     * 要上传的文件
     */
    public Map<String,File> getFileUploads() {
    	return fileUploads;
    }
    
    /**
     * 要上传的参数
     */
    public Map<String,String> getStringUploads() {
    	return stringUploads;
    }
    

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        Log.e("MultiPartStringRequest",parsed);
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

	@Override
	protected void deliverResponse(T response) {
		if(mListener != null) {
			mListener.onResponse(response);
		}
	}
	
	/**
	 * 空表示不上传
	 */
    public String getBodyContentType() {
        return null;
    }
}