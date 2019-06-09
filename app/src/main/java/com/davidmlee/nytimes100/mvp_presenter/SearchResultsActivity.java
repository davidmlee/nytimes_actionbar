package com.davidmlee.nytimes100.mvp_presenter;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import com.davidmlee.nytimes100.R;

public class SearchResultsActivity extends AppCompatActivity {
    private static String TAG;
    private static int objectcount = 0;
    int iDebug = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = "SearchResultsActivity-" + objectcount++;
        Log.v(TAG, "onCreate in");
        setContentView(R.layout.activity_result);
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
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            iDebug = 2;
            //use the query to search
            Log.v(TAG, query);
        }
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
