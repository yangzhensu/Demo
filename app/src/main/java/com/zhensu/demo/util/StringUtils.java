package com.zhensu.demo.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StringUtils {

    public static String parseDate(long timeInMillis) {
        timeInMillis *= 1000;
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        return formatter.format(calendar.getTime());
    }
}
