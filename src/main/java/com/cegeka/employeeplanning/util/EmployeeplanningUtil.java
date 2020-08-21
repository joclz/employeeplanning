package com.cegeka.employeeplanning.util;

import static java.util.Calendar.getInstance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeplanningUtil {

    public static final String TEST_IMPORT = "/data.sql";
    public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public static Date today()
    {
        return getInstance().getTime();
    }

    public static String formateTodayDateToString() {
        Date today = getInstance().getTime();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormatter.format(today);
    }

    public static Date parseDate(String dateString) {
        if (dateString == null || dateString.length() == 0)
        {
            return null;
        }

        Date date = null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static double round(double value, int decimalPoints) {
        double d = Math.pow(10, decimalPoints);
        return Math.round(value * d) / d;
    }
}
