package com.davidmlee.nytimes100.mvp_presenter;

import android.app.Activity;
import android.provider.SyncStateContract;
import android.util.Log;
import android.widget.Toast;

import com.davidmlee.nytimes100.R;
import com.davidmlee.nytimes100.mvp_model.SearchResult;
import com.davidmlee.nytimes100.mvp_presenter.MainActivity;
import com.davidmlee.nytimes100.mvp_presenter.MyApp;
import com.davidmlee.nytimes100.mvp_presenter.ScreenMap;
import com.davidmlee.nytimes100.mvp_model.ListSummaryEntity;
import com.davidmlee.nytimes100.mvp_presenter.QueryResponseCallback;
import com.davidmlee.nytimes100.util.SearchArticles;

import static com.davidmlee.nytimes100.mvp_model.Contants.NUM_ARTICLES_PER_PAGE;

import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * class MainController
 *
 */
public class MainController {
    private final static String TAG = "MainController";
    private static WeakReference<Activity> weakReferenceMainActivity = null;
    static private SearchResult searchResult = new SearchResult();
    static private ArrayList<ListSummaryEntity> articleAry = new ArrayList<>(); // List for list adapter
    /**
     * @return articleAry - List for list adapter
     */
    static public ArrayList<ListSummaryEntity> getArticleList() {
        return articleAry;
    }

    /**
     * @param mainActivity1 list activity
     */
    static public void setMainActivity(Activity mainActivity1) {
        weakReferenceMainActivity = new WeakReference<>(mainActivity1);
    }

    /**
     * @return mainActivity list activity
     */
    static Activity getMainActivity() {
        return weakReferenceMainActivity.get();
    }
    /**
     * @param str_search_text - string to search
     */
    static public void searchArticles(final String str_search_text) {
        // New search
        MainController.articleAry.clear(); // Clear the data list for a new search
        MainController.searchResult.setTotalArticlesFetched(-1);
        MainController.searchResult.setTotalPagesFetched(-1);
        MainController.searchResult.setLastFetchedPageNum(-1);
        MainController.searchResult.setSearchText("");
        new Thread() {
            @Override
            public void run() {
                super.run();
                Log.v(TAG, "sendSearchByPage in");
                SearchArticles.sendSearchByPage(str_search_text, 0, new QueryResponseCallback() {
                    @Override
                    public void onSuccess(String responseBodyString) {
                        JSONObject jsonTop;
                        JSONObject response;
                        JSONArray results;
                        try {
                            jsonTop = new JSONObject(responseBodyString);
                            MainController.searchResult.setLastFetchedPageNum(0); // First page fetched
                            MainController.searchResult.setSearchText(str_search_text);
                            response = jsonTop.getJSONObject("response");
                            if (response != null) {
                                results = response.getJSONArray("docs");
                                int numEntries = results.length();
                                ListSummaryEntity fe;
                                for (int i = 0; i < numEntries; i++) {
                                    fe = ListSummaryEntity.populateFetchableResource(results.getJSONObject(i));
                                    MainController.articleAry.add(fe);
                                } // for
                                MainController.searchResult.setLastFetchedPage_NumArticlesInPage(numEntries);
                                MainController.searchResult.setTotalArticlesFetched(numEntries);
                                MainController.searchResult.setTotalPagesFetched(1);
                                if (weakReferenceMainActivity.get() != null) {
                                    ((SearchResultsActivity) weakReferenceMainActivity.get()).resetArticleList();
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response httpResponse, Exception ex) {
                        String errorString = "";
                        if (httpResponse != null) {
                            errorString = httpResponse.toString();
                        } else if (ex != null) {
                            errorString = ex.getLocalizedMessage();
                        }
                        if (weakReferenceMainActivity.get() != null && ! MyApp.getIsAppBackground()) {
                            Activity activity1 = ScreenMap.getCurrentResumedActivity();
                            if (activity1 != null &&
                                    activity1.getLocalClassName().contains(SearchResultsActivity.class.getSimpleName())) {
                                ((SearchResultsActivity)weakReferenceMainActivity.get()).displayQueryError(errorString);
                            }
                        }
                    }
                });
            }
        }.start();
    }
    /**
     */
    static public void searchArticlesNextPage() {
        if (searchResult.getLastFetchedPage_NumArticlesInPage() < NUM_ARTICLES_PER_PAGE) {
            if (weakReferenceMainActivity.get() != null) {
                ((SearchResultsActivity)weakReferenceMainActivity.get()).promptUser(MyApp.getStrRes(R.string.label_list_bottom_reached), Toast.LENGTH_SHORT);
            }
            return;
        }
        // Subsequent search
        new Thread() {
            @Override
            public void run() {
                super.run();
                int pageNum = MainController.searchResult.getLastFetchedPageNum() + 1;
                SearchArticles.sendSearchByPage(MainController.searchResult.getSearchText(), pageNum, new QueryResponseCallback() {
                    @Override
                    public void onSuccess(String responseBodyString) {
                        JSONObject jsonTop;
                        JSONArray results;
                        try {
                            jsonTop = new JSONObject(responseBodyString);
                            MainController.searchResult.setLastFetchedPageNum(searchResult.getLastFetchedPageNum() + 1);
                            if (jsonTop.has("results")) {
                                results = jsonTop.optJSONArray("results");
                                if (results != null && results.length() > 0) {
                                    int numEntries = results.length();
                                    ListSummaryEntity fe;
                                    for (int i = 0; i < numEntries; i++) {
                                        fe = ListSummaryEntity.populateFetchableResource(results.getJSONObject(i));
                                        MainController.articleAry.add(fe);
                                    } // for
                                    if (weakReferenceMainActivity.get() != null) {
                                        ((SearchResultsActivity) weakReferenceMainActivity.get()).appendToArticleList();
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response httpResponse, Exception ex) {
                        String errorString = "";
                        if (httpResponse != null) {
                            errorString = httpResponse.toString();
                        } else if (ex != null) {
                            errorString = ex.getLocalizedMessage();
                        }
                        if (weakReferenceMainActivity.get() != null && ! MyApp.getIsAppBackground()) {
                            Activity myMainActivity = ScreenMap.getCurrentResumedActivity();
                            if (myMainActivity != null &&
                                    myMainActivity.getLocalClassName().contains(SearchResultsActivity.class.getSimpleName())) {
                                ((SearchResultsActivity)weakReferenceMainActivity.get()).displayQueryError(errorString);
                            }
                        }
                    }
                });
            }
        }.start();
    }
}
