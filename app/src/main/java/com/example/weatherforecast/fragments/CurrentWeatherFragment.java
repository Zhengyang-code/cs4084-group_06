package com.example.weatherforecast.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import java.lang.reflect.Field;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.example.weatherforecast.models.DailyForecast;

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

        // 检查weatherData是否为null
        if (weatherData == null) {
            showNoData(true);
            return;
        }

        try {
            // 检查是否有days数组
            if (weatherData.getDailyForecasts() == null || weatherData.getDailyForecasts().isEmpty()) {
                showNoData(true);
                return;
            }

            showNoData(false);

            // 获取第一天的数据
            DailyForecast currentDay = weatherData.getDailyForecasts().get(0);

            // 更新UI - 温度
            tvTemperature.setText(String.format("%.0f°", currentDay.getTemperature()));

            // 天气状况
            tvCondition.setText(currentDay.getConditions());

            // 天气图标
            int iconResId = IconUtils.getWeatherIconResource(currentDay.getIcon());
            ivWeatherIcon.setImageResource(iconResId);

            // 高低温
            tvHighLow.setText(String.format("H: %.0f° L: %.0f°", currentDay.getTempMax(), currentDay.getTempMin()));

            // 体感温度
            tvFeelsLike.setText(String.format("%.0f°", currentDay.getFeelsLike()));

            // 湿度
            tvHumidity.setText(String.format("%.0f%%", currentDay.getHumidity()));

            // 风速
            tvWindSpeed.setText(String.format("%.1f km/h", currentDay.getWindSpeed()));

            // 气压
            tvPressure.setText(String.format("%.0f hPa", currentDay.getPressure()));

            // 能见度
            tvVisibility.setText(String.format("%.1f km", currentDay.getVisibility()));

            // 日出时间
            tvSunrise.setText(DateTimeUtils.formatTime(currentDay.getSunrise()));

            // 日落时间
            tvSunset.setText(DateTimeUtils.formatTime(currentDay.getSunset()));

            // 更新时间
            if (currentDay.getDate() != null) {
                // 使用DateTimeUtils或直接使用Android的工具类格式化时间
                tvLastUpdated.setText("Updated " + android.text.format.DateUtils.getRelativeTimeSpanString(
                        System.currentTimeMillis(), // 当前时间
                        System.currentTimeMillis(), // 假设刚刚更新
                        android.text.format.DateUtils.MINUTE_IN_MILLIS
                ));
            }

            // 更新天气详情列表
            List<WeatherDetailAdapter.WeatherDetail> details = new ArrayList<>();

            // 湿度
            details.add(new WeatherDetailAdapter.WeatherDetail(
                    R.drawable.ic_humidity, "Humidity",
                    String.format("%.0f%%", currentDay.getHumidity())));

            // 风速
            details.add(new WeatherDetailAdapter.WeatherDetail(
                    R.drawable.ic_wind, "Wind",
                    String.format("%.1f km/h", currentDay.getWindSpeed())));

            // 气压
            details.add(new WeatherDetailAdapter.WeatherDetail(
                    R.drawable.ic_pressure, "Pressure",
                    String.format("%.0f hPa", currentDay.getPressure())));

            // 能见度
            details.add(new WeatherDetailAdapter.WeatherDetail(
                    R.drawable.ic_visibility, "Visibility",
                    String.format("%.1f km", currentDay.getVisibility())));

            // 露点
            details.add(new WeatherDetailAdapter.WeatherDetail(
                    R.drawable.ic_dew_point, "Dew Point",
                    String.format("%.0f°", currentDay.getDewPoint())));

            // 紫外线指数
            details.add(new WeatherDetailAdapter.WeatherDetail(
                    R.drawable.ic_uv_index, "UV Index",
                    String.format("%.0f", currentDay.getUvIndex())));

            detailAdapter.updateData(details);

            // 隐藏警报视图
            alertView.setVisibility(View.GONE);

        } catch (Exception e) {
            Log.e("CurrentWeatherFragment", "Error updating weather data", e);
            showNoData(true);
        }
    }





    private void showNoData(boolean show) {
        if (getView() != null) {
            getView().findViewById(R.id.content_layout).setVisibility(show ? View.GONE : View.VISIBLE);
            tvNoData.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
}