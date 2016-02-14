package com.segunfamisa.base.utils;

import java.util.Calendar;

/**
 * Created by Segun Famisa {segunfamisa@gmail.com} on 9/17/15.
 */
public class Clock {


    private static Clock sInstance;
    private Calendar calendarInstance;

    public static Clock getInstance(){
        if(sInstance == null)
            sInstance = new Clock();

        return sInstance;
    }

    private Calendar _calendarInstance(){
        return calendarInstance != null ? (Calendar)calendarInstance.clone() : Calendar.getInstance();
    }

    public Calendar getCalendar(){
        return getInstance()._calendarInstance();
    }

}
