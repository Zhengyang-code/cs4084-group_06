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
import com.example.weatherforecast.adapters.HourlyForecastAdapter;
import com.example.weatherforecast.models.CurrentWeather;
import com.example.weatherforecast.models.DailyForecast;
import com.example.weatherforecast.models.HourlyForecast;

import java.util.ArrayList;
import java.util.List;

public class HourlyForecastFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView tvNoData;
    private HourlyForecastAdapter adapter;
    private List<HourlyForecast> hourlyForecasts;

    public HourlyForecastFragment() {
        // Required empty public constructor
    }

    public static HourlyForecastFragment newInstance() {
        return new HourlyForecastFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hourly_forecast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_hourly_forecast);
        tvNoData = view.findViewById(R.id.tv_no_data);

        // Initialize RecyclerView
        hourlyForecasts = new ArrayList<>();
        adapter = new HourlyForecastAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }

    /**
     * Update the fragment with weather data
     * @param weatherData The current weather data containing hourly forecasts
     */
    public void updateWeatherData(CurrentWeather weatherData) {
        if (weatherData == null || weatherData.getDailyForecasts() == null ||
                weatherData.getDailyForecasts().isEmpty() ||
                weatherData.getDailyForecasts().get(0).getHours() == null) {

            showNoData(true);
            return;
        }

        // Get hourly forecasts from the first day
        DailyForecast today = weatherData.getDailyForecasts().get(0);
        List<HourlyForecast> hourlyData = today.getHours();

        if (hourlyData != null && !hourlyData.isEmpty()) {
            adapter.updateData(hourlyData);
            showNoData(false);
        } else {
            showNoData(true);
        }
    }

    private void showNoData(boolean show) {
        if (recyclerView != null && tvNoData != null) {
            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            tvNoData.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
}