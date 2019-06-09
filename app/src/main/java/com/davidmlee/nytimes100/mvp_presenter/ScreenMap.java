package com.davidmlee.nytimes100.mvp_presenter;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import java.lang.ref.WeakReference;

/**
 * class ScreenMap utility
 */
public class ScreenMap {
    //private static final String TAG = "ScreenMap";
    private static WeakReference<Activity> weakReferenceCurrentResumedActivity = null;
    /**
     * getCurrentResumedActivity
     *
     * @return currentResumedActivity
     */
    public static Activity getCurrentResumedActivity() {
        if (ScreenMap.weakReferenceCurrentResumedActivity == null) {
            return null;
        }
        return ScreenMap.weakReferenceCurrentResumedActivity.get();
    }
    /**
     * Get the ScreenMap started
     *
     */
    static void start(Application app) {
        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }
            @Override
            public void onActivityStarted(Activity activity) {
            }
            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }
            @Override
            public void onActivityResumed(Activity activity) {
                MyApp.setIsAppBackground(false);
                ScreenMap.weakReferenceCurrentResumedActivity = new WeakReference<>(activity);
            }
            @Override
            public void onActivityPaused(Activity activity) {
            }
            @Override
            public void onActivityStopped(Activity activity) {
            }
            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });

        IntentFilter screenStateFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        app.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                MyApp.setIsAppBackground(true);
            }
        }, screenStateFilter);
    }
}
