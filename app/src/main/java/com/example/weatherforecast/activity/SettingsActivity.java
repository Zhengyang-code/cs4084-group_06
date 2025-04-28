package com.example.weatherforecast.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.weatherforecast.R;
import com.example.weatherforecast.utils.SharedPrefsUtils;

public class SettingsActivity extends AppCompatActivity {

    private RadioGroup unitsRadioGroup;
    private RadioButton metricRadioButton;
    private RadioButton imperialRadioButton;
    private Switch notificationsSwitch;
    private Switch locationSwitch;
    private Button btnClearCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Settings");
        }

        // Initialize views
        initViews();

        // Load saved settings
        loadSettings();

        // Setup listeners
        setupListeners();
    }

    private void initViews() {
        unitsRadioGroup = findViewById(R.id.radio_group_units);
        metricRadioButton = findViewById(R.id.radio_metric);
        imperialRadioButton = findViewById(R.id.radio_imperial);
        notificationsSwitch = findViewById(R.id.switch_notifications);
        locationSwitch = findViewById(R.id.switch_location);
        btnClearCache = findViewById(R.id.btn_clear_cache);
    }

    private void loadSettings() {
        // Load units setting
        boolean isMetric = SharedPrefsUtils.isMetricUnits(this);
        if (isMetric) {
            metricRadioButton.setChecked(true);
        } else {
            imperialRadioButton.setChecked(true);
        }

        // Load notifications setting
        boolean notificationsEnabled = SharedPrefsUtils.isNotificationEnabled(this);
        notificationsSwitch.setChecked(notificationsEnabled);

        // Load location setting
        boolean locationEnabled = SharedPrefsUtils.getBoolean(this, "location_enabled", true);
        locationSwitch.setChecked(locationEnabled);
    }

    private void setupListeners() {
        unitsRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_metric) {
                SharedPrefsUtils.saveUnitSystem(SettingsActivity.this, true);
                markRefreshNeeded();
            } else if (checkedId == R.id.radio_imperial) {
                SharedPrefsUtils.saveUnitSystem(SettingsActivity.this, false);
                markRefreshNeeded();
            }
        });

        notificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPrefsUtils.saveNotificationEnabled(SettingsActivity.this, isChecked);
            // Implement notification logic here or in a service
        });

        locationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPrefsUtils.putBoolean(SettingsActivity.this, "location_enabled", isChecked);
        });

        btnClearCache.setOnClickListener(v -> {
            // Clear cached weather data
            SharedPrefsUtils.putString(SettingsActivity.this, "cached_weather", null);
            Toast.makeText(SettingsActivity.this, "Cache cleared", Toast.LENGTH_SHORT).show();
            markRefreshNeeded();
        });
    }

    private void markRefreshNeeded() {
        // Mark that data needs to be refreshed on main activity
        SharedPrefsUtils.putBoolean(this, "refresh_needed", true);
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