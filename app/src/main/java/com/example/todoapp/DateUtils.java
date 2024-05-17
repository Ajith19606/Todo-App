package com.example.todoapp;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateUtils {

    public static String formatDate(String inputDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmm", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(inputFormat.parse(inputDate));

            int day = calendar.get(Calendar.DAY_OF_MONTH);
            String month = new DateFormatSymbols().getMonths()[calendar.get(Calendar.MONTH)];

            return day + " " + month;

        } catch (ParseException e) {
            e.printStackTrace();
            return "error";
        }
    }
}

