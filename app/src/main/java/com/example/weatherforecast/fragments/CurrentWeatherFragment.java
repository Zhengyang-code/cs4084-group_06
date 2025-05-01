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
            // 将CurrentWeather对象转换为JSON字符串
            String jsonString = new Gson().toJson(weatherData);

            // 将JSON字符串解析为JsonObject
            JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

            // 检查是否有days数组
            if (!jsonObject.has("days") || jsonObject.getAsJsonArray("days").size() == 0) {
                showNoData(true);
                return;
            }

            showNoData(false);

            // 获取第一天的数据
            JsonObject currentDay = jsonObject.getAsJsonArray("days").get(0).getAsJsonObject();

            // 更新UI - 温度
            if (currentDay.has("temp")) {
                double temp = currentDay.get("temp").getAsDouble();
                tvTemperature.setText(String.format("%.0f°", temp));
            }

            // 天气状况
            if (currentDay.has("conditions")) {
                String conditions = currentDay.get("conditions").getAsString();
                tvCondition.setText(conditions);
            }

            // 天气图标
            if (currentDay.has("icon")) {
                String icon = currentDay.get("icon").getAsString();
                int iconResId = IconUtils.getWeatherIconResource(icon);
                ivWeatherIcon.setImageResource(iconResId);
            }

            // 高低温
            double tempMax = currentDay.has("tempmax") ? currentDay.get("tempmax").getAsDouble() : 0;
            double tempMin = currentDay.has("tempmin") ? currentDay.get("tempmin").getAsDouble() : 0;
            tvHighLow.setText(String.format("H: %.0f° L: %.0f°", tempMax, tempMin));

            // 体感温度
            if (currentDay.has("feelslike")) {
                double feelslike = currentDay.get("feelslike").getAsDouble();
                tvFeelsLike.setText(String.format("%.0f°", feelslike));
            }

            // 湿度
            if (currentDay.has("humidity")) {
                double humidity = currentDay.get("humidity").getAsDouble();
                tvHumidity.setText(String.format("%.0f%%", humidity));
            }

            // 风速
            if (currentDay.has("windspeed")) {
                double windspeed = currentDay.get("windspeed").getAsDouble();
                tvWindSpeed.setText(String.format("%.1f km/h", windspeed));
            }

            // 气压
            if (currentDay.has("pressure")) {
                double pressure = currentDay.get("pressure").getAsDouble();
                tvPressure.setText(String.format("%.0f hPa", pressure));
            }

            // 能见度
            if (currentDay.has("visibility")) {
                double visibility = currentDay.get("visibility").getAsDouble();
                tvVisibility.setText(String.format("%.1f km", visibility));
            }

            // 日出时间
            if (currentDay.has("sunrise")) {
                String sunrise = currentDay.get("sunrise").getAsString();
                tvSunrise.setText(DateTimeUtils.formatTime(sunrise));
            }

            // 日落时间
            if (currentDay.has("sunset")) {
                String sunset = currentDay.get("sunset").getAsString();
                tvSunset.setText(DateTimeUtils.formatTime(sunset));
            }

            // 更新时间
            if (currentDay.has("datetimeEpoch")) {
                long datetimeEpoch = currentDay.get("datetimeEpoch").getAsLong();
                // 确保时间单位正确（毫秒）
                tvLastUpdated.setText("Updated " + android.text.format.DateUtils.getRelativeTimeSpanString(
                        datetimeEpoch * 1000, // 如果datetimeEpoch是秒，转换为毫秒
                        System.currentTimeMillis(),
                        android.text.format.DateUtils.MINUTE_IN_MILLIS
                ));
            }



            // 更新天气详情列表
            List<WeatherDetailAdapter.WeatherDetail> details = new ArrayList<>();

            // 湿度
            if (currentDay.has("humidity")) {
                double humidity = currentDay.get("humidity").getAsDouble();
                details.add(new WeatherDetailAdapter.WeatherDetail(
                        R.drawable.ic_humidity, "Humidity",
                        String.format("%.0f%%", humidity)));
            }

            // 风速
            if (currentDay.has("windspeed")) {
                double windspeed = currentDay.get("windspeed").getAsDouble();
                details.add(new WeatherDetailAdapter.WeatherDetail(
                        R.drawable.ic_wind, "Wind",
                        String.format("%.1f km/h", windspeed)));
            }

            // 气压
            if (currentDay.has("pressure")) {
                double pressure = currentDay.get("pressure").getAsDouble();
                details.add(new WeatherDetailAdapter.WeatherDetail(
                        R.drawable.ic_pressure, "Pressure",
                        String.format("%.0f hPa", pressure)));
            }

            // 能见度
            if (currentDay.has("visibility")) {
                double visibility = currentDay.get("visibility").getAsDouble();
                details.add(new WeatherDetailAdapter.WeatherDetail(
                        R.drawable.ic_visibility, "Visibility",
                        String.format("%.1f km", visibility)));
            }

            // 露点
            if (currentDay.has("dew")) {
                double dew = currentDay.get("dew").getAsDouble();
                details.add(new WeatherDetailAdapter.WeatherDetail(
                        R.drawable.ic_dew_point, "Dew Point",
                        String.format("%.0f°", dew)));
            }

            // 紫外线指数
            if (currentDay.has("uvindex")) {
                double uvindex = currentDay.get("uvindex").getAsDouble();
                details.add(new WeatherDetailAdapter.WeatherDetail(
                        R.drawable.ic_uv_index, "UV Index",
                        String.format("%.0f", uvindex)));
            }

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