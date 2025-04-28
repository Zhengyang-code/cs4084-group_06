package com.example.weatherforecast.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {

    @SerializedName("results")
    private List<City> results;

    @SerializedName("count")
    private int count;

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    public List<City> getResults() {
        return results;
    }

    public void setResults(List<City> results) {
        this.results = results;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}