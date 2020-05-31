package com.echo.moviememoir.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class BoxMapAPI {
    private static final String BASE_URL = "https://api.mapbox.com/geocoding/v5/mapbox.places/";
    private static final String ACCESS_TOKEN = "YOUR ACCESS_TOKEN";
    private static final String COUNTRY = "AU";

    public static String getAddressInfo(String address) {
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";

        try {
            url = new URL(BASE_URL + address + ".json?access_token=" + ACCESS_TOKEN + "&country=" + COUNTRY);
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

    public static String[] getCoordinates(String result) {
        String snippet = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("features");

            if (jsonArray != null && jsonArray.length() > 0) {
                snippet = jsonArray.getJSONObject(0).getString("center");
            }

        } catch (Exception e) {
            e.printStackTrace();
            snippet = "NO INFO FOUND";
        }
        assert snippet != null;
        String[] coordinates = snippet.split(",");
        coordinates[0] = coordinates[0].substring(1);
        coordinates[1] = coordinates[1].substring(0, coordinates[1].length() - 1);
        return coordinates;
    }

}
