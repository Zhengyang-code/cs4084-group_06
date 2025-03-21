package com.example.weatherforecast.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WeatherAlert implements Serializable {

    @SerializedName("event")
    private String event;

    @SerializedName("headline")
    private String headline;

    @SerializedName("description")
    private String description;

    @SerializedName("onset")
    private String startTime;

    @SerializedName("ends")
    private String endTime;

    @SerializedName("id")
    private String id;

    // Visual Crossing specific fields
    @SerializedName("severity")
    private String severity;

    // Getters and setters
    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    /**
     * Get the alert severity level
     * @return Alert severity: HIGH, MODERATE, LOW
     */
    public AlertSeverity getAlertSeverity() {
        if (severity == null) {
            return AlertSeverity.MODERATE;
        }

        if (severity.equalsIgnoreCase("severe") ||
                severity.equalsIgnoreCase("extreme") ||
                severity.equalsIgnoreCase("high")) {
            return AlertSeverity.HIGH;
        } else if (severity.equalsIgnoreCase("moderate")) {
            return AlertSeverity.MODERATE;
        } else {
            return AlertSeverity.LOW;
        }
    }

    /**
     * Get color code for the alert severity
     * @return Color value for the alert
     */
    public int getAlertColor() {
        switch (getAlertSeverity()) {
            case HIGH:
                return 0xFFFF3B30; // Red
            case MODERATE:
                return 0xFFFF9500; // Orange
            case LOW:
                return 0xFFFFCC00; // Yellow
            default:
                return 0xFFFF9500; // Default to orange
        }
    }

    /**
     * Alert severity enum
     */
    public enum AlertSeverity {
        HIGH,
        MODERATE,
        LOW
    }
}
