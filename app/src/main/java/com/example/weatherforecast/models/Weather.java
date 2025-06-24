package com.example.weatherforecast.models;

public class Weather {
    private RealtimeWeather realtime;
    private DailyWeather daily;

    public Weather(RealtimeWeather realtime, DailyWeather daily) {
        this.realtime = realtime;
        this.daily = daily;
    }

    public RealtimeWeather getRealtime() {
        return realtime;
    }

    public void setRealtime(RealtimeWeather realtime) {
        this.realtime = realtime;
    }

    public DailyWeather getDaily() {
        return daily;
    }

    public void setDaily(DailyWeather daily) {
        this.daily = daily;
    }
} 