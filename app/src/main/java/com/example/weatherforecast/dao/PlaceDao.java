package com.example.weatherforecast.dao;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.weatherforecast.WeatherApplication;
import com.example.weatherforecast.models.Place;
import com.google.gson.Gson;

public class PlaceDao {
    private static final String PREF_NAME = "weather_preferences";
    private static final String KEY_PLACE = "saved_place";

    public static void savePlace(Place place) {
        SharedPreferences prefs = WeatherApplication.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_PLACE, new Gson().toJson(place));
        editor.apply();
    }

    public static Place getSavedPlace() {
        SharedPreferences prefs = WeatherApplication.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String placeJson = prefs.getString(KEY_PLACE, "");
        if (placeJson.isEmpty()) {
            return null;
        }
        return new Gson().fromJson(placeJson, Place.class);
    }

    public static boolean isPlaceSaved() {
        SharedPreferences prefs = WeatherApplication.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.contains(KEY_PLACE);
    }
} 