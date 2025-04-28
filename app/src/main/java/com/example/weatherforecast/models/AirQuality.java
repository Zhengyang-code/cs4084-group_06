package com.example.weatherforecast.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Model class for air quality data
 */
public class AirQuality implements Serializable {

    @SerializedName("aqi")
    private int aqiIndex;

    @SerializedName("pm25")
    private double pm25;

    @SerializedName("pm10")
    private double pm10;

    @SerializedName("o3")
    private double ozone;

    @SerializedName("no2")
    private double nitrogenDioxide;

    @SerializedName("so2")
    private double sulfurDioxide;

    @SerializedName("co")
    private double carbonMonoxide;

    @SerializedName("category")
    private String category;

    // Default constructor
    public AirQuality() {
    }

    // Getters and setters
    public int getAqiIndex() {
        return aqiIndex;
    }

    public void setAqiIndex(int aqiIndex) {
        this.aqiIndex = aqiIndex;
    }

    public double getPm25() {
        return pm25;
    }

    public void setPm25(double pm25) {
        this.pm25 = pm25;
    }

    public double getPm10() {
        return pm10;
    }

    public void setPm10(double pm10) {
        this.pm10 = pm10;
    }

    public double getOzone() {
        return ozone;
    }

    public void setOzone(double ozone) {
        this.ozone = ozone;
    }

    public double getNitrogenDioxide() {
        return nitrogenDioxide;
    }

    public void setNitrogenDioxide(double nitrogenDioxide) {
        this.nitrogenDioxide = nitrogenDioxide;
    }

    public double getSulfurDioxide() {
        return sulfurDioxide;
    }

    public void setSulfurDioxide(double sulfurDioxide) {
        this.sulfurDioxide = sulfurDioxide;
    }

    public double getCarbonMonoxide() {
        return carbonMonoxide;
    }

    public void setCarbonMonoxide(double carbonMonoxide) {
        this.carbonMonoxide = carbonMonoxide;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Get air quality level description based on AQI index
     * @return String description of air quality
     */
    public String getAqiDescription() {
        if (aqiIndex <= 50) {
            return "Good";
        } else if (aqiIndex <= 100) {
            return "Moderate";
        } else if (aqiIndex <= 150) {
            return "Unhealthy for Sensitive Groups";
        } else if (aqiIndex <= 200) {
            return "Unhealthy";
        } else if (aqiIndex <= 300) {
            return "Very Unhealthy";
        } else {
            return "Hazardous";
        }
    }

    /**
     * Get color code for AQI level
     * @return int color value
     */
    public int getAqiColor() {
        if (aqiIndex <= 50) {
            return 0xFF00E400; // Green
        } else if (aqiIndex <= 100) {
            return 0xFFFFFF00; // Yellow
        } else if (aqiIndex <= 150) {
            return 0xFFFF7E00; // Orange
        } else if (aqiIndex <= 200) {
            return 0xFFFF0000; // Red
        } else if (aqiIndex <= 300) {
            return 0xFF8F3F97; // Purple
        } else {
            return 0xFF7E0023; // Maroon
        }
    }

    /**
     * Get health recommendations based on air quality
     * @return String with health recommendation
     */
    public String getHealthRecommendation() {
        if (aqiIndex <= 50) {
            return "Air quality is considered satisfactory, and air pollution poses little or no risk.";
        } else if (aqiIndex <= 100) {
            return "Air quality is acceptable; however, for some pollutants there may be a moderate health concern for a very small number of people.";
        } else if (aqiIndex <= 150) {
            return "Members of sensitive groups may experience health effects. The general public is not likely to be affected.";
        } else if (aqiIndex <= 200) {
            return "Everyone may begin to experience health effects; members of sensitive groups may experience more serious health effects.";
        } else if (aqiIndex <= 300) {
            return "Health alert: everyone may experience more serious health effects.";
        } else {
            return "Health warnings of emergency conditions. The entire population is more likely to be affected.";
        }
    }
}