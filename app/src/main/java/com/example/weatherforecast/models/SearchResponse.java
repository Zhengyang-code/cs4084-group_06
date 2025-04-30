package com.example.weatherforecast.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SearchResponse {
    @SerializedName("queryCost")
    private int queryCost;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("resolvedAddress")
    private String resolvedAddress;

    @SerializedName("address")
    private String address;

    @SerializedName("timezone")
    private String timezone;

    @SerializedName("tzoffset")
    private double tzoffset;

    @SerializedName("description")
    private String description;

    @SerializedName("days")
    private List<DayWeather> days;

    // 添加获取结果城市的方法
    public List<City> getResults() {
        List<City> cities = new ArrayList<>();

        // 从响应创建城市对象
        if (resolvedAddress != null && !resolvedAddress.isEmpty()) {
            City city = new City();
            city.setName(resolvedAddress);
            city.setLatitude(latitude);
            city.setLongitude(longitude);

            // 可以添加其他信息
            city.setCountry(extractCountry(resolvedAddress));

            cities.add(city);
        }

        return cities;
    }

    // 从解析地址中提取国家信息
    private String extractCountry(String address) {
        if (address != null && address.contains(",")) {
            String[] parts = address.split(",");
            if (parts.length > 1) {
                return parts[parts.length - 1].trim();
            }
        }
        return "";
    }

    // Getters and Setters
    public int getQueryCost() {
        return queryCost;
    }

    public void setQueryCost(int queryCost) {
        this.queryCost = queryCost;
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

    public String getResolvedAddress() {
        return resolvedAddress;
    }

    public void setResolvedAddress(String resolvedAddress) {
        this.resolvedAddress = resolvedAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public double getTzoffset() {
        return tzoffset;
    }

    public void setTzoffset(double tzoffset) {
        this.tzoffset = tzoffset;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DayWeather> getDays() {
        return days;
    }

    public void setDays(List<DayWeather> days) {
        this.days = days;
    }
}