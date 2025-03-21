package com.example.weatherforecast.utils;

import com.example.weatherforecast.R;

/**
 * Utility class for handling weather icons
 */
public class IconUtils {

    /**
     * Get the appropriate drawable resource for a weather icon code
     * @param iconCode Weather icon code from API
     * @return Resource ID for the weather icon drawable
     */
    public static int getWeatherIconResource(String iconCode) {
        if (iconCode == null) {
            return R.drawable.ic_weather_unknown;
        }

        // Map Visual Crossing icon codes to app drawable resources
        switch (iconCode) {
            case "clear-day":
                return R.drawable.ic_weather_clear_day;
            case "clear-night":
                return R.drawable.ic_weather_clear_night;
            case "partly-cloudy-day":
                return R.drawable.ic_weather_partly_cloudy_day;
            case "partly-cloudy-night":
                return R.drawable.ic_weather_partly_cloudy_night;
            case "cloudy":
                return R.drawable.ic_weather_cloudy;
            case "rain":
                return R.drawable.ic_weather_rain;
            case "showers-day":
            case "showers-night":
                return R.drawable.ic_weather_showers;
            case "fog":
                return R.drawable.ic_weather_fog;
            case "snow":
                return R.drawable.ic_weather_snow;
            case "snow-showers-day":
            case "snow-showers-night":
                return R.drawable.ic_weather_snow_showers;
            case "wind":
                return R.drawable.ic_weather_wind;
            case "thunder-rain":
            case "thunder-showers-day":
            case "thunder-showers-night":
                return R.drawable.ic_weather_thunderstorm;
            case "sleet":
                return R.drawable.ic_weather_sleet;
            default:
                return R.drawable.ic_weather_unknown;
        }
    }

    /**
     * Get the background resource for the current weather condition
     * @param iconCode Weather icon code from API
     * @param isDay Whether it's currently daytime
     * @return Resource ID for the background drawable
     */
    public static int getWeatherBackgroundResource(String iconCode, boolean isDay) {
        if (iconCode == null) {
            return isDay ? R.drawable.bg_clear_day : R.drawable.bg_clear_night;
        }

        // Map icon codes to background resources
        if (iconCode.contains("clear")) {
            return isDay ? R.drawable.bg_clear_day : R.drawable.bg_clear_night;
        } else if (iconCode.contains("cloud") || iconCode.contains("partly")) {
            return isDay ? R.drawable.bg_partly_cloudy_day : R.drawable.bg_partly_cloudy_night;
        } else if (iconCode.contains("rain") || iconCode.contains("shower")) {
            return R.drawable.bg_rain;
        } else if (iconCode.contains("snow")) {
            return R.drawable.bg_snow;
        } else if (iconCode.contains("thunder")) {
            return R.drawable.bg_thunderstorm;
        } else if (iconCode.contains("fog")) {
            return R.drawable.bg_fog;
        } else {
            return isDay ? R.drawable.bg_clear_day : R.drawable.bg_clear_night;
        }
    }

    /**
     * Get the animation resource for a weather condition
     * @param iconCode Weather icon code from API
     * @return Resource ID for the animation drawable, or 0 if no animation
     */
    public static int getWeatherAnimationResource(String iconCode) {
        if (iconCode == null) {
            return 0;
        }

        // Map icon codes to animation resources
        if (iconCode.contains("rain") || iconCode.contains("shower")) {
            return R.drawable.anim_rain;
        } else if (iconCode.contains("snow")) {
            return R.drawable.anim_snow;
        } else if (iconCode.contains("thunder")) {
            return R.drawable.anim_thunder;
        } else {
            return 0; // No animation
        }
    }

    /**
     * Determine if the current time is daytime based on sunrise and sunset
     * @param currentTimeMillis Current time in milliseconds
     * @param sunriseTimeMillis Sunrise time in milliseconds
     * @param sunsetTimeMillis Sunset time in milliseconds
     * @return true if it's currently daytime
     */
    public static boolean isDaytime(long currentTimeMillis, long sunriseTimeMillis, long sunsetTimeMillis) {
        return currentTimeMillis >= sunriseTimeMillis && currentTimeMillis < sunsetTimeMillis;
    }
}
