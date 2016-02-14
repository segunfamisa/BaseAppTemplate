package com.segunfamisa.base.utils;

import android.util.Log;

/**
 * Created by segunfamisa on 9/1/15.
 */
public class Logger {
    private static String TAG_DEFAULT = "AppName";

    private static boolean DEBUG_ON = true;

    /**
     * Method to do a debug log
     * @param tag
     * @param message
     */
    public static void d(String tag, String message){
        if(DEBUG_ON){
            Log.d(tag, message);
        }
    }

    public static void d(String message){
        d(TAG_DEFAULT, message);
    }

    public static void d(Class<?> cls, String message){
        d(cls.getSimpleName(), message);
    }


    /**
     * Method to do a warning log
     * @param tag
     * @param message
     */
    public static void w(String tag, String message){
        if(DEBUG_ON){
            Log.w(tag, message);
        }
    }

    public static void w(String message){
        w(TAG_DEFAULT, message);
    }

    public static void w(Class<?> cls, String message){
        w(cls.getSimpleName(), message);
    }


    /**
     * Method to do an error log
     * @param tag
     * @param message
     */
    public static void e(String tag, String message){
        if(DEBUG_ON){
            Log.e(tag, message);
        }
    }

    public static void e(String message){
        e(TAG_DEFAULT, message);
    }

    public static void e(Class<?> cls, String message){
        e(cls.getSimpleName(), message);
    }


    /**
     * Method to do an info tag
     * @param tag
     * @param message
     */
    public static void i(String tag, String message){
        if(DEBUG_ON){
            Log.i(tag, message);
        }
    }

    public static void i(String message){
        i(TAG_DEFAULT, message);
    }

    public static void i(Class<?> cls, String message){
        i(cls.getSimpleName(), message);
    }
}
