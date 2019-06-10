package com.davidmlee.nytimes100.mvp_presenter;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.davidmlee.nytimes100.R;
import com.davidmlee.nytimes100.mvp_view.ArticleListAdapter;
import com.davidmlee.nytimes100.util.Util;

public class SearchResultsActivity extends AppCompatActivity {
    private static String TAG;
    private static int objectcount = 0;
    int iDebug = -1;
    RecyclerView recList;
    ArticleListAdapter articleListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = "SearchResultsActivity-" + objectcount++;
        Log.v(TAG, "onCreate in");
        setContentView(R.layout.activity_result);

        MainController.setMainActivity(this);
        recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        articleListAdapter = new ArticleListAdapter(SearchResultsActivity.this, MainController.getArticleList());
        recList.setAdapter(articleListAdapter);

        handleIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.v(TAG, "onNewIntent in");
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.v(TAG, "handleIntent query=" + query);
            iDebug = 2;
            // use the query to search
            Log.v(TAG, query);
            if (query.length() == 0) {
                Toast.makeText(SearchResultsActivity.this, R.string.label_search_text_empty, Toast.LENGTH_LONG).show();
            } else {
                clearMovieList();
                MainController.searchArticles(query);
            }

        }
    }

    public void clearMovieList() {
        MainController.getArticleList().clear(); // Clear the data list for a new search
        this.articleListAdapter.notifyDataSetChanged();
        recList.invalidate();
    }

    public void resetMovieList() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                articleListAdapter.notifyDataSetChanged();
                recList.invalidate();
                Activity currentlyResumed = ScreenMap.getCurrentResumedActivity();
                if (currentlyResumed != null && currentlyResumed.getLocalClassName().contains(MainActivity.class.getSimpleName())) {
                    Util.hideSoftKeyboard(currentlyResumed);
                    recList.scrollToPosition(0);
                    String strToDisplay;
                    if (articleListAdapter.getItemCount() == 0) {
                        strToDisplay = MyApp.getStrRes(R.string.label_article_not_found);
                    } else {
                        strToDisplay = MyApp.getStrRes(R.string.label_list_reset);
                    }
                    Snackbar.make(recList,
                            strToDisplay
                            , Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    public void appendToMovieList() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                articleListAdapter.notifyDataSetChanged();
                recList.invalidate();
                Activity currentlyResumed = ScreenMap.getCurrentResumedActivity();
                if (currentlyResumed != null && currentlyResumed.getLocalClassName().contains(MainActivity.class.getSimpleName())) {
                    Util.hideSoftKeyboard(currentlyResumed);
                    Snackbar.make(recList,
                            MyApp.getStrRes(R.string.label_list_appended)
                            , Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    public void promptUser(final String prompt_str, final int duration) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Activity currentlyResumed = ScreenMap.getCurrentResumedActivity();
                if (currentlyResumed != null && currentlyResumed.getLocalClassName().contains(MainActivity.class.getSimpleName())) {
                    Util.hideSoftKeyboard(currentlyResumed);
                    //Toast.makeText(MainActivity.this, prompt_str, Toast.LENGTH_LONG).show();
                    Snackbar.make(recList,
                            prompt_str
                            , duration)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    public void displayQueryError(final String errorString) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Activity currentlyResumed = ScreenMap.getCurrentResumedActivity();
                if (currentlyResumed != null && currentlyResumed.getLocalClassName().contains(MainActivity.class.getSimpleName())) {
                    Util.hideSoftKeyboard(currentlyResumed);
                    Toast.makeText(currentlyResumed, errorString, Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "onStart in");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onPause in");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onResume in");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "onStop in");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy in");
    }
}
