package com.example.weatherforecast.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.weatherforecast.R;
import com.example.weatherforecast.activities.WeatherAlertActivity;
import com.example.weatherforecast.models.City;
import com.example.weatherforecast.models.CurrentWeather;
import com.example.weatherforecast.models.WeatherAlert;
import com.example.weatherforecast.network.APIClient;
import com.example.weatherforecast.network.WeatherService;
import com.example.weatherforecast.utils.SharedPrefsUtils;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherUpdateService extends Service {

    private static final String TAG = "WeatherUpdateService";
    private static final String CHANNEL_ID = "weather_alerts_channel";
    private static final int NOTIFICATION_ID = 1001;

    private ScheduledExecutorService scheduler;
    private WeatherService weatherService;

    @Override
    public void onCreate() {
        super.onCreate();

        weatherService = APIClient.getClient().create(WeatherService.class);
        createNotificationChannel();

        Log.d(TAG, "Weather update service created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Weather update service started");

        // Start periodic weather updates
        startPeriodicUpdates();

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();
        }

        Log.d(TAG, "Weather update service destroyed");
    }

    private void startPeriodicUpdates() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();
        }

        scheduler = Executors.newSingleThreadScheduledExecutor();

        // Get refresh interval from settings (in minutes)
        int refreshInterval = SharedPrefsUtils.getRefreshInterval(this);

        // Schedule periodic updates
        scheduler.scheduleAtFixedRate(this::updateWeather, 0, refreshInterval, TimeUnit.MINUTES);
    }

    private void updateWeather() {
        // Get saved cities
        List<City> cities = SharedPrefsUtils.getSavedCities(this);

        if (cities != null && !cities.isEmpty()) {
            // Update weather for each city
            for (City city : cities) {
                updateCityWeather(city);
            }
        }
    }

    private void updateCityWeather(City city) {
        String apiKey = getString(R.string.weather_api_key);
        String location = city.getLatitude() + "," + city.getLongitude();

        Call<CurrentWeather> call = weatherService.getCurrentWeather(
                location, apiKey, "current,alerts", "metric", "en");

        call.enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CurrentWeather weatherData = response.body();

                    // Save weather data to cache
                    String weatherJson = new Gson().toJson(weatherData);
                    SharedPrefsUtils.getPrefs(getApplicationContext())
                            .edit()
                            .putString("cached_weather_" + city.getName(), weatherJson)
                            .apply();

                    // Check for weather alerts
                    if (weatherData.hasAlerts() && SharedPrefsUtils.isNotificationEnabled(getApplicationContext())) {
                        showWeatherAlertNotification(weatherData);
                    }

                    Log.d(TAG, "Weather updated for " + city.getName());
                }
            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {
                Log.e(TAG, "Error updating weather for " + city.getName() + ": " + t.getMessage());
            }
        });
    }

    private void showWeatherAlertNotification(CurrentWeather weatherData) {
        if (weatherData.getAlerts() == null || weatherData.getAlerts().isEmpty()) {
            return;
        }

        WeatherAlert alert = weatherData.getAlerts().get(0);

        // Create notification intent
        Intent intent = new Intent(this, WeatherAlertActivity.class);
        intent.putExtra("weather_data", weatherData);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Build notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Weather Alert: " + alert.getEvent())
                .setContentText(alert.getHeadline())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Show notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Weather Alerts";
            String description = "Notifications for severe weather alerts";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}