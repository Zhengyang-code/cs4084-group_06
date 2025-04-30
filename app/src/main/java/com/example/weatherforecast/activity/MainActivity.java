package com.example.weatherforecast.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.weatherforecast.R;
import com.example.weatherforecast.adapters.WeatherViewPagerAdapter;
import com.example.weatherforecast.models.City;
import com.example.weatherforecast.models.CurrentWeather;
import com.example.weatherforecast.network.APIClient;
import com.example.weatherforecast.network.WeatherService;
import com.example.weatherforecast.utils.LocationUtils;
import com.example.weatherforecast.utils.SharedPrefsUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private static final int CITY_MANAGE_REQUEST_CODE = 1002;

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private FloatingActionButton fabRefresh;
    private ProgressBar progressBar;
    private TextView tvCityName;
    private ImageView ivSettings;
    private ImageView ivCityList;

    private WeatherViewPagerAdapter pagerAdapter;
    private WeatherService weatherService;
    private LocationUtils locationUtils;
    private List<City> savedCities;
    private City currentCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupToolbar();
        initServices();
        checkLocationPermission();
        setupViewPager();
        setupListeners();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        fabRefresh = findViewById(R.id.fab_refresh);
        progressBar = findViewById(R.id.progress_bar);
        tvCityName = findViewById(R.id.tv_city_name);
        ivSettings = findViewById(R.id.iv_settings);
        ivCityList = findViewById(R.id.iv_city_list);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }


    private void initServices() {
        weatherService = APIClient.getClient().create(WeatherService.class);
        locationUtils = new LocationUtils(this);
        savedCities = SharedPrefsUtils.getSavedCities(this);

        if (savedCities == null) {
            savedCities = new ArrayList<>();
        }
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getCurrentLocation();
        }
    }

    private void getCurrentLocation() {
        showProgress(true);
        locationUtils.getCurrentLocation(new LocationUtils.LocationResultListener() {
            @Override
            public void onLocationResult(Location location) {
                if (location != null) {
                    fetchWeatherForLocation(location.getLatitude(), location.getLongitude());
                } else {
                    // Use default location or last saved location
                    City lastCity = SharedPrefsUtils.getLastLocation(MainActivity.this);
                    if (lastCity != null) {
                        currentCity = lastCity;
                        fetchWeatherForCity(currentCity);
                    } else if (!savedCities.isEmpty()) {
                        currentCity = savedCities.get(0);
                        fetchWeatherForCity(currentCity);
                    } else {
                        // Default to a known location if no saved city
                        fetchWeatherForLocation(40.7128, -74.0060); // New York
                        Toast.makeText(MainActivity.this, "Unable to get your location. Showing default city.",
                                Toast.LENGTH_LONG).show();
                    }
                }
                showProgress(false); // 不要忘了加载结束时隐藏ProgressBar
            }

            @Override
            public void onLocationFailure(Exception e) {
                Toast.makeText(MainActivity.this, "Failed to get location: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                showProgress(false);
            }
        });
    }


    private void fetchWeatherForLocation(double latitude, double longitude) {
        String apiKey = getString(R.string.weather_api_key);
        String location = latitude + "," + longitude;

        Call<CurrentWeather> call = weatherService.getCurrentWeather(
                location, apiKey, "current,daily,hourly,alerts", "metric", "en");

        call.enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(@NonNull Call<CurrentWeather> call, @NonNull Response<CurrentWeather> response) {
                showProgress(false);
                if (response.isSuccessful() && response.body() != null) {
                    CurrentWeather weatherData = response.body();
                    updateUI(weatherData);

                    // Cache the weather data
                    cacheWeatherData(weatherData);

                    // Get city info from coordinates
                    String cityName = weatherData.getLocationName();
                    if (cityName != null && !cityName.isEmpty()) {
                        currentCity = new City(cityName, latitude, longitude);

                        // Save current location
                        SharedPrefsUtils.saveLastLocation(MainActivity.this, currentCity);

                        // Add to saved cities if not already there
                        if (!isCityInSavedList(currentCity)) {
                            savedCities.add(0, currentCity);
                            SharedPrefsUtils.saveCities(MainActivity.this, savedCities);
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Error fetching weather data",
                            Toast.LENGTH_SHORT).show();
                    loadCachedWeatherData();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CurrentWeather> call, @NonNull Throwable t) {
                showProgress(false);
                Toast.makeText(MainActivity.this, "Network error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
                loadCachedWeatherData();
            }
        });
    }

    private void fetchWeatherForCity(City city) {
        Log.d("MainActivity", "Fetching weather for city: " + city.getName() + ", lat: " + city.getLatitude() + ", lon: " + city.getLongitude());

        // 使用城市的经纬度
        fetchWeatherForLocation(city.getLatitude(), city.getLongitude());
    }

    private boolean isCityInSavedList(City city) {
        for (City savedCity : savedCities) {
            if (savedCity.getName().equals(city.getName())) {
                return true;
            }
        }
        return false;
    }

    private void updateUI(CurrentWeather weatherData) {
        tvCityName.setText(weatherData.getLocationName());
        // Update fragments with weather data
        if (pagerAdapter != null) {
            pagerAdapter.updateWeatherData(weatherData);
        }
    }

    private void cacheWeatherData(CurrentWeather weatherData) {
        // Cache weather data for offline use
        if (weatherData != null) {
            String weatherJson = new Gson().toJson(weatherData);
            SharedPrefsUtils.getPrefs(this)
                    .edit()
                    .putString("cached_weather", weatherJson)
                    .apply();
        }
    }

    private void loadCachedWeatherData() {
        // Load cached weather data if available
        String cachedWeatherJson = SharedPrefsUtils.getPrefs(this).getString("cached_weather", null);
        if (cachedWeatherJson != null) {
            try {
                CurrentWeather cachedWeather = new Gson().fromJson(cachedWeatherJson, CurrentWeather.class);
                updateUI(cachedWeather);
                Toast.makeText(this, "Showing cached weather data", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setupViewPager() {
        pagerAdapter = new WeatherViewPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText(R.string.tab_current);
                    break;
                case 1:
                    tab.setText(R.string.tab_hourly);
                    break;
                case 2:
                    tab.setText(R.string.tab_daily);
                    break;
                case 3:
                    tab.setText(R.string.tab_details);
                    break;
            }
        }).attach();
    }

    private void setupListeners() {
        fabRefresh.setOnClickListener(v -> {
            if (currentCity != null) {
                fetchWeatherForCity(currentCity);
            } else {
                getCurrentLocation();
            }
        });

        ivSettings.setOnClickListener(v -> {
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        });

        ivCityList.setOnClickListener(v -> {
            Intent cityIntent = new Intent(MainActivity.this, CityManageActivity.class);
            startActivityForResult(cityIntent, CITY_MANAGE_REQUEST_CODE);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Location permission denied. Using saved or default location.",
                        Toast.LENGTH_SHORT).show();

                // Use saved location or default
                City lastCity = SharedPrefsUtils.getLastLocation(this);
                if (lastCity != null) {
                    currentCity = lastCity;
                    fetchWeatherForCity(currentCity);
                } else if (!savedCities.isEmpty()) {
                    currentCity = savedCities.get(0);
                    fetchWeatherForCity(currentCity);
                } else {
                    // Default to a known location if no saved city
                    fetchWeatherForLocation(40.7128, -74.0060); // New York
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(searchIntent);
            return true;
        } else if (id == R.id.action_map) {
            // 由于不确定您是否有WeatherMapActivity，先注释掉
            // Intent mapIntent = new Intent(MainActivity.this, WeatherMapActivity.class);
            // if (currentCity != null) {
            //     mapIntent.putExtra("latitude", currentCity.getLatitude());
            //     mapIntent.putExtra("longitude", currentCity.getLongitude());
            // }
            // startActivity(mapIntent);
            Toast.makeText(this, "Map feature coming soon!", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_share) {
            shareWeatherInfo();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareWeatherInfo() {
        if (pagerAdapter != null && pagerAdapter.getCurrentWeatherData() != null) {
            CurrentWeather weather = pagerAdapter.getCurrentWeatherData();
            String shareText = "Weather in " + weather.getLocationName() + ": " +
                    weather.getCurrent().getTemperature() + "°C, " +
                    weather.getCurrent().getConditions() + "\n" +
                    "Check out this forecast from my Weather App!";

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            shareIntent.setType("text/plain");
            startActivity(Intent.createChooser(shareIntent, "Share weather via"));
        }
    }

    private void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("MainActivity", "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == CITY_MANAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("selected_city")) {
                City selectedCity = (City) data.getSerializableExtra("selected_city");
                if (selectedCity != null) {
                    Log.d("MainActivity", "Selected city: " + selectedCity.getName());

                    // 保存为当前城市
                    currentCity = selectedCity;

                    // 更新UI以显示正在加载
                    showProgress(true);

                    // 为选定城市获取天气数据
                    fetchWeatherForCity(currentCity);

                    // 也可以将城市添加到保存列表
                    if (!isCityInSavedList(currentCity)) {
                        savedCities.add(currentCity);
                        SharedPrefsUtils.saveCities(MainActivity.this, savedCities);
                    }
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Refresh data if needed based on settings
        boolean refreshNeeded = SharedPrefsUtils.getPrefs(this).getBoolean("refresh_needed", false);
        if (refreshNeeded) {
            if (currentCity != null) {
                fetchWeatherForCity(currentCity);
            }
            SharedPrefsUtils.getPrefs(this).edit().putBoolean("refresh_needed", false).apply();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationUtils != null) {
            locationUtils.stopLocationUpdates();
        }
    }
}