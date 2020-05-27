package com.echo.moviememoir.entity.convert;

import androidx.room.TypeConverter;

import com.echo.moviememoir.entity.Credential;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class CredentialTypeConverter {
    private Gson gson = new Gson();

    @TypeConverter
    public Credential stringToCredential(String data) {
        if (data == null) {
            return new Credential();
        }

        Type credential = new TypeToken<Credential>() {
        }.getType();

        return gson.fromJson(data, credential);
    }

    @TypeConverter
    public String credentialToString(Credential credential) {
        return gson.toJson(credential);
    }
}
