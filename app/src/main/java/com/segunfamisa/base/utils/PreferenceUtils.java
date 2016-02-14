package com.segunfamisa.base.utils;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * Created by segunfamisa on 8/31/15.
 */
public class PreferenceUtils {

    private static PreferenceUtils sInstance;

    private static Context sContext;

    SharedPreferences prefs;

    /**
     * String for the SharedPreference file
     */
    private static final String PREFS = "prefs";

    /**
     * key for storing the gcm id;
     */
    private static final String PREFS_GCM_TOKEN = "prefs_gcm_token";

    private static final String PREFS_DEVICE_ID = "prefs_device_id";

    @Inject
    public PreferenceUtils() {}

    private PreferenceUtils(Context context) {
        sContext = context;
        sInstance = this;
        prefs = sContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public static PreferenceUtils init(Context context) {
        sInstance = new PreferenceUtils(context);
        return sInstance;
    }

    public String getGcmToken(){
        return prefs.getString(PREFS_GCM_TOKEN, null);
    }

    public boolean saveGcmToken(String gcmId) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREFS_GCM_TOKEN, gcmId);
        return editor.commit();
    }

    public String getDeviceId(){
        return prefs.getString(PREFS_DEVICE_ID, null);
    }

    public void setDeviceId(String deviceId) {
        deviceId = StringUtils.nullify(deviceId);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREFS_DEVICE_ID, deviceId);
        editor.commit();
    }
}
