/**
 * HttpRequest.java
 */
package com.davidmlee.nytimes100.util;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * class HttpRequest
 */
public class HttpRequest {
	private static final String TAG = HttpRequest.class.getSimpleName();
	synchronized static public void sendResquest(String url, HttpResponseCallback httpResponseCallback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
		Request request = new Request.Builder()
				.url(url)
				.build();
		Log.v(TAG, "request=" + request.toString());
		Call call = client.newCall(request);
		Response response = null;
		String responseBodyStr;
		try {
			response = call.execute();
			if (response.isSuccessful()) {
                responseBodyStr = response.body().string();
				httpResponseCallback.onSuccess(response, responseBodyStr);
			} else {
				httpResponseCallback.onError(response, null);
			}
        } catch (Exception ex) {
            ex.printStackTrace();
            httpResponseCallback.onError(response, ex);
		}
	}
}
