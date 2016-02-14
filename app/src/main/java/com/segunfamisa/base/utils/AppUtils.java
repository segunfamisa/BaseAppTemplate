package com.segunfamisa.base.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.inputmethod.InputMethodManager;

import com.segunfamisa.base.R;

/**
 * Util methods for activity componenets
 *
 * Created by Segun Famisa <segunfamisa@gmail.com> on 14/02/2016.
 */
public class AppUtils {

    /**
     * Utility method for retrieving the appversion
     *
     * @param context context
     * @return the app version name
     */
    public static String getAppVersion(Context context){
        try{
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionName;
        }
        catch (PackageManager.NameNotFoundException nnfe){
            nnfe.printStackTrace();
            return "";
        }
    }

    /**
     * utility method to determine if a device is a tablet
     * @param context
     * @return
     */
    public static boolean isTablet(Context context){
        if(context == null) return false;
        boolean isTablet = context.getResources().getBoolean(R.bool.isTablet);
        return isTablet;
    }

    /**
     * Determines whether or not a service is running
     *
     * @param context context
     * @param serviceClass class of the service
     * @return true if the service is running, false otherwise
     */
    public static boolean isServiceRunning(Context context, Class serviceClass) {
        ActivityManager manager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Hides keyboard
     * @param activity activity contexr
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
    }
}
