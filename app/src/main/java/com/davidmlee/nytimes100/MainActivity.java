package com.davidmlee.nytimes100;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

public class MainActivity extends AppCompatActivity {
    private static String TAG;
    private static int objectcount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = "MainActivity-" + objectcount++;
        Log.v(TAG, "onCreate in");
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.v(TAG, "onCreateOptionsMenu in");
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
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
