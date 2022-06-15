package com.example.school.resources;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Monika M (monikam@sagesurfer.com)
 * Created on 13/03/2018
 * Last Modified on
 */

/*
 * This class is used to converts timestamp in milliseconds to desire date time format
 */

public class GetTime {
    private static final String TAG = "GetTime";
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public static String getChatTimestamp() {
        Long tsLong = System.currentTimeMillis() / 1000;
        return tsLong.toString();
    }

    public static String wallTime(long time) {
        long now = System.currentTimeMillis();
        if (time < 1000000000000L) {
            time *= 1000;
        }
       /* if (time > now || time <= 0) {
            AppLog.i(TAG, "wallTime:  time > now || time <= 0 and time = "+time +" and now = "+now);
            return "now";
        }*/
        AppLog.i(TAG,"now "+now +" time "+time);
        final long diff = now - time;

        if (diff < MINUTE_MILLIS) {
            AppLog.i(TAG, "wallTime: diff < MINUTE_MILLIS "+diff);
            return "now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            AppLog.i(TAG, "wallTime: diff < 2 * MINUTE_MILLIS "+diff);
            return "1 min";
        } else if (diff < 50 * MINUTE_MILLIS) {
            AppLog.i(TAG, "wallTime: diff < 50 * MINUTE_MILLIS "+diff);
            return diff / MINUTE_MILLIS + " min";
        } else if (diff < 90 * MINUTE_MILLIS) {
            AppLog.i(TAG, "diff < 90 * MINUTE_MILLIS "+diff);
            return "1 hr";
        } else if (diff < 24 * HOUR_MILLIS) {
            AppLog.i(TAG, "wallTime: diff < 24 * HOUR_MILLIS "+diff);
            return diff / HOUR_MILLIS + " hr";
        } else if (diff < 48 * HOUR_MILLIS) {
            AppLog.i(TAG, "wallTime: diff < 24 * HOUR_MILLIS "+diff);
            return "1 day";
        } else if ((diff / DAY_MILLIS) > 3) {
            AppLog.i(TAG, "wallTime: diff < MINUTE_MILLIS "+diff);
            return getDate(time);
        } else {
            AppLog.i(TAG, "wallTime: diff / DAY_MILLIS) > 3 "+diff);
            return diff / DAY_MILLIS + " days";
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static String month_DdYyyy(String original_date) {
        //Log.e(GetTime.class.getSimpleName(), "original_date: " + original_date);
        if (original_date == null) {
            return "";
        }
        if (original_date.trim().length() <= 0) {
            return "";
        }
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat convertFormat = new SimpleDateFormat("MMM dd, yyyy");
        try {
            Date originalDate = originalFormat.parse(original_date);
            return convertFormat.format(originalDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return original_date;
    }

    @SuppressLint("SimpleDateFormat")
    public static String yy_mm_dd(String original_date) {
        //Log.e(GetTime.class.getSimpleName(), "original_date: " + original_date);
        if (original_date == null) {
            return "";
        }
        if (original_date.trim().length() <= 0) {
            return "";
        }
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat convertFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date originalDate = originalFormat.parse(original_date);
            return convertFormat.format(originalDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return original_date;
    }

    public static String dueTime(long time) {
        long now = System.currentTimeMillis();
        if (time < 1000000000000L) {
            time *= 1000;
        }

        if (time > now || time <= 0) {
            return "due now";
        }

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "Due now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "Due in 1 min";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return "Due in " + diff / MINUTE_MILLIS + " min";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "Due in 1 hr";
        } else if (diff < 24 * HOUR_MILLIS) {
            return "Due in " + diff / HOUR_MILLIS + " hr";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "Due in 1 day";
        } else if ((diff / DAY_MILLIS) > 3) {
            return getDate(time);
        } else {
            return "Due in " + diff / DAY_MILLIS + " days";
        }
    }

    public static Date addDay(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, i);
        return cal.getTime();
    }

    @SuppressLint("SimpleDateFormat")
    public static String todaysDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(c.getTime());
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDate(long time) {
        if (time < 1000000000000L) {
            time *= 1000;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy");
        Date result_date = new Date(time);
        return sdf.format(result_date);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getTodayEeeMm(long time) {
        if (time < 1000000000000L) {
            time *= 1000;
        }
        SimpleDateFormat formatFrom = new SimpleDateFormat("EEE, MMM dd yyyy HH:mm:ss");
        Date result_date = new Date(time);
        return formatFrom.format(result_date);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getTodayMm(long time) {
        if (time < 1000000000000L) {
            time *= 1000;
        }
        SimpleDateFormat formatFrom = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
        Date result_date = new Date(time);
        return formatFrom.format(result_date);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getTodayMonthYear(long time) {
        if (time < 1000000000000L) {
            time *= 1000;
        }
        SimpleDateFormat formatFrom = new SimpleDateFormat("EEEE, MMM dd yyyy");
        Date result_date = new Date(time);
        return formatFrom.format(result_date);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getMmDdYyyy(long time) {
        if (time < 1000000000000L) {
            time *= 1000;
        }
        SimpleDateFormat formatFrom = new SimpleDateFormat("yyyy-MM-dd");
        Date result_date = new Date(time);
        return formatFrom.format(result_date);
    }


    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDateYyyyMmDd() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        return sdfDate.format(now);
    }

    public static int currentTimeSpan() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay >= 0 && timeOfDay < 12) {
            return 1;
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            return 2;
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            return 3;
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            return 4;
        }
        return 1;
    }

    @SuppressLint("SimpleDateFormat")
    public static long getDateTimestamp(String date_string) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        long dateInMillis = 0;
        try {
            Date date = formatter.parse(date_string);
            dateInMillis = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateInMillis / 1000;
    }



    @SuppressLint("SimpleDateFormat")
    public static long dayDifference(String start_date, String end_date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate;
        Date endDate;
        try {
            startDate = dateFormat.parse(start_date);
            endDate = dateFormat.parse(end_date);
            long diff = endDate.getTime() - startDate.getTime();
            return diff / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
