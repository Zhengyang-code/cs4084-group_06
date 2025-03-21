package com.example.weatherforecast.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HourlyForecast implements Serializable {

    @SerializedName("datetime")
    private String time;

    @SerializedName("temp")
    private double temperature;

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

    @SerializedName("visibility")
    private double visibility;

    @SerializedName("cloudcover")
    private double cloudCover;

    @SerializedName("uvindex")
    private double uvIndex;

    @SerializedName("conditions")
    private String conditions;

    @SerializedName("icon")
    private String icon;

    @SerializedName("solarradiation")
    private double solarRadiation;

    @SerializedName("solarenergy")
    private double solarEnergy;

    // Getters and setters
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
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

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public double getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(double cloudCover) {
        this.cloudCover = cloudCover;
    }

    public double getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(double uvIndex) {
        this.uvIndex = uvIndex;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getSolarRadiation() {
        return solarRadiation;
    }

    public void setSolarRadiation(double solarRadiation) {
        this.solarRadiation = solarRadiation;
    }

    public double getSolarEnergy() {
        return solarEnergy;
    }

    public void setSolarEnergy(double solarEnergy) {
        this.solarEnergy = solarEnergy;
    }

    /**
     * Get formatted time from the datetime string
     * @return Time in HH:MM format
     */
    public String getFormattedTime() {
        // Example implementation - may need to be adjusted based on actual API response format
        if (time != null && time.contains("T")) {
            String[] parts = time.split("T");
            if (parts.length > 1) {
                String timePart = parts[1];
                // Return just hours and minutes
                if (timePart.length() >= 5) {
                    return timePart.substring(0, 5);
                }
                return timePart;
            }
        }
        return time;
    }

    /**
     * Check if this hour has precipitation
     * @return true if precipitation is expected
     */
    public boolean hasPrecipitation() {
        return precipitation > 0 || precipitationProbability > 30;
    }
}