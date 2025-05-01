package com.example.weatherforecast.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CurrentWeather implements Serializable {

    @SerializedName("address")
    private String locationName;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("timezone")
    private String timezone;

    @SerializedName("tzoffset")
    private double timezoneOffset;

    @SerializedName("days")
    private List<DailyForecast> dailyForecasts;

    @SerializedName("alerts")
    private List<WeatherAlert> alerts;

    // Additional fields from Visual Crossing API
    @SerializedName("description")
    private String description;

    private CurrentConditions current;
    @SerializedName("resolvedAddress")
    private String resolvedAddress;

    public CurrentConditions getCurrent() {
        if (current == null && dailyForecasts != null && !dailyForecasts.isEmpty()) {
            // 从第一天数据创建CurrentConditions对象
            DailyForecast today = dailyForecasts.get(0);
            current = new CurrentConditions();
            current.setDateTime(today.getDate());
            current.setTemperature(today.getTemperature());
            current.setFeelsLike(today.getFeelsLike());
            current.setHumidity(today.getHumidity());
            current.setDewPoint(today.getDewPoint());
            current.setPrecipitation(today.getPrecipitation());
            current.setPrecipitationProbability(today.getPrecipitationProbability());
            current.setWindSpeed(today.getWindSpeed());
            current.setWindDirection(today.getWindDirection());
            current.setPressure(today.getPressure());
            current.setVisibility(today.getVisibility());
            current.setCloudCover(today.getCloudCover());
            current.setUvIndex(today.getUvIndex());
            current.setConditions(today.getConditions());
            current.setIcon(today.getIcon());
            current.setSunrise(today.getSunrise());
            current.setSunset(today.getSunset());
            current.setMoonPhase(today.getMoonPhase());
        }
        return current;
    }
    public static class CurrentConditions implements Serializable {
        @SerializedName("datetime")
        private String dateTime;

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

        @SerializedName("sunrise")
        private String sunrise;

        @SerializedName("sunset")
        private String sunset;

        @SerializedName("moonphase")
        private double moonPhase;

        @SerializedName("solarradiation")
        private double solarRadiation;

        @SerializedName("solarenergy")
        private double solarEnergy;

        // Getters and setters
        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
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
    }

    // Getters and setters for main class
    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public double getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(double timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }


    public void setCurrent(CurrentConditions current) {
        this.current = current;
    }

    public List<DailyForecast> getDailyForecasts() {
        return dailyForecasts;
    }

    public void setDailyForecasts(List<DailyForecast> dailyForecasts) {
        this.dailyForecasts = dailyForecasts;
    }

    public List<WeatherAlert> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<WeatherAlert> alerts) {
        this.alerts = alerts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResolvedAddress() {
        return resolvedAddress;
    }

    public void setResolvedAddress(String resolvedAddress) {
        this.resolvedAddress = resolvedAddress;
    }

    /**
     * Check if there are any active weather alerts
     * @return true if there are alerts
     */
    public boolean hasAlerts() {
        return alerts != null && !alerts.isEmpty();
    }

    /**
     * Get air quality information
     * @return AirQuality object or null if not available
     */
    public AirQuality getAirQuality() {
        // Visual Crossing API doesn't directly provide AQI in the same endpoint
        // This would need to be implemented with a separate API call or using another API
        return null;
    }
}