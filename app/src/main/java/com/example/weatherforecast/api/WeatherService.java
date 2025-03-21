package com.example.weatherforecast.api;

import com.example.weatherforecast.models.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherService {

    // 获取当前及未来天气预报
    @GET("VisualCrossingWebServices/rest/services/timeline/{location}")
    Call<WeatherResponse> getWeather(
            @Path("location") String location,
            @Query("key") String apiKey
    );
}
