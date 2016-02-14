package com.segunfamisa.base.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Network related utils
 *
 * Created by Segun Famisa <segunfamisa@gmail.com> on 14/02/2016.
 */
public class NetworkUtils {


    /**
     * Checks whether network is connected
     *
     * @param context context
     * @return true if the network is connected, false otherwise
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
