package com.example.weatherforecast.models;

public class WeatherResponse {
    public CurrentConditions currentConditions;
    public String resolvedAddress;

    // 创建一个内部静态类 CurrentConditions，避免报错
    public static class CurrentConditions {
        public String datetime;
        public double temp;
        public String conditions;
        public int humidity;
        public double windspeed;
    }
}
