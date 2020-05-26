package com.echo.moviememoir.utils;

import com.echo.moviememoir.entity.User;

public class LocalStorage {
    public static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        LocalStorage.user = user;
    }
}
