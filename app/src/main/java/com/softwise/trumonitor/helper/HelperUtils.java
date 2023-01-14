package com.softwise.trumonitor.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.core.app.ActivityCompat;
import java.util.ArrayList;
import java.util.Collections;

public class HelperUtils {
    private static String LOG_TAG = "HelperLog";

    public static void updateTag(String str) {
        if (!TextUtils.isEmpty(str)) {
            LOG_TAG = str;
        }
    }

    public static void logError(String str) {
        Log.e(LOG_TAG, str);
    }

    public static void logDebug(String str) {
        Log.d(LOG_TAG, str);
    }

    public static void logVerbose(String str) {
        Log.v(LOG_TAG, str);
    }

    public static void logInfo(String str) {
        Log.i(LOG_TAG, str);
    }

    public static boolean hasPermissions(Context context, String... strArr) {
        if (Build.VERSION.SDK_INT < 23 || context == null || strArr == null) {
            return true;
        }
        for (String checkSelfPermission : strArr) {
            if (ActivityCompat.checkSelfPermission(context, checkSelfPermission) != 0) {
                return false;
            }
        }
        return true;
    }

    public static synchronized String[] getManifestPermissions(Activity activity) {
        String[] strArr;
        String[] strArr2;
        synchronized (HelperUtils.class) {
            PackageInfo packageInfo = null;
            ArrayList arrayList = new ArrayList(1);
            try {
                packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 4096);
            } catch (PackageManager.NameNotFoundException e) {
                logError(e.getMessage());
            }
            if (!(packageInfo == null || (strArr2 = packageInfo.requestedPermissions) == null)) {
                Collections.addAll(arrayList, strArr2);
            }
            strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
        }
        return strArr;
    }

    public static void hideKeyboard(View view, Context context) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyboard(View view, Context context) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(view, 2);
    }
}
