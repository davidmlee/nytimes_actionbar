package com.davidmlee.nytimes100.mvp_presenter;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.util.Log;

/**
 * class MyApp
 *    Application class
 */

public class MyApp extends Application {
    final static private String TAG = "App";
    public static boolean DEBUG = false;
    private static volatile Context appContext;
    // Backgrounded: 1. App starts out in background
    //               2. ACTION_SCREEN_OFF
    //               3. onTrimMemory with TRIM_MEMORY_UI_HIDDEN
    // Foregrounded  1. Any of my activities is resumed
    private static boolean isAppBackground = true;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        ScreenMap.start(this);
    }

    /**
     * setIsAppBackground
     *
     * @return appContext: Context of this application
     */
    public static Context getAppContext() {
        return appContext;
    }

    /**
     * setIsAppBackground
     *
     * @param backgrounded: True app backgrounded; False, otherwise
     */
    public static void setIsAppBackground(boolean backgrounded) {
        Log.v(TAG, "backgrounded=" + backgrounded);
        MyApp.isAppBackground = backgrounded;
    }

    /**
     * getIsAppBackground
     *
     * @return True if app in background; False, otherwise
     */
    public static boolean getIsAppBackground() {
        return MyApp.isAppBackground;
    }

    /**
     * callback onTrimMemory
     * @param level the level of memory trim
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) { // Not enough memory, UI already not visible
            MyApp.setIsAppBackground(true);
        }
    }

    /**
     * getStrRes
     * @param id String ID
     * @return string from the string file
     */
    public static String getStrRes(int id) {
        return appContext.getResources().getString(id);
    }

    /**
     * handleException
     * @param TAG an id for logging
     * @param ex Exception
     * @param throwIfDebug True to throw during DEBUG
     */
    public static void handleException(String TAG, Throwable ex,
                                       boolean throwIfDebug) {
        if (DEBUG) {
            // both may be useful
            Log.e(TAG,
                    ex.getMessage() != null ? ex.getMessage() : ex.toString());
            ex.printStackTrace();

            if (throwIfDebug) {
                throw new RuntimeException(ex);
            }
        }
    }
}
