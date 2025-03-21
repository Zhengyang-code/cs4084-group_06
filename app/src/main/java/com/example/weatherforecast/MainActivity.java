package com.example.weatherforecast;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherforecast.api.ApiClient;
import com.example.weatherforecast.api.WeatherService;
import com.example.weatherforecast.models.WeatherResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText cityInput;
    private Button getWeatherButton;
    private TextView weatherInfo;

    private WeatherService weatherService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityInput = findViewById(R.id.cityInput);
        getWeatherButton = findViewById(R.id.getWeatherButton);
        weatherInfo = findViewById(R.id.weatherInfo);

        weatherService = ApiClient.getRetrofit().create(WeatherService.class);

        getWeatherButton.setOnClickListener(view -> {
            String city = cityInput.getText().toString().trim();
            if (!city.isEmpty()) {
                getWeather(city);
            } else {
                Toast.makeText(MainActivity.this, "请输入城市名", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getWeather(String city) {
        String apiKey = "你的API_KEY";  // 确保你已正确填写API Key
        Call<WeatherResponse> call = weatherService.getWeather(city, apiKey);

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API_RESPONSE", new Gson().toJson(response.body())); // 调试用
                    displayWeather(response.body());
                } else {
                    weatherInfo.setText("未能获取到天气数据，响应码：" + response.code());
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                weatherInfo.setText("请求失败：" + t.getMessage());
            }
        });
    }

    private void displayWeather(WeatherResponse weather) {
        if (weather.currentConditions == null) {
            weatherInfo.setText("未能获取当前天气信息");
            return;
        }

        WeatherResponse.CurrentConditions current = weather.currentConditions;
        String result = "地点：" + weather.resolvedAddress +
                "\n更新时间：" + current.datetime +
                "\n当前温度：" + current.temp + "℃" +
                "\n天气：" + current.conditions +
                "\n湿度：" + current.humidity + "%" +
                "\n风速：" + current.windspeed + " km/h";
        weatherInfo.setText(result);
    }
}
