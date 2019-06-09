package com.davidmlee.nytimes100.util;

import com.davidmlee.nytimes100.mvp_model.Contants;
import com.davidmlee.nytimes100.util.HttpRequest;
import com.davidmlee.nytimes100.util.HttpResponseCallback;
import okhttp3.Response;

import static com.davidmlee.nytimes100.mvp_model.Contants.BASE_URL;
import com.davidmlee.nytimes100.mvp_presenter.*;

/**
 * (non-Javadoc)
 *
 */
public class SearchArticles {
    /**
     * @param searchStr The string to search
     * @param pageNum The page to get (0 based)
     * @param queryResponseCallback The callback object for return
     */
    public static void sendSearchByPage(String searchStr, int pageNum, final QueryResponseCallback queryResponseCallback) {
        String urlStr = BASE_URL + "articlesearch.json" + "?api-key=" + Contants.API_KEY + "&q=" + searchStr + "&sort=newest&page=" + pageNum;
        HttpRequest.sendResquest(urlStr, new HttpResponseCallback() {
            @Override
            public void onSuccess(Response response, String responseBodyString) {
                queryResponseCallback.onSuccess(responseBodyString);
            }

            @Override
            public void onError(Response response, Exception ex) {
                if (ex != null) {
                    ex.printStackTrace();
                }
                queryResponseCallback.onError(response, ex);
            }
        });
    }
}
