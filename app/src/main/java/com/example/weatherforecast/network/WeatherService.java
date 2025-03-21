package com.example.weatherforecast.network;

import com.example.weatherforecast.models.CurrentWeather;
import com.example.weatherforecast.models.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    /**
     * Get current weather and forecast data for a location
     * Using Visual Crossing Weather API
     *
     * @param location Location name or lat,lon coordinates
     * @param key API key
     * @param include Sections to include (current,daily,hourly,alerts)
     * @param unitGroup Unit group (metric or us)
     * @param lang Language code (en, es, etc.)
     * @return Call object with CurrentWeather response
     */
    @GET("timeline")
    Call<CurrentWeather> getCurrentWeather(
            @Query("location") String location,
            @Query("key") String key,
            @Query("include") String include,
            @Query("unitGroup") String unitGroup,
            @Query("lang") String lang
    );

    /**
     * Get forecast for multiple days
     *
     * @param location Location name or lat,lon coordinates
     * @param startDate Start date (YYYY-MM-DD)
     * @param endDate End date (YYYY-MM-DD)
     * @param key API key
     * @param include Sections to include
     * @param unitGroup Unit group (metric or us)
     * @param lang Language code (en, es, etc.)
     * @return Call object with CurrentWeather response
     */
    @GET("timeline")
    Call<CurrentWeather> getForecast(
            @Query("location") String location,
            @Query("startDate") String startDate,
            @Query("endDate") String endDate,
            @Query("key") String key,
            @Query("include") String include,
            @Query("unitGroup") String unitGroup,
            @Query("lang") String lang
    );

    /**
     * Search for cities by name
     *
     * @param query Search query
     * @param key API key
     * @return Call object with SearchResponse containing city results
     */
    @GET("search")
    Call<SearchResponse> searchCity(
            @Query("query") String query,
            @Query("key") String key
    );
}
