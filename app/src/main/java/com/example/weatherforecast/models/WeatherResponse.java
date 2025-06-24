package com.example.weatherforecast.models;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
    @SerializedName("status")
    private String status;
    
    @SerializedName("result")
    private Weather result;
    
    @SerializedName("error")
    private String error;
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Weather getResult() {
        return result;
    }
    
    public void setResult(Weather result) {
        this.result = result;
    }
    
    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }
} 