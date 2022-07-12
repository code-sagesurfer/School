package com.sagesurfer.school.resources;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {

    public static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private static final String TAG = "Utils";
    private static final String CURRENT_FORMAT = "MM-dd-yyyy";
    private static final String YEAR_FORMAT = "yyyy-MM-dd";
    private static final String MONTH_FORMAT = "MM-dd-yyyy";
    private static final String DATE_FORMAT = "dd-MM-yyyy";

    public static Date convertStringToDate(String strDate, String parseFormat) {
        try {
            if (!strDate.isEmpty())
                return new SimpleDateFormat(parseFormat, Locale.US).parse(strDate);
            else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String convertDateStringToString(String strDate,
                                                   String currentFormat, String parseFormat) {
        try {
            if (strDate != null && !strDate.isEmpty())
                return convertDateToString(convertStringToDate(strDate, currentFormat), parseFormat);
            else
                return "";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertDateToString(Date objDate, String parseFormat) {
        try {
            return new SimpleDateFormat(parseFormat, Locale.US).format(objDate);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getCurrentDateUSFormat() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
    }

    public static void hideKeyBoard(Context context, View view) {
        try {
            if (context == null)
                return;
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (view != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showKeyBoard(Context context) {
        try {
            if (context == null)
                return;
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void showToastMessage(Context context, String mgs, boolean isShort) {
        Toast.makeText(context, mgs, isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
    }

    public static void resizeDailog(Dialog dialog) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }

    public static void resizeFullDailog(Dialog dialog) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public static String timeMillisToDate(long timeMillis, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        return formatter.format(calendar.getTime());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String strDate = dtf.format(now);
        return strDate;
    }

    public static String getCurrentDateNew() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
    }

    public static String currentTimeString() {
        Calendar calendar=Calendar.getInstance();
        String formatToDisplyOnly = "yyyy-MM-dd";
        SimpleDateFormat sdf12hrs = new SimpleDateFormat(formatToDisplyOnly, Locale.US);
        String currentTime= sdf12hrs.format(calendar.getTime());
        return currentTime;

    }

    public static boolean containsUpperCaseCharacter(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (Character.isUpperCase(string.charAt(i))) {
                return true;
            }
        }

        return false;
    }

    public static boolean isValidPassword(String password)
    {
        // Regex to check valid password.
        /*1 small, 1 cap, 1 num, 1 special char, min 8chars*/
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the password is empty
        // return false
        if (password == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given password
        // and regular expression.
        Matcher m = p.matcher(password);

        // Return if the password
        // matched the ReGex
        return m.matches();
    }

    public static boolean validateEmail(String emailId) {
        if (emailId.matches(emailPattern))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static String getUserConvertedDateFromServerReceived(String date) {
        Log.i(TAG, "getUserDateFormatAsServer: date"+date);
        if (Preferences.get(General.USER_DATE_FORMAT).equalsIgnoreCase("mm-dd-yyyy")) {
            Log.i(TAG, "getUserDateFormatAsServer: return date 1"+convertDateStringToString(date,
                    CURRENT_FORMAT,
                    MONTH_FORMAT));
            return convertDateStringToString(date,
                    CURRENT_FORMAT,
                    MONTH_FORMAT);

        } else if (Preferences.get(General.USER_DATE_FORMAT).equalsIgnoreCase("dd-mm-yyyy")) {
            Log.i(TAG, "getUserDateFormatAsServer: return date 2"+convertDateStringToString(date,
                    CURRENT_FORMAT,
                    DATE_FORMAT));
            Log.i(TAG, "getUserDateFormatAsServer: return date 2 "+date);
            return convertDateStringToString(date,
                    CURRENT_FORMAT,
                    DATE_FORMAT);

        } else if (Preferences.get(General.USER_DATE_FORMAT).equalsIgnoreCase("yyyy-MM-dd")) {
            Log.i(TAG, "getUserDateFormatAsServer: return date 3  "+convertDateStringToString(date,
                    CURRENT_FORMAT,
                    YEAR_FORMAT));
            return convertDateStringToString(date,
                    CURRENT_FORMAT,
                    YEAR_FORMAT);
        }else{
            Log.i(TAG, "getUserDateFormatAsServer   : return date 4 "+convertDateStringToString(date,
                    CURRENT_FORMAT,
                    MONTH_FORMAT));
            return convertDateStringToString(date,
                    CURRENT_FORMAT,
                    MONTH_FORMAT);
        }
    }

    public static String getUserDateFormat() {
        if (Preferences.get(General.USER_DATE_FORMAT).equalsIgnoreCase("mm-dd-yyyy")) {
            return MONTH_FORMAT;
        }else if (Preferences.get(General.USER_DATE_FORMAT).equalsIgnoreCase("dd-mm-yyyy")) {
            return DATE_FORMAT;
        }else if (Preferences.get(General.USER_DATE_FORMAT).equalsIgnoreCase("yyyy-MM-dd")) {
            return YEAR_FORMAT;
        }else{
            return MONTH_FORMAT;
        }
    }

    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("MMM dd, yyyy | hh:mm a", cal).toString();
        return date;
    }

    public static String getTimeFromDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("hh:mm a", cal).toString();
        return date;
    }
}
