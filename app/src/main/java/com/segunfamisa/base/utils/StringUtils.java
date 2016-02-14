package com.segunfamisa.base.utils;

import android.text.TextUtils;

/**
 * Created by segunfamisa on 9/2/15.
 */
public class StringUtils {

    /**
     * Gets whether or not a string is null, empty or "null"
     * @param str string to check
     * @return true if the string is null, empty or "null"
     */
    public static boolean isEmpty(String str){
        return TextUtils.isEmpty(str) || str.equalsIgnoreCase("null");
    }

    /**
     * This method nullifies a string.
     *
     * @param value string to nullify
     * @return the input string if the string is not empty, null otherwise
     */
    public static String nullify(String value){
        if(isEmpty(value)){
            return null;
        }
        return value;
    }
}
