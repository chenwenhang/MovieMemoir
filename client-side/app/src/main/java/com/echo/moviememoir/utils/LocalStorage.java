package com.echo.moviememoir.utils;

import com.echo.moviememoir.entity.Credential;
import com.echo.moviememoir.entity.Memoir;
import com.echo.moviememoir.entity.User;

public class LocalStorage {
    public static User user;
    public static Memoir memoir;
    public static String postUrl;
    public static boolean canAddToWatchlist;

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

    public static boolean isCanAddToWatchlist() {
        return canAddToWatchlist;
    }

    public static void setCanAddToWatchlist(boolean canAddToWatchlist) {
        LocalStorage.canAddToWatchlist = canAddToWatchlist;
    }

    public static String getPostUrl() {
        return postUrl;
    }

    public static void setPostUrl(String postUrl) {
        LocalStorage.postUrl = postUrl;
    }
}
