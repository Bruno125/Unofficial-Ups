package com.brunoaybar.unofficialupc.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by brunoaybar on 16/10/2016.
 */

public class Utils {

    public static boolean isEmpty(String text){
        return text==null || text.length()<1;
    }

    public static boolean sameDay(Date date1, Date date2){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }
}
