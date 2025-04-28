package com.example.weatherforecast.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.weatherforecast.R;
import com.example.weatherforecast.adapters.WeatherAlertAdapter;
import com.example.weatherforecast.models.CurrentWeather;
import com.example.weatherforecast.models.WeatherAlert;

import java.util.ArrayList;
import java.util.List;

public class WeatherAlertActivity extends AppCompatActivity {

    private TextView tvNoAlerts;
    private ListView listViewAlerts;
    private WeatherAlertAdapter alertAdapter;
    private List<WeatherAlert> alertList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_alert);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Weather Alerts");
        }

        tvNoAlerts = findViewById(R.id.tv_no_alerts);
        listViewAlerts = findViewById(R.id.list_view_alerts);

        alertList = new ArrayList<>();
        alertAdapter = new WeatherAlertAdapter(this, alertList);
        listViewAlerts.setAdapter(alertAdapter);

        // Get weather data from intent
        if (getIntent().hasExtra("weather_data")) {
            CurrentWeather weatherData = (CurrentWeather) getIntent().getSerializableExtra("weather_data");
            displayAlerts(weatherData);
        }
    }

    private void displayAlerts(CurrentWeather weatherData) {
        if (weatherData != null && weatherData.hasAlerts()) {
            alertList.clear();
            alertList.addAll(weatherData.getAlerts());
            alertAdapter.notifyDataSetChanged();

            // Show list, hide empty view
            tvNoAlerts.setVisibility(alertList.isEmpty() ? android.view.View.VISIBLE : android.view.View.GONE);
            listViewAlerts.setVisibility(alertList.isEmpty() ? android.view.View.GONE : android.view.View.VISIBLE);
        } else {
            // No alerts to show
            tvNoAlerts.setVisibility(android.view.View.VISIBLE);
            listViewAlerts.setVisibility(android.view.View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
