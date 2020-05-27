package com.echo.moviememoir.entity.convert;

import androidx.room.TypeConverter;

import com.echo.moviememoir.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;

public class UserTypeConverter {
    private Gson gson = new Gson();

    @TypeConverter
    public User stringToUser(String data) {
        if (data == null) {
            return new User();
        }

        Type user = new TypeToken<User>() {
        }.getType();

        return gson.fromJson(data, user);
    }

    @TypeConverter
    public String userToString(User user) {
        return gson.toJson(user);
    }
}
