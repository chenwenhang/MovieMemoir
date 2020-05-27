package com.echo.moviememoir.entity.convert;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;

public class DateTypeConverter {
    private Gson gson = new Gson();

    @TypeConverter
    public Date stringToDate(String data) {
        if (data == null) {
            return new Date();
        }

        Type date = new TypeToken<Date>() {
        }.getType();

        return gson.fromJson(data, date);
    }

    @TypeConverter
    public String dateToString(Date date) {
        return gson.toJson(date);
    }
}
