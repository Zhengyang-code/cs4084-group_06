package com.example.weatherforecast.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DailyForecast implements Serializable {

    @SerializedName("datetime")
    private String date;

    @SerializedName("tempmax")
    private double tempMax;

    @SerializedName("tempmin")
    private double tempMin;

    @SerializedName("temp")
    private double temperature;

    @SerializedName("feelslikemax")
    private double feelsLikeMax;

    @SerializedName("feelslikemin")
    private double feelsLikeMin;

    @SerializedName("feelslike")
    private double feelsLike;

    @SerializedName("humidity")
    private double humidity;

    @SerializedName("dew")
    private double dewPoint;

    @SerializedName("precip")
    private double precipitation;

    @SerializedName("precipprob")
    private double precipitationProbability;

    @SerializedName("precipcover")
    private double precipitationCoverage;

    @SerializedName("snow")
    private double snow;

    @SerializedName("snowdepth")
    private double snowDepth;

    @SerializedName("windspeed")
    private double windSpeed;

    @SerializedName("winddir")
    private double windDirection;

    @SerializedName("pressure")
    private double pressure;

    @SerializedName("cloudcover")
    private double cloudCover;

    @SerializedName("visibility")
    private double visibility;

    @SerializedName("uvindex")
    private double uvIndex;

    @SerializedName("sunrise")
    private String sunrise;

    @SerializedName("sunset")
    private String sunset;

    @SerializedName("moonphase")
    private double moonPhase;

    @SerializedName("conditions")
    private String conditions;

    @SerializedName("description")
    private String description;

    @SerializedName("icon")
    private String icon;

    @SerializedName("hours")
    private List<HourlyForecast> hours;

    // Getters and setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getFeelsLikeMax() {
        return feelsLikeMax;
    }

    public void setFeelsLikeMax(double feelsLikeMax) {
        this.feelsLikeMax = feelsLikeMax;
    }

    public double getFeelsLikeMin() {
        return feelsLikeMin;
    }

    public void setFeelsLikeMin(double feelsLikeMin) {
        this.feelsLikeMin = feelsLikeMin;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(double dewPoint) {
        this.dewPoint = dewPoint;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }

    public double getPrecipitationProbability() {
        return precipitationProbability;
    }

    public void setPrecipitationProbability(double precipitationProbability) {
        this.precipitationProbability = precipitationProbability;
    }

    public double getPrecipitationCoverage() {
        return precipitationCoverage;
    }

    public void setPrecipitationCoverage(double precipitationCoverage) {
        this.precipitationCoverage = precipitationCoverage;
    }

    public double getSnow() {
        return snow;
    }

    public void setSnow(double snow) {
        this.snow = snow;
    }

    public double getSnowDepth() {
        return snowDepth;
    }

    public void setSnowDepth(double snowDepth) {
        this.snowDepth = snowDepth;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(double windDirection) {
        this.windDirection = windDirection;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(double cloudCover) {
        this.cloudCover = cloudCover;
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public double getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(double uvIndex) {
        this.uvIndex = uvIndex;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public double getMoonPhase() {
        return moonPhase;
    }

    public void setMoonPhase(double moonPhase) {
        this.moonPhase = moonPhase;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<HourlyForecast> getHours() {
        return hours;
    }

    public void setHours(List<HourlyForecast> hours) {
        this.hours = hours;
    }

    /**
     * Get formatted temperature display string
     * @return String with high and low temperatures
     */
    public String getFormattedTemperature() {
        return Math.round(tempMax) + "° / " + Math.round(tempMin) + "°";
    }

    /**
     * Get 24-hour forecast for this day
     * @return List of hourly forecasts or null
     */
    public List<HourlyForecast> getHourlyForecast() {
        return hours;
    }
}
