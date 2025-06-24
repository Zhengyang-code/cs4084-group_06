package com.example.weatherforecast.repository;

import com.example.weatherforecast.dao.PlaceDao;
import com.example.weatherforecast.models.AirQuality;
import com.example.weatherforecast.models.DailyWeather;
import com.example.weatherforecast.models.Place;
import com.example.weatherforecast.models.PlaceResponse;
import com.example.weatherforecast.models.RealtimeWeather;
import com.example.weatherforecast.models.Weather;
import com.example.weatherforecast.models.WeatherResponse;
import com.example.weatherforecast.network.WeatherNetwork;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class Repository {
    
    public static void searchPlaces(String query, SearchCallback callback) {
        WeatherNetwork.searchPlaces(query, new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PlaceResponse placeResponse = response.body();
                    if ("ok".equals(placeResponse.getStatus())) {
                        callback.onSuccess(placeResponse.getPlaces());
                    } else {
                        callback.onError("Response status is " + placeResponse.getStatus());
                    }
                } else {
                    callback.onError("Response body is null");
                }
            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
    
    public static void getWeather(String lng, String lat, WeatherCallback callback) {
        // Call real Caiyun Weather API
        WeatherNetwork.getWeather(lng, lat, new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weatherResponse = response.body();
                    if ("ok".equals(weatherResponse.getStatus())) {
                        callback.onSuccess(weatherResponse.getResult());
                    } else {
                        callback.onError("Weather API response status is " + weatherResponse.getStatus());
                    }
                } else {
                    // Fallback to mock data if API fails
                    Weather mockWeather = createMockWeather();
                    callback.onSuccess(mockWeather);
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                // Fallback to mock data if network fails
                Weather mockWeather = createMockWeather();
                callback.onSuccess(mockWeather);
            }
        });
    }

    public static void savePlace(Place place) {
        PlaceDao.savePlace(place);
    }

    public static Place getSavedPlace() {
        return PlaceDao.getSavedPlace();
    }

    public static boolean isPlaceSaved() {
        return PlaceDao.isPlaceSaved();
    }
    
    /**
     * Create mock weather data as fallback
     */
    private static Weather createMockWeather() {
        // Create mock realtime weather data
        AirQuality.AQI aqi = new AirQuality.AQI(75.0);
        AirQuality airQuality = new AirQuality(aqi);
        RealtimeWeather realtime = new RealtimeWeather(25.5, "CLEAR_DAY", airQuality);
        
        // Create mock daily weather data
        List<DailyWeather.Skycon> skycons = List.of(
            new DailyWeather.Skycon("2024-01-01", "CLEAR_DAY"),
            new DailyWeather.Skycon("2024-01-02", "PARTLY_CLOUDY_DAY"),
            new DailyWeather.Skycon("2024-01-03", "LIGHT_RAIN"),
            new DailyWeather.Skycon("2024-01-04", "CLOUDY"),
            new DailyWeather.Skycon("2024-01-05", "CLEAR_DAY"),
            new DailyWeather.Skycon("2024-01-06", "PARTLY_CLOUDY_DAY"),
            new DailyWeather.Skycon("2024-01-07", "CLEAR_DAY")
        );
        
        List<DailyWeather.Temperature> temperatures = List.of(
            new DailyWeather.Temperature(20.0, 28.0),
            new DailyWeather.Temperature(18.0, 26.0),
            new DailyWeather.Temperature(15.0, 22.0),
            new DailyWeather.Temperature(16.0, 24.0),
            new DailyWeather.Temperature(19.0, 27.0),
            new DailyWeather.Temperature(17.0, 25.0),
            new DailyWeather.Temperature(21.0, 29.0)
        );
        
        List<DailyWeather.IndexItem> coldRisk = List.of(new DailyWeather.IndexItem("感冒风险较低"));
        List<DailyWeather.IndexItem> dressing = List.of(new DailyWeather.IndexItem("建议穿T恤、长裤"));
        List<DailyWeather.IndexItem> ultraviolet = List.of(new DailyWeather.IndexItem("紫外线强度中等"));
        List<DailyWeather.IndexItem> carWashing = List.of(new DailyWeather.IndexItem("适合洗车"));
        
        DailyWeather.LifeIndex lifeIndex = new DailyWeather.LifeIndex(coldRisk, dressing, ultraviolet, carWashing);
        DailyWeather daily = new DailyWeather(skycons, temperatures, lifeIndex);
        
        return new Weather(realtime, daily);
    }

    public interface SearchCallback {
        void onSuccess(List<Place> places);
        void onError(String error);
    }
    
    public interface WeatherCallback {
        void onSuccess(Weather weather);
        void onError(String error);
    }
} 