package com.example.weatherforecast.model;

import java.util.List;

public class WeatherResponse {
    public String resolvedAddress;
    public String timezone;
    public CurrentConditions currentConditions;
    public List<Days> days;

    public static class CurrentConditions {
        public String datetime;
        public double temp;
        public String conditions;
        public double humidity;
        public double windspeed;
    }

    public static class Days {
        public String datetime;
        public double tempmax;
        public double tempmin;
        public String conditions;
    }
}

