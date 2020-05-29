package com.echo.moviememoir.api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ImageAPI {
    private static final String BASE_URL = "http://image.tmdb.org/t/p/w500";

    // get Image
    public static Bitmap getImage(String relative_url) {
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        Bitmap bitmap = null;

        try {
            url = new URL(BASE_URL + relative_url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("charset", "UTF-8");
            StringBuilder s = new StringBuilder();
            String str;
            if (connection.getResponseCode() == 200) {
                InputStream in = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return bitmap;
    }


}
