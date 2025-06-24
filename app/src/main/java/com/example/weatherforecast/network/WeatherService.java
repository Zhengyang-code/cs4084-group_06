package com.example.weatherforecast.network;

import com.example.weatherforecast.models.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherService {
    @GET("v2.6/{token}/{lng},{lat}/weather?alert=true&dailysteps=7&hourlysteps=24")
    Call<WeatherResponse> getWeather(@Path("token") String token, @Path("lng") String lng, @Path("lat") String lat);
} 