package com.example.weatherforecast.models;

public class AirQuality {
    private AQI aqi;

    public AirQuality(AQI aqi) {
        this.aqi = aqi;
    }

    public AQI getAqi() {
        return aqi;
    }

    public void setAqi(AQI aqi) {
        this.aqi = aqi;
    }

    public static class AQI {
        private double chn;

        public AQI(double chn) {
            this.chn = chn;
        }

        public double getChn() {
            return chn;
        }

        public void setChn(double chn) {
            this.chn = chn;
        }
    }
} 