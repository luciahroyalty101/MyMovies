package com.moringaschool.mymovie.utils;

import android.net.ParseException;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;



public class Utility {

    public static final String TAG = "Utility";

    // Format the date received from guardian JSON
    public static String formatDate(String currentDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date newDate = null;
            try {
                newDate = format.parse(currentDate);
                format = new SimpleDateFormat("dd-MM-yyyy");
                currentDate = format.format(newDate);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }

        }catch (ParseException e) {
            Log.e(TAG, "Problem with parsing the date format");
        }
        return currentDate;
    }
}


