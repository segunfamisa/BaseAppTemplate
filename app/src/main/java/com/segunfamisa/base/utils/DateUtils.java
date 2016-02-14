package com.segunfamisa.base.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Segun Famisa {segunfamisa@gmail.com} on 9/17/15.
 */
public class DateUtils {


    /**
     * This method extracts the day from a long milliseconds
     * @param dateInMilliseconds
     * @return String of the day
     */
    public static String getDay(long dateInMilliseconds){
        Calendar cal = Clock.getInstance().getCalendar();
        cal.setTimeInMillis(dateInMilliseconds);

        int day = cal.get(Calendar.DAY_OF_MONTH);

        String dayStr = null;
        if(day < 10)
            dayStr = "0"+day;
        else
            dayStr = day+"";

        return dayStr;
    }

    /**
     *
     * @param dateInMilliseconds
     * @return short
     */
    public static String getMonthShort(long dateInMilliseconds){
        return getMonth(dateInMilliseconds, "MMM");
    }

    public static String getMonth(long dateInMilliseconds, String format){
        Calendar cal = Clock.getInstance().getCalendar();
        cal.setTimeInMillis(dateInMilliseconds);
        SimpleDateFormat sdf = null;
        try{
            sdf = new SimpleDateFormat(format);
        }
        catch (Exception e){
            sdf = new SimpleDateFormat("MMMM");
        }

        return sdf.format(cal.getTime());
    }

}
