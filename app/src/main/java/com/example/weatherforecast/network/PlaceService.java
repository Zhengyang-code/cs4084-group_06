package com.example.weatherforecast.network;

import com.example.weatherforecast.WeatherApplication;
import com.example.weatherforecast.models.PlaceResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlaceService {
    @GET("v2/place?token=" + WeatherApplication.TOKEN + "&lang=zh_CN")
    Call<PlaceResponse> searchPlaces(@Query("query") String query);
} 