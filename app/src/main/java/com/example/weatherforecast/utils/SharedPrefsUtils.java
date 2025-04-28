package com.example.weatherforecast.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.weatherforecast.models.City;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for SharedPreferences operations
 */
public class SharedPrefsUtils {

    private static final String PREF_NAME = "WeatherAppPrefs";
    private static final String KEY_SAVED_CITIES = "saved_cities";
    private static final String KEY_UNIT_SYSTEM = "unit_system";
    private static final String KEY_REFRESH_INTERVAL = "refresh_interval";
    private static final String KEY_NOTIFICATION_ENABLED = "notification_enabled";
    private static final String KEY_DARK_MODE = "dark_mode";
    private static final String KEY_LAST_LOCATION = "last_location";
    private static final String KEY_CACHED_WEATHER = "cached_weather";
    private static final String KEY_REFRESH_NEEDED = "refresh_needed";

    /**
     * Get SharedPreferences instance
     * @param context Application context
     * @return SharedPreferences instance
     */
    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Save list of cities to preferences
     * @param context Application context
     * @param cities List of City objects
     */
    public static void saveCities(Context context, List<City> cities) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        Gson gson = new Gson();
        String json = gson.toJson(cities);
        editor.putString(KEY_SAVED_CITIES, json);
        editor.apply();
    }

    /**
     * Get saved list of cities from preferences
     * @param context Application context
     * @return List of City objects or empty list
     */
    public static List<City> getSavedCities(Context context) {
        SharedPreferences prefs = getPrefs(context);
        String json = prefs.getString(KEY_SAVED_CITIES, null);

        if (json == null) {
            return new ArrayList<>();
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<City>>() {}.getType();
        List<City> cities = gson.fromJson(json, type);

        return cities != null ? cities : new ArrayList<>();
    }

    /**
     * Save preferred unit system
     * @param context Application context
     * @param isMetric true for metric, false for imperial
     */
    public static void saveUnitSystem(Context context, boolean isMetric) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putBoolean(KEY_UNIT_SYSTEM, isMetric);
        editor.apply();
    }

    /**
     * Get preferred unit system
     * @param context Application context
     * @return true for metric, false for imperial
     */
    public static boolean isMetricUnits(Context context) {
        return getPrefs(context).getBoolean(KEY_UNIT_SYSTEM, true);
    }

    /**
     * Save refresh interval in minutes
     * @param context Application context
     * @param intervalMinutes Refresh interval in minutes
     */
    public static void saveRefreshInterval(Context context, int intervalMinutes) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putInt(KEY_REFRESH_INTERVAL, intervalMinutes);
        editor.apply();
    }

    /**
     * Get refresh interval in minutes
     * @param context Application context
     * @return Refresh interval in minutes (default: 30)
     */
    public static int getRefreshInterval(Context context) {
        return getPrefs(context).getInt(KEY_REFRESH_INTERVAL, 30);
    }

    /**
     * Save notification setting
     * @param context Application context
     * @param enabled true if notifications are enabled
     */
    public static void saveNotificationEnabled(Context context, boolean enabled) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putBoolean(KEY_NOTIFICATION_ENABLED, enabled);
        editor.apply();
    }

    /**
     * Get notification setting
     * @param context Application context
     * @return true if notifications are enabled
     */
    public static boolean isNotificationEnabled(Context context) {
        return getPrefs(context).getBoolean(KEY_NOTIFICATION_ENABLED, true);
    }

    /**
     * Save dark mode setting
     * @param context Application context
     * @param darkMode Dark mode setting (0: system default, 1: light, 2: dark)
     */
    public static void saveDarkMode(Context context, int darkMode) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putInt(KEY_DARK_MODE, darkMode);
        editor.apply();
    }

    /**
     * Get dark mode setting
     * @param context Application context
     * @return Dark mode setting (0: system default, 1: light, 2: dark)
     */
    public static int getDarkMode(Context context) {
        return getPrefs(context).getInt(KEY_DARK_MODE, 0);
    }

    /**
     * Save last used location
     * @param context Application context
     * @param city City object
     */
    public static void saveLastLocation(Context context, City city) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        Gson gson = new Gson();
        String json = gson.toJson(city);
        editor.putString(KEY_LAST_LOCATION, json);
        editor.apply();
    }

    /**
     * Get last used location
     * @param context Application context
     * @return City object or null
     */
    public static City getLastLocation(Context context) {
        SharedPreferences prefs = getPrefs(context);
        String json = prefs.getString(KEY_LAST_LOCATION, null);

        if (json == null) {
            return null;
        }

        Gson gson = new Gson();
        return gson.fromJson(json, City.class);
    }

    /**
     * Save string value
     * @param context Application context
     * @param key Preference key
     * @param value String value
     */
    public static void putString(Context context, String key, String value) {
        getPrefs(context).edit().putString(key, value).apply();
    }

    /**
     * Get string value
     * @param context Application context
     * @param key Preference key
     * @param defaultValue Default value
     * @return String value
     */
    public static String getString(Context context, String key, String defaultValue) {
        return getPrefs(context).getString(key, defaultValue);
    }

    /**
     * Save boolean value
     * @param context Application context
     * @param key Preference key
     * @param value Boolean value
     */
    public static void putBoolean(Context context, String key, boolean value) {
        getPrefs(context).edit().putBoolean(key, value).apply();
    }

    /**
     * Get boolean value
     * @param context Application context
     * @param key Preference key
     * @param defaultValue Default value
     * @return Boolean value
     */
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return getPrefs(context).getBoolean(key, defaultValue);
    }

    /**
     * Clear all preferences
     * @param context Application context
     */
    public static void clearAll(Context context) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.clear();
        editor.apply();
    }
}