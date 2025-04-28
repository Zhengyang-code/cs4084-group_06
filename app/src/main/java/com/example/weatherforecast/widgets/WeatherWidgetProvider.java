package com.example.weatherforecast.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.weatherforecast.R;
import com.example.weatherforecast.models.City;
import com.example.weatherforecast.models.CurrentWeather;
import com.example.weatherforecast.network.APIClient;
import com.example.weatherforecast.network.WeatherService;
import com.example.weatherforecast.utils.IconUtils;
import com.example.weatherforecast.utils.SharedPrefsUtils;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Get the last viewed city
        City lastCity = SharedPrefsUtils.getLastLocation(context);

        if (lastCity != null) {
            // Update weather for the last city
            updateWidgetWeather(context, appWidgetManager, appWidgetIds, lastCity);
        } else {
            // Show placeholder data if no city is available
            for (int appWidgetId : appWidgetIds) {
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_weather);
                views.setTextViewText(R.id.widget_city_name, "No location");
                views.setTextViewText(R.id.widget_temperature, "--°");
                views.setTextViewText(R.id.widget_condition, "Unknown");

                // Set click intent to open app
                Intent intent = new Intent(context, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);

                appWidgetManager.updateAppWidget(appWidgetId, views);
            }
        }
    }

    private void updateWidgetWeather(Context context, AppWidgetManager appWidgetManager,
                                     int[] appWidgetIds, City city) {
        WeatherService weatherService = APIClient.getClient().create(WeatherService.class);
        String apiKey = context.getString(R.string.weather_api_key);
        String location = city.getLatitude() + "," + city.getLongitude();

        Call<CurrentWeather> call = weatherService.getCurrentWeather(
                location, apiKey, "current", "metric", "en");

        call.enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CurrentWeather weatherData = response.body();

                    for (int appWidgetId : appWidgetIds) {
                        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_weather);

                        // Update widget data
                        views.setTextViewText(R.id.widget_city_name, weatherData.getLocationName());
                        views.setTextViewText(R.id.widget_temperature,
                                String.format("%.0f°", weatherData.getCurrent().getTemperature()));
                        views.setTextViewText(R.id.widget_condition,
                                weatherData.getCurrent().getConditions());

                        // Set weather icon
                        int iconResId = IconUtils.getWeatherIconResource(weatherData.getCurrent().getIcon());
                        views.setImageViewResource(R.id.widget_weather_icon, iconResId);

                        // Set click intent to open app
                        Intent intent = new Intent(context, MainActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                        views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);

                        // Update widget
                        appWidgetManager.updateAppWidget(appWidgetId, views);
                    }
                }
            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {
                // On failure, show cached data if available
                String cachedWeatherJson = SharedPrefsUtils.getPrefs(context)
                        .getString("cached_weather", null);

                if (cachedWeatherJson != null) {
                    try {
                        CurrentWeather cachedWeather = new Gson().fromJson(cachedWeatherJson, CurrentWeather.class);

                        for (int appWidgetId : appWidgetIds) {
                            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_weather);

                            // Update widget with cached data
                            views.setTextViewText(R.id.widget_city_name, cachedWeather.getLocationName());
                            views.setTextViewText(R.id.widget_temperature,
                                    String.format("%.0f°", cachedWeather.getCurrent().getTemperature()));
                            views.setTextViewText(R.id.widget_condition,
                                    cachedWeather.getCurrent().getConditions());

                            // Set weather icon
                            int iconResId = IconUtils.getWeatherIconResource(cachedWeather.getCurrent().getIcon());
                            views.setImageViewResource(R.id.widget_weather_icon, iconResId);

                            // Add "(Offline)" indicator
                            views.setTextViewText(R.id.widget_update_time, "(Offline)");

                            // Set click intent to open app
                            Intent intent = new Intent(context, MainActivity.class);
                            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                            views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);

                            // Update widget
                            appWidgetManager.updateAppWidget(appWidgetId, views);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}