/**
 * (non-Javadoc)
 *
 */
package com.davidmlee.nytimes100.mvp_presenter;

import okhttp3.Response;

/**
 * HttpResponseCallback
 */
public interface QueryResponseCallback {
    /**
     * @param responseBodyString The response string
     */
    void onSuccess(String responseBodyString);

    /**
     * @param httpResponse - http Response object
     * @param ex The Exception
     */
    void onError(Response httpResponse, Exception ex);
}
