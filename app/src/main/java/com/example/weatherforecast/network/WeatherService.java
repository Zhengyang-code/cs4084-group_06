package com.example.weatherforecast.network;

import com.example.weatherforecast.models.CurrentWeather;
import com.example.weatherforecast.models.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherService {

    /**
     * 获取当前天气信息
     * @param location 位置 (经纬度格式: "lat,lon" 或城市名)
     * @param apiKey API密钥
     * @param include 包含的数据 (例如: "current,daily,hourly,alerts")
     * @param unitGroup 单位 (例如: "metric", "us")
     * @param lang 语言
     * @return 天气信息
     */
    @GET("timeline/{location}")
    Call<CurrentWeather> getCurrentWeather(
            @Path("location") String location,
            @Query("key") String apiKey,
            @Query("include") String include,
            @Query("unitGroup") String unitGroup,
            @Query("lang") String lang);

    /**
     * 搜索城市
     * @param query 查询字符串
     * @param apiKey API密钥
     * @return 搜索结果
     */
    @GET("timeline/{query}")
    Call<SearchResponse> searchCity(
            @Path("query") String query,
            @Query("key") String apiKey);
}

