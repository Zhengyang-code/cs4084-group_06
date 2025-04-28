package com.example.weatherforecast.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.weatherforecast.fragments.CurrentWeatherFragment;
import com.example.weatherforecast.fragments.ForecastFragment;
import com.example.weatherforecast.fragments.HourlyForecastFragment;
import com.example.weatherforecast.fragments.WeatherDetailsFragment;
import com.example.weatherforecast.models.CurrentWeather;

public class WeatherViewPagerAdapter extends FragmentStateAdapter {

    private static final int NUM_PAGES = 4;

    private final CurrentWeatherFragment currentWeatherFragment;
    private final HourlyForecastFragment hourlyForecastFragment;
    private final ForecastFragment forecastFragment;
    private final WeatherDetailsFragment weatherDetailsFragment;

    private CurrentWeather currentWeatherData;

    public WeatherViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

        currentWeatherFragment = CurrentWeatherFragment.newInstance();
        hourlyForecastFragment = HourlyForecastFragment.newInstance();
        forecastFragment = ForecastFragment.newInstance();
        weatherDetailsFragment = WeatherDetailsFragment.newInstance();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return currentWeatherFragment;
            case 1:
                return hourlyForecastFragment;
            case 2:
                return forecastFragment;
            case 3:
                return weatherDetailsFragment;
            default:
                return currentWeatherFragment;
        }
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }

    /**
     * Update all fragments with new weather data
     * @param weatherData The current weather data
     */
    public void updateWeatherData(CurrentWeather weatherData) {
        this.currentWeatherData = weatherData;

        currentWeatherFragment.updateWeatherData(weatherData);
        hourlyForecastFragment.updateWeatherData(weatherData);
        forecastFragment.updateWeatherData(weatherData);
        weatherDetailsFragment.updateWeatherData(weatherData);
    }

    /**
     * Get the current weather data
     * @return CurrentWeather object
     */
    public CurrentWeather getCurrentWeatherData() {
        return currentWeatherData;
    }
}
