package com.example.weatherforecast.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.weatherforecast.R;
import com.example.weatherforecast.models.Weather;
import com.example.weatherforecast.repository.Repository;
import com.example.weatherforecast.utils.WeatherUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeatherActivity extends AppCompatActivity {
    
    private TextView placeNameText;
    private TextView temperatureText;
    private TextView weatherDescriptionText;
    private TextView aqiText;
    private TextView humidityText;
    private TextView windSpeedText;
    private TextView updateTimeText;
    private ImageView weatherIcon;
    private LinearLayout forecastLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View weatherLayout;
    
    private String locationLng;
    private String locationLat;
    private String placeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Set status bar transparent
        getWindow().getDecorView().setSystemUiVisibility(
            android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | 
            android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        
        setContentView(R.layout.activity_weather);
        
        initViews();
        loadIntentData();
        setupSwipeRefresh();
        refreshWeather();
    }
    
    private void initViews() {
        placeNameText = findViewById(R.id.placeName);
        temperatureText = findViewById(R.id.currentTemp);
        weatherDescriptionText = findViewById(R.id.currentSky);
        aqiText = findViewById(R.id.currentAQI);
        humidityText = findViewById(R.id.humidity);
        windSpeedText = findViewById(R.id.windSpeed);
        updateTimeText = findViewById(R.id.updateTime);
        weatherIcon = findViewById(R.id.weatherIcon);
        forecastLayout = findViewById(R.id.forecastLayout);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        weatherLayout = findViewById(R.id.weatherLayout);
    }
    
    private void loadIntentData() {
        placeName = getIntent().getStringExtra("place_name");
        locationLng = getIntent().getStringExtra("location_lng");
        locationLat = getIntent().getStringExtra("location_lat");
        
        if (placeName != null) {
            placeNameText.setText(placeName);
        }
    }
    
    private void setupSwipeRefresh() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this::refreshWeather);
    }
    
    private void refreshWeather() {
        if (locationLng == null || locationLat == null) {
            Toast.makeText(this, "位置信息无效", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
            return;
        }
        
        // 显示加载状态
        weatherLayout.setVisibility(View.GONE);
        
        // 调用天气API
        Repository.getWeather(locationLng, locationLat, new Repository.WeatherCallback() {
            @Override
            public void onSuccess(Weather weather) {
                runOnUiThread(() -> {
                    displayWeatherData(weather);
                    swipeRefreshLayout.setRefreshing(false);
                });
            }
            
            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(WeatherActivity.this, "获取天气信息失败: " + error, Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                    // 显示默认数据
                    displayDefaultWeather();
                });
            }
        });
    }
    
    private void displayWeatherData(Weather weather) {
        if (weather == null || weather.getRealtime() == null) {
            displayDefaultWeather();
            return;
        }
        
        // 显示实时天气
        var realtime = weather.getRealtime();
        temperatureText.setText(String.format("%.0f°C", realtime.getTemperature()));
        weatherDescriptionText.setText(WeatherUtils.getWeatherDescription(realtime.getSkycon()));
        
        // 显示空气质量
        if (realtime.getAirQuality() != null && realtime.getAirQuality().getAqi() != null) {
            aqiText.setText(String.format("空气质量: %.0f", realtime.getAirQuality().getAqi().getChn()));
        }
        
        // 设置天气图标
        weatherIcon.setImageResource(WeatherUtils.getWeatherIcon(realtime.getSkycon()));
        
        // 显示更新时间
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        updateTimeText.setText("更新时间: " + sdf.format(new Date()));
        
        // 显示预报信息
        if (weather.getDaily() != null) {
            displayForecast(weather.getDaily());
        }
        
        weatherLayout.setVisibility(View.VISIBLE);
    }
    
    private void displayForecast(DailyWeather daily) {
        forecastLayout.removeAllViews();
        
        if (daily.getSkycon() != null && daily.getTemperature() != null) {
            for (int i = 0; i < Math.min(daily.getSkycon().size(), 7); i++) {
                View forecastItem = getLayoutInflater().inflate(R.layout.forecast_item, forecastLayout, false);
                
                TextView dateText = forecastItem.findViewById(R.id.dateInfo);
                TextView tempText = forecastItem.findViewById(R.id.temperatureInfo);
                TextView weatherText = forecastItem.findViewById(R.id.skyInfo);
                ImageView iconView = forecastItem.findViewById(R.id.skyIcon);
                
                var skycon = daily.getSkycon().get(i);
                var temp = daily.getTemperature().get(i);
                
                // 设置日期
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd", Locale.getDefault());
                dateText.setText(sdf.format(new Date()));
                
                // 设置温度
                tempText.setText(String.format("%.0f°~%.0f°", temp.getMin(), temp.getMax()));
                
                // 设置天气描述和图标
                weatherText.setText(WeatherUtils.getWeatherDescription(skycon.getValue()));
                iconView.setImageResource(WeatherUtils.getWeatherIcon(skycon.getValue()));
                
                forecastLayout.addView(forecastItem);
            }
        }
    }
    
    private void displayDefaultWeather() {
        temperatureText.setText("25°C");
        weatherDescriptionText.setText("晴天");
        aqiText.setText("空气质量: 50");
        updateTimeText.setText("更新时间: " + new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
        weatherIcon.setImageResource(R.drawable.ic_clear_day);
        weatherLayout.setVisibility(View.VISIBLE);
    }
} 