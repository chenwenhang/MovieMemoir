package com.echo.moviememoir.utils;

import com.echo.moviememoir.entity.Memoir;
import com.echo.moviememoir.entity.User;

public class LocalStorage {
    public static User user;
    public static Memoir memoir;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        LocalStorage.user = user;
    }

    public static Memoir getMemoir() {
        return memoir;
    }

    public static void setMemoir(Memoir memoir) {
        LocalStorage.memoir = memoir;
    }
}
