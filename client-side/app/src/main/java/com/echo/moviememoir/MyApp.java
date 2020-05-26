package com.echo.moviememoir;

import android.app.Application;

import com.mikepenz.iconics.Iconics;
import com.xuexiang.xui.XUI;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        XUI.init(this);
        XUI.debug(true);
        Iconics.init(this);
    }
}
