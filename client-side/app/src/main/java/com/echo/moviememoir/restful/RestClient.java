package com.echo.moviememoir.restful;

import com.echo.moviememoir.entity.Cinema;
import com.echo.moviememoir.entity.Credential;
import com.echo.moviememoir.entity.Memoir;
import com.echo.moviememoir.entity.User;
import com.echo.moviememoir.utils.RestUtils;

public class RestClient {
    /**
     * Connect to backend
     */

    // cinema
    public static String findAllCinemas() {
        final String methodPath = "ent.cinema";
        return RestUtils.getData(methodPath);
    }

    public static void createCinema(Cinema cinema) {
        final String methodPath = "ent.cinema";
        RestUtils.postData(methodPath, cinema);
    }

    // credential
    public static void createCredential(Credential credential) {
        final String methodPath = "ent.credentials";
        RestUtils.postData(methodPath, credential);
    }

    public static String findAllCredentials() {
        final String methodPath = "ent.credentials";
        return RestUtils.getData(methodPath);
    }

    public static String findByUsername(String username) {
        final String methodPath = "ent.credentials/findByUsername/" + username;
        return RestUtils.getData(methodPath);
    }


    // user
    public static void createUser(User user) {
        final String methodPath = "ent.users";
        RestUtils.postData(methodPath, user);
    }

    public static String findAllUsers() {
        final String methodPath = "ent.users";
        return RestUtils.getData(methodPath);
    }

    public static String findByUserId(Integer userId) {
        final String methodPath = "ent.users/" + userId;
        return RestUtils.getData(methodPath);
    }

    // memoir
    public static void createMemoir(Memoir memoir) {
        final String methodPath = "ent.memoir";
        memoir.setImageUrl(null);
        memoir.setAddDateTime(null);
        memoir.setDescription(null);
        RestUtils.postData(methodPath, memoir);
    }

    public static String findAllMemoirs() {
        final String methodPath = "ent.memoir";
        return RestUtils.getData(methodPath);
    }

    public static String findFiveHighestMovieByUserId(Integer userId) {
        final String methodPath = "ent.memoir/findFiveHighestMovieByUserId/" + userId;
        return RestUtils.getData(methodPath);
    }

    public static String findByMovieName(String movieName) {
        final String methodPath = "ent.memoir/findByMovieName/" + movieName;
        return RestUtils.getData(methodPath);
    }
}