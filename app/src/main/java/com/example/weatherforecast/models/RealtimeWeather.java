package com.example.weatherforecast.models;

public class RealtimeWeather {
    private double temperature;
    private String skycon;
    private AirQuality airQuality;

    public RealtimeWeather(double temperature, String skycon, AirQuality airQuality) {
        this.temperature = temperature;
        this.skycon = skycon;
        this.airQuality = airQuality;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getSkycon() {
        return skycon;
    }

    public void setSkycon(String skycon) {
        this.skycon = skycon;
    }

    public AirQuality getAirQuality() {
        return airQuality;
    }

    public void setAirQuality(AirQuality airQuality) {
        this.airQuality = airQuality;
    }
} 