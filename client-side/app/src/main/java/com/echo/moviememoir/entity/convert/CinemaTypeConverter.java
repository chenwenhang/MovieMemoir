package com.echo.moviememoir.entity.convert;

import androidx.room.TypeConverter;

import com.echo.moviememoir.entity.Cinema;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class CinemaTypeConverter {

    private Gson gson = new Gson();

    @TypeConverter
    public Cinema stringToCinema(String data) {
        if (data == null) {
            return new Cinema();
        }

        Type cinema = new TypeToken<Cinema>() {
        }.getType();

        return gson.fromJson(data, cinema);
    }

    @TypeConverter
    public String cinemaToString(Cinema cinema) {
        return gson.toJson(cinema);
    }

}
