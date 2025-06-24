package com.example.weatherforecast.network;

import com.example.weatherforecast.WeatherApplication;
import com.example.weatherforecast.models.PlaceResponse;
import com.example.weatherforecast.models.WeatherResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherNetwork {
    private static final PlaceService placeService = ServiceCreator.create(PlaceService.class);
    private static final WeatherService weatherService = ServiceCreator.create(WeatherService.class);

    public static void searchPlaces(String query, Callback<PlaceResponse> callback) {
        placeService.searchPlaces(query).enqueue(callback);
    }
    
    public static void getWeather(String lng, String lat, Callback<WeatherResponse> callback) {
        weatherService.getWeather(WeatherApplication.TOKEN, lng, lat).enqueue(callback);
    }
} 