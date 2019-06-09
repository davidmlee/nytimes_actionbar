package com.davidmlee.nytimes100.util;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import org.json.JSONObject;

import java.util.Locale;
import com.davidmlee.nytimes100.mvp_presenter.MyApp;

/**
 * (non-Javadoc)
 *
 */
public class Util {
    /**
     * @param obj jsonobject
     * @param key key in json object to look for
     * @param defValue The default value to return
     */
    public static String getString(JSONObject obj, String key, String defValue) {
        String res = defValue;
        if (obj != null && obj.has(key)) {
            try {
                Object val = asObject(obj.get(key));
                if (val != null && !val.toString().isEmpty()
                        && !val.toString().equalsIgnoreCase("null")) {
                    res = val.toString();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return res;
    }

    public static boolean getBool(JSONObject obj, String key) {
        boolean res = false;
        if (obj != null && obj.has(key)) {
            try {
                Object val = asObject(obj.get(key));
                if (val != null && !val.toString().isEmpty()
                        && !val.toString().equalsIgnoreCase("null")) {
                    res = Boolean.valueOf(val.toString().toLowerCase());
                }
            } catch (Exception ex) {
                MyApp.handleException("JSONException", ex, false);
            }
        }
        return res;
    }

    /**
     * @param obj The Object
     */
    private static Object asObject(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj == JSONObject.NULL) {
            return null;
        }
        return obj;
    }

    /**
     * @param activity The activity on which the soft keyboard is displayed
     */
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View focusedView = activity.getCurrentFocus();
        if (focusedView != null) { // If no view is focused, an NPE will be thrown.  So check to make sure here
            try {
                imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
            } catch (Exception ex) {
                Log.e("Util", "hideSoftInputFromWindow exception: " + ex.getLocalizedMessage());
            }
        }
    }
    /**
     * @param str Input string to check
     * @return true if null or empty
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty() || str.equalsIgnoreCase("null");
    }

    /**
     * @return string of formatted app version
     */
    @SuppressWarnings("deprecation")
    public static String getAppVersionString() {
        PackageInfo info;
        try {
            info = MyApp.getAppContext().getPackageManager().getPackageInfo(MyApp.getAppContext().getPackageName(), 0);
        } catch (Exception ex) {
            MyApp.handleException("updateVersionLabel", ex, true);
            return "";
        }
        int versionCode;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            versionCode = (int) info.getLongVersionCode(); // avoid huge version numbers and you will be ok
        } else {
            //noinspection deprecation
            versionCode = info.versionCode;
        }
        return String.format(Locale.getDefault(), "%s (%d)", info.versionName, versionCode);
    }
}
