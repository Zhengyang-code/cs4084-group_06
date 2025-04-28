package com.example.weatherforecast.models;


import java.io.Serializable;

/**
 * City model class for storing location information
 */
public class City implements Serializable {

    private String name;
    private double latitude;
    private double longitude;
    private String country;
    private String state;
    private boolean isFavorite;
    private String lastUpdated;

    /**
     * Default constructor
     */
    public City() {
    }

    /**
     * Constructor with basic details
     *
     * @param name City name
     * @param latitude Latitude coordinate
     * @param longitude Longitude coordinate
     */
    public City(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Constructor with all details
     *
     * @param name City name
     * @param latitude Latitude coordinate
     * @param longitude Longitude coordinate
     * @param country Country name
     * @param state State/province name
     */
    public City(String name, double latitude, double longitude, String country, String state) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.state = state;
    }

    /**
     * Constructor with city name and country
     *
     * @param name City name
     * @param country Country name
     */
    public City(String name, String country) {
        this.name = name;
        this.country = country;
    }


    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * Get full display name with state/country info if available
     *
     * @return Formatted display name
     */
    public String getDisplayName() {
        StringBuilder displayName = new StringBuilder(name);

        if (state != null && !state.isEmpty()) {
            displayName.append(", ").append(state);
        }

        if (country != null && !country.isEmpty()) {
            displayName.append(", ").append(country);
        }

        return displayName.toString();
    }

    /**
     * Get coordinates as string in format "lat,lon"
     *
     * @return Coordinate string
     */
    public String getCoordinatesString() {
        return latitude + "," + longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (Double.compare(city.latitude, latitude) != 0) return false;
        if (Double.compare(city.longitude, longitude) != 0) return false;
        return name != null ? name.equals(city.name) : city.name == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}
