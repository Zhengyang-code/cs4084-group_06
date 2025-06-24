package com.example.weatherforecast.models;

import java.util.List;

public class DailyWeather {
    private List<Skycon> skycon;
    private List<Temperature> temperature;
    private LifeIndex lifeIndex;

    public DailyWeather(List<Skycon> skycon, List<Temperature> temperature, LifeIndex lifeIndex) {
        this.skycon = skycon;
        this.temperature = temperature;
        this.lifeIndex = lifeIndex;
    }

    public List<Skycon> getSkycon() {
        return skycon;
    }

    public void setSkycon(List<Skycon> skycon) {
        this.skycon = skycon;
    }

    public List<Temperature> getTemperature() {
        return temperature;
    }

    public void setTemperature(List<Temperature> temperature) {
        this.temperature = temperature;
    }

    public LifeIndex getLifeIndex() {
        return lifeIndex;
    }

    public void setLifeIndex(LifeIndex lifeIndex) {
        this.lifeIndex = lifeIndex;
    }

    public static class Skycon {
        private String date;
        private String value;

        public Skycon(String date, String value) {
            this.date = date;
            this.value = value;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class Temperature {
        private double min;
        private double max;

        public Temperature(double min, double max) {
            this.min = min;
            this.max = max;
        }

        public double getMin() {
            return min;
        }

        public void setMin(double min) {
            this.min = min;
        }

        public double getMax() {
            return max;
        }

        public void setMax(double max) {
            this.max = max;
        }
    }

    public static class LifeIndex {
        private List<IndexItem> coldRisk;
        private List<IndexItem> dressing;
        private List<IndexItem> ultraviolet;
        private List<IndexItem> carWashing;

        public LifeIndex(List<IndexItem> coldRisk, List<IndexItem> dressing, 
                        List<IndexItem> ultraviolet, List<IndexItem> carWashing) {
            this.coldRisk = coldRisk;
            this.dressing = dressing;
            this.ultraviolet = ultraviolet;
            this.carWashing = carWashing;
        }

        public List<IndexItem> getColdRisk() {
            return coldRisk;
        }

        public void setColdRisk(List<IndexItem> coldRisk) {
            this.coldRisk = coldRisk;
        }

        public List<IndexItem> getDressing() {
            return dressing;
        }

        public void setDressing(List<IndexItem> dressing) {
            this.dressing = dressing;
        }

        public List<IndexItem> getUltraviolet() {
            return ultraviolet;
        }

        public void setUltraviolet(List<IndexItem> ultraviolet) {
            this.ultraviolet = ultraviolet;
        }

        public List<IndexItem> getCarWashing() {
            return carWashing;
        }

        public void setCarWashing(List<IndexItem> carWashing) {
            this.carWashing = carWashing;
        }
    }

    public static class IndexItem {
        private String desc;

        public IndexItem(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
} 