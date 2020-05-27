package com.echo.moviememoir.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateString {
    public static String date2String(Date date) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public static Date string2Date(String str) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(str);
    }
}
