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
import com.example.weatherforecast.models.DailyForecast;

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
        if (weatherData == null || weatherData.getDailyForecasts() == null || weatherData.getDailyForecasts().isEmpty()) {
            showNoData(true);
            return;
        }

        // 从第一天获取详细信息
        DailyForecast currentDay = weatherData.getDailyForecasts().get(0);

        // Show AQI if available (这部分可以保持不变，因为AirQuality依然来自weatherData对象)
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

        // Populate detailed weather information using currentDay instead of current
        List<WeatherDetailAdapter.WeatherDetail> details = createDetailsList(currentDay);
        adapter.updateData(details);

        showNoData(false);
    }


    // 调整createDetailsList方法接收DailyForecast而非CurrentConditions
    private List<WeatherDetailAdapter.WeatherDetail> createDetailsList(DailyForecast currentDay) {
        List<WeatherDetailAdapter.WeatherDetail> details = new ArrayList<>();

        // Humidity
        details.add(new WeatherDetailAdapter.WeatherDetail(
                R.drawable.ic_humidity,
                "Humidity",
                String.format("%.0f%%", currentDay.getHumidity())));

        // Dew Point
        details.add(new WeatherDetailAdapter.WeatherDetail(
                R.drawable.ic_dew_point,
                "Dew Point",
                String.format("%.1f°", currentDay.getDewPoint())));

        // Pressure
        details.add(new WeatherDetailAdapter.WeatherDetail(
                R.drawable.ic_pressure,
                "Pressure",
                String.format("%.0f hPa", currentDay.getPressure())));

        // Wind Speed
        details.add(new WeatherDetailAdapter.WeatherDetail(
                R.drawable.ic_wind,
                "Wind Speed",
                String.format("%.1f km/h", currentDay.getWindSpeed())));

        // Wind Direction
        details.add(new WeatherDetailAdapter.WeatherDetail(
                R.drawable.ic_wind,
                "Wind Direction",
                String.format("%.0f°", currentDay.getWindDirection())));

        // UV Index
        details.add(new WeatherDetailAdapter.WeatherDetail(
                R.drawable.ic_uv_index,
                "UV Index",
                String.format("%.0f", currentDay.getUvIndex())));

        // Visibility
        details.add(new WeatherDetailAdapter.WeatherDetail(
                R.drawable.ic_visibility,
                "Visibility",
                String.format("%.1f km", currentDay.getVisibility())));

        // Cloud Cover
        details.add(new WeatherDetailAdapter.WeatherDetail(
                R.drawable.ic_cloud,
                "Cloud Cover",
                String.format("%.0f%%", currentDay.getCloudCover())));

        // Precipitation
        details.add(new WeatherDetailAdapter.WeatherDetail(
                R.drawable.ic_precipitation,
                "Precipitation",
                String.format("%.1f mm", currentDay.getPrecipitation())));

        // Precipitation Chance
        details.add(new WeatherDetailAdapter.WeatherDetail(
                R.drawable.ic_precipitation,
                "Precipitation Chance",
                String.format("%.0f%%", currentDay.getPrecipitationProbability())));

        return details;
    }

    private void showNoData(boolean show) {
        if (recyclerView != null && tvNoData != null) {
            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            tvNoData.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
}