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
import com.example.weatherforecast.adapters.DailyForecastAdapter;
import com.example.weatherforecast.models.CurrentWeather;
import com.example.weatherforecast.models.DailyForecast;

import java.util.ArrayList;
import java.util.List;

public class ForecastFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView tvNoData;
    private DailyForecastAdapter adapter;
    private List<DailyForecast> dailyForecasts;

    public ForecastFragment() {
        // Required empty public constructor
    }

    public static ForecastFragment newInstance() {
        return new ForecastFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forecast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_daily_forecast);
        tvNoData = view.findViewById(R.id.tv_no_data);

        // Initialize RecyclerView
        dailyForecasts = new ArrayList<>();
        adapter = new DailyForecastAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Set click listener
        adapter.setOnItemClickListener((forecast, position) -> {
            // Show detailed forecast for the day if needed
            // You could open a new activity or show a bottom sheet with details
        });
    }

    /**
     * Update the fragment with weather data
     * @param weatherData The current weather data containing daily forecasts
     */
    public void updateWeatherData(CurrentWeather weatherData) {
        if (weatherData == null || weatherData.getDailyForecasts() == null ||
                weatherData.getDailyForecasts().isEmpty()) {

            showNoData(true);
            return;
        }

        List<DailyForecast> forecastData = weatherData.getDailyForecasts();

        if (!forecastData.isEmpty()) {
            adapter.updateData(forecastData);
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
