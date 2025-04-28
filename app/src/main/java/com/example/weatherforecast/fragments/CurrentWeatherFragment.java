package com.example.weatherforecast.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforecast.R;
import com.example.weatherforecast.activity.WeatherAlertActivity;
import com.example.weatherforecast.adapters.WeatherDetailAdapter;
import com.example.weatherforecast.models.CurrentWeather;
import com.example.weatherforecast.models.WeatherAlert;
import com.example.weatherforecast.utils.DateTimeUtils;
import com.example.weatherforecast.utils.IconUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment to display current weather conditions
 */
public class CurrentWeatherFragment extends Fragment {

    private TextView tvTemperature;
    private TextView tvCondition;
    private TextView tvHighLow;
    private TextView tvFeelsLike;
    private TextView tvHumidity;
    private TextView tvWindSpeed;
    private TextView tvPressure;
    private TextView tvVisibility;
    private TextView tvSunrise;
    private TextView tvSunset;
    private TextView tvLastUpdated;
    private ImageView ivWeatherIcon;
    private RecyclerView rvWeatherDetails;
    private LinearLayout alertView;
    private TextView tvAlertTitle;
    private ImageView ivAlertIcon;
    private TextView tvNoData;

    private WeatherDetailAdapter detailAdapter;
    private CurrentWeather weatherData;

    public CurrentWeatherFragment() {
        // Required empty public constructor
    }

    /**
     * Create a new instance of the fragment
     * @return A new instance of fragment CurrentWeatherFragment
     */
    public static CurrentWeatherFragment newInstance() {
        return new CurrentWeatherFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupRecyclerView();
        setupClickListeners();
    }

    private void initViews(View view) {
        tvTemperature = view.findViewById(R.id.tv_temperature);
        tvCondition = view.findViewById(R.id.tv_condition);
        tvHighLow = view.findViewById(R.id.tv_high_low);
        tvFeelsLike = view.findViewById(R.id.tv_feels_like);
        tvHumidity = view.findViewById(R.id.tv_humidity);
        tvWindSpeed = view.findViewById(R.id.tv_wind_speed);
        tvPressure = view.findViewById(R.id.tv_pressure);
        tvVisibility = view.findViewById(R.id.tv_visibility);
        tvSunrise = view.findViewById(R.id.tv_sunrise);
        tvSunset = view.findViewById(R.id.tv_sunset);
        tvLastUpdated = view.findViewById(R.id.tv_last_updated);
        ivWeatherIcon = view.findViewById(R.id.iv_weather_icon);
        rvWeatherDetails = view.findViewById(R.id.rv_weather_details);
        alertView = view.findViewById(R.id.alert_view);
        tvAlertTitle = view.findViewById(R.id.tv_alert_title);
        ivAlertIcon = view.findViewById(R.id.iv_alert_icon);
        tvNoData = view.findViewById(R.id.tv_no_data);
    }

    private void setupRecyclerView() {
        detailAdapter = new WeatherDetailAdapter();
        rvWeatherDetails.setLayoutManager(new LinearLayoutManager(getContext()));
        rvWeatherDetails.setAdapter(detailAdapter);
    }

    private void setupClickListeners() {
        alertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (weatherData != null && weatherData.hasAlerts()) {
                    // Open alerts activity
                    Intent alertIntent = new Intent(getActivity(), WeatherAlertActivity.class);
                    alertIntent.putExtra("weather_data", weatherData);
                    startActivity(alertIntent);
                }
            }
        });
    }

    /**
     * Update the UI with new weather data
     * @param weatherData The current weather data
     */
    public void updateWeatherData(CurrentWeather weatherData) {
        this.weatherData = weatherData;

        if (weatherData == null || weatherData.getCurrent() == null) {
            showNoData(true);
            return;
        }

        showNoData(false);

        // Update main weather display
        tvTemperature.setText(String.format("%.0f°", weatherData.getCurrent().getTemperature()));
        tvCondition.setText(weatherData.getCurrent().getConditions());

        // Set icon based on weather condition
        int iconResId = IconUtils.getWeatherIconResource(weatherData.getCurrent().getIcon());
        ivWeatherIcon.setImageResource(iconResId);

        // Show high/low temperatures
        if (weatherData.getDailyForecasts() != null && !weatherData.getDailyForecasts().isEmpty()) {
            double highTemp = weatherData.getDailyForecasts().get(0).getTempMax();
            double lowTemp = weatherData.getDailyForecasts().get(0).getTempMin();
            tvHighLow.setText(String.format("H: %.0f° L: %.0f°", highTemp, lowTemp));
        }

        // Update details
        tvFeelsLike.setText(String.format("%.0f°", weatherData.getCurrent().getFeelsLike()));
        tvHumidity.setText(String.format("%.0f%%", weatherData.getCurrent().getHumidity()));
        tvWindSpeed.setText(String.format("%.1f km/h", weatherData.getCurrent().getWindSpeed()));
        tvPressure.setText(String.format("%.0f hPa", weatherData.getCurrent().getPressure()));
        tvVisibility.setText(String.format("%.1f km", weatherData.getCurrent().getVisibility()));

        // Update sunrise and sunset times
        tvSunrise.setText(DateTimeUtils.formatTime(weatherData.getCurrent().getSunrise()));
        tvSunset.setText(DateTimeUtils.formatTime(weatherData.getCurrent().getSunset()));

        // Update last updated time
        tvLastUpdated.setText(String.format("Updated %s",
                DateTimeUtils.getRelativeTimeSpanString(weatherData.getCurrent().getDateTime())));

        // Update weather details list
        List<WeatherDetailAdapter.WeatherDetail> details = new ArrayList<>();
        details.add(new WeatherDetailAdapter.WeatherDetail(
                R.drawable.ic_humidity, "Humidity",
                String.format("%.0f%%", weatherData.getCurrent().getHumidity())));

        details.add(new WeatherDetailAdapter.WeatherDetail(
                R.drawable.ic_wind, "Wind",
                String.format("%.1f km/h", weatherData.getCurrent().getWindSpeed())));

        details.add(new WeatherDetailAdapter.WeatherDetail(
                R.drawable.ic_pressure, "Pressure",
                String.format("%.0f hPa", weatherData.getCurrent().getPressure())));

        details.add(new WeatherDetailAdapter.WeatherDetail(
                R.drawable.ic_visibility, "Visibility",
                String.format("%.1f km", weatherData.getCurrent().getVisibility())));

        details.add(new WeatherDetailAdapter.WeatherDetail(
                R.drawable.ic_dew_point, "Dew Point",
                String.format("%.0f°", weatherData.getCurrent().getDewPoint())));

        details.add(new WeatherDetailAdapter.WeatherDetail(
                R.drawable.ic_uv_index, "UV Index",
                String.format("%.0f", weatherData.getCurrent().getUvIndex())));

        detailAdapter.updateData(details);

        // Show or hide weather alerts
        if (weatherData.hasAlerts()) {
            WeatherAlert alert = weatherData.getAlerts().get(0);
            tvAlertTitle.setText(alert.getEvent());

            // Set the alert icon color based on severity
            ivAlertIcon.setColorFilter(alert.getAlertColor());
            alertView.setVisibility(View.VISIBLE);
        } else {
            alertView.setVisibility(View.GONE);
        }
    }

    private void showNoData(boolean show) {
        if (getView() != null) {
            getView().findViewById(R.id.content_layout).setVisibility(show ? View.GONE : View.VISIBLE);
            tvNoData.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
}