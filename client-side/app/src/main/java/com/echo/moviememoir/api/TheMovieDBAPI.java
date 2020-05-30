package com.echo.moviememoir.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class TheMovieDBAPI {
    private static final String API_KEY = "YOUR API_KEY";


    // search movie
    public static String searchMovie(String keyword) {
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";

        try {
            url = new URL("https://z4vrpkijmodhwsxzc.stoplight-proxy.io/3/search/movie?language=en-US&page=1&include_adult=false&api_key=" + API_KEY + "&query=" + keyword);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return textResult;
    }

    public static JSONArray getSnippet(String result, String name) {
        String snippet = null;
        JSONArray jsonArray = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            jsonArray = jsonObject.getJSONArray(name);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
}
