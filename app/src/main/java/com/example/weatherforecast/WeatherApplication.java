package com.example.weatherforecast;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class WeatherApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    public static Context context;
    public static final String TOKEN = "fJRKNUB6LbxMKPAu";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
} 