package com.example.weatherforecast.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "weather",
        foreignKeys = @ForeignKey(entity = CityEntity.class,
                parentColumns = "id",
                childColumns = "cityId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("cityId")})
public class WeatherEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long cityId;
    private String weatherData; // JSON string of weather data
    private long timestamp;

    public WeatherEntity() {
    }

    public WeatherEntity(long cityId, String weatherData) {
        this.cityId = cityId;
        this.weatherData = weatherData;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public String getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(String weatherData) {
        this.weatherData = weatherData;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}