package com.example.weatherforecast.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforecast.R;
import com.example.weatherforecast.adapters.WeatherDetailAdapter;
import com.example.weatherforecast.models.AirQuality;
import com.example.weatherforecast.models.CurrentWeather;
import com.example.weatherforecast.models.CurrentWeather.CurrentConditions;

import java.util.ArrayList;
import java.util.List;

public class WeatherDetailsFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView tvAqiValue;
    private TextView tvAqiDescription;
    private TextView tvAqiRecommendation;
    private View aqiContainer;
    private TextView tvNoData;

    private WeatherDetailAdapter adapter;

    public WeatherDetailsFragment() {
        // Required empty public constructor
    }

    public static WeatherDetailsFragment newInstance() {
        return new WeatherDetailsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_weather_details);
        tvAqiValue = view.findViewById(R.id.tv_aqi_value);
        tvAqiDescription = view.findViewById(R.id.tv_aqi_description);
        tvAqiRecommendation = view.findViewById(R.id.tv_aqi_recommendation);
        aqiContainer = view.findViewById(R.id.aqi_container);
        tvNoData = view.findViewById(R.id.tv_no_data);

        // Initialize RecyclerView
        adapter = new WeatherDetailAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    /**
     * Update the fragment with weather data
     * @param weatherData The current weather data
     */
    public void updateWeatherData(CurrentWeather weatherData) {
        if (weatherData == null || weatherData.getCurrent() == null) {
            showNoData(true);
            return;
        }

        // Show AQI if available
        AirQuality airQuality = weatherData.getAirQuality();
        if (airQuality != null) {
            tvAqiValue.setText(String.valueOf(airQuality.getAqiIndex()));
            tvAqiDescription.setText(airQuality.getAqiDescription());
            tvAqiRecommendation.setText(airQuality.getHealthRecommendation());
            aqiContainer.setVisibility(View.VISIBLE);

            // Set AQI color
            int aqiColor = airQuality.getAqiColor();
            tvAqiValue.setTextColor(aqiColor);
        } else {
            aqiContainer.setVisibility(View.GONE);
        }

        // Populate detailed weather information
        List<WeatherDetailAdapter.WeatherDetail> details = createDetailsList(weatherData);
        adapter.updateData(details);

        showNoData(false);
    }

    private List<WeatherDetailAdapter.WeatherDetail> createDetailsList(CurrentWeather weatherData) {
        List<WeatherDetailAdapter.WeatherDetail> details = new ArrayList<>();
        CurrentConditions current = weatherData.getCurrent();

        // Humidity
        details.add(new WeatherDetailAdapter.WeatherDetail(
                R.drawable.ic_humidity,
                "Humidity",
                String.format("%.0f%%", current.getHumidity())));

        // Dew Point
        details.add(new WeatherDetailAdapter.WeatherDetail(
                R.drawable.ic_dew_point,
                "Dew Point",
                String.format("%.1f°", current.getDewPoint())));

        // Pressure
        details.add(new WeatherDetailAdapter.WeatherDetail(
                R.drawable.ic_pressure,
                "Pressure",
                String.format("%.0f hPa", current.getPressure())));

        // Wind
        details.add(new WeatherDetailAdapter.WeatherDetail(
                R.drawable.ic_wind,
                "Wind Speed",
                String.format("%.1f km/h", current.getWindSpeed())));

        // Wind Direction
        details.add(new WeatherDetailAdapter.WeatherDetail(
                R.drawable.ic_wind,
                "Wind Direction",
                String.format("%.0f°", current.getWindDirection())));

        // UV Index
        details.add(new WeatherDetailAdapter.WeatherDetail(
                R.drawable.ic_uv_index,
                "UV Index",
                String.format("%.0f", current.getUvIndex())));

        // Visibility
        details.add(new WeatherDetailAdapter.WeatherDetail(
                R.drawable.ic_visibility,
                "Visibility",
                String.format("%.1f km", current.getVisibility())));

        // Cloud Cover
        details.add(new WeatherDetailAdapter.WeatherDetail(
                R.drawable.ic_cloud,
                "Cloud Cover",
                String.format("%.0f%%", current.getCloudCover())));

        // Precipitation
        details.add(new WeatherDetailAdapter.WeatherDetail(
                R.drawable.ic_precipitation,
                "Precipitation",
                String.format("%.1f mm", current.getPrecipitation())));

        // Precipitation Chance
        details.add(new WeatherDetailAdapter.WeatherDetail(
                R.drawable.ic_precipitation,
                "Precipitation Chance",
                String.format("%.0f%%", current.getPrecipitationProbability())));

        return details;
    }

    private void showNoData(boolean show) {
        if (recyclerView != null && tvNoData != null) {
            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            tvNoData.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
}