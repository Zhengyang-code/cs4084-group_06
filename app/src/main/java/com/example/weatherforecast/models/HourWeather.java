package com.example.weatherforecast.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class HourWeather {
    @SerializedName("datetime")
    private String datetime;

    @SerializedName("datetimeEpoch")
    private long datetimeEpoch;

    @SerializedName("temp")
    private double temp;

    @SerializedName("feelslike")
    private double feelslike;

    @SerializedName("humidity")
    private double humidity;

    @SerializedName("dew")
    private double dew;

    @SerializedName("precip")
    private double precip;

    @SerializedName("precipprob")
    private double precipprob;

    @SerializedName("snow")
    private double snow;

    @SerializedName("snowdepth")
    private double snowdepth;

    @SerializedName("preciptype")
    private List<String> preciptype;

    @SerializedName("windgust")
    private double windgust;

    @SerializedName("windspeed")
    private double windspeed;

    @SerializedName("winddir")
    private double winddir;

    @SerializedName("pressure")
    private double pressure;

    @SerializedName("visibility")
    private double visibility;

    @SerializedName("cloudcover")
    private double cloudcover;

    @SerializedName("solarradiation")
    private double solarradiation;

    @SerializedName("solarenergy")
    private double solarenergy;

    @SerializedName("uvindex")
    private double uvindex;

    @SerializedName("severerisk")
    private double severerisk;

    @SerializedName("conditions")
    private String conditions;

    @SerializedName("icon")
    private String icon;

    @SerializedName("stations")
    private List<String> stations;

    @SerializedName("source")
    private String source;

    // Getters and Setters
    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public long getDatetimeEpoch() {
        return datetimeEpoch;
    }

    public void setDatetimeEpoch(long datetimeEpoch) {
        this.datetimeEpoch = datetimeEpoch;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getFeelslike() {
        return feelslike;
    }

    public void setFeelslike(double feelslike) {
        this.feelslike = feelslike;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getDew() {
        return dew;
    }

    public void setDew(double dew) {
        this.dew = dew;
    }

    public double getPrecip() {
        return precip;
    }

    public void setPrecip(double precip) {
        this.precip = precip;
    }

    public double getPrecipprob() {
        return precipprob;
    }

    public void setPrecipprob(double precipprob) {
        this.precipprob = precipprob;
    }

    public double getSnow() {
        return snow;
    }

    public void setSnow(double snow) {
        this.snow = snow;
    }

    public double getSnowdepth() {
        return snowdepth;
    }

    public void setSnowdepth(double snowdepth) {
        this.snowdepth = snowdepth;
    }

    public List<String> getPreciptype() {
        return preciptype;
    }

    public void setPreciptype(List<String> preciptype) {
        this.preciptype = preciptype;
    }

    public double getWindgust() {
        return windgust;
    }

    public void setWindgust(double windgust) {
        this.windgust = windgust;
    }

    public double getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(double windspeed) {
        this.windspeed = windspeed;
    }

    public double getWinddir() {
        return winddir;
    }

    public void setWinddir(double winddir) {
        this.winddir = winddir;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getCloudcover() {
        return cloudcover;
    }

    public void setCloudcover(double cloudcover) {
        this.cloudcover = cloudcover;
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public double getSolarradiation() {
        return solarradiation;
    }

    public void setSolarradiation(double solarradiation) {
        this.solarradiation = solarradiation;
    }

    public double getSolarenergy() {
        return solarenergy;
    }

    public void setSolarenergy(double solarenergy) {
        this.solarenergy = solarenergy;
    }

    public double getUvindex() {
        return uvindex;
    }

    public void setUvindex(double uvindex) {
        this.uvindex = uvindex;
    }

    public double getSevererisk() {
        return severerisk;
    }

    public void setSevererisk(double severerisk) {
        this.severerisk = severerisk;
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

    public List<String> getStations() {
        return stations;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}