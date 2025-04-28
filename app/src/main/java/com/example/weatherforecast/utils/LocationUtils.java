package com.example.weatherforecast.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.weatherforecast.models.City;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Utility class for location-related operations
 */
public class LocationUtils {

    private static final String TAG = "LocationUtils";
    private static final long LOCATION_UPDATE_INTERVAL = TimeUnit.MINUTES.toMillis(15);
    private static final long LOCATION_FASTEST_INTERVAL = TimeUnit.MINUTES.toMillis(5);

    private final Context context;
    private final FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    /**
     * Constructor
     */
    public LocationUtils(Context context) {
        this.context = context;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    /**
     * Get the current device location
     */
    public void getCurrentLocation(final LocationCallback callback) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            callback.onLocationResult(null);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            List<Location> locations = new ArrayList<>();
                            locations.add(location);
                            LocationResult locationResult = LocationResult.create(locations);
                            callback.onLocationResult(locationResult);
                        } else {
                            requestNewLocation(callback);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error getting last location: " + e.getMessage());
                        requestNewLocation(callback);
                    }
                });
    }

    /**
     * Request a new location update
     */
    private void requestNewLocation(final LocationCallback callback) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            callback.onLocationResult(null);
            return;
        }

        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .setInterval(LOCATION_UPDATE_INTERVAL)
                .setFastestInterval(LOCATION_FASTEST_INTERVAL);

        // 👇 新加内部 LocationCallback
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null && locationResult.getLastLocation() != null) {
                    List<Location> locations = new ArrayList<>();
                    locations.add(locationResult.getLastLocation());
                    LocationResult result = LocationResult.create(locations);
                    callback.onLocationResult(result);
                } else {
                    requestNewLocation(callback);
                }
            }
        };

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

        // 超时10秒后如果没有拿到位置信息，则停止请求
        new android.os.Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (locationCallback != null) {
                    fusedLocationClient.removeLocationUpdates(locationCallback);
                    callback.onLocationResult(null);
                }
            }
        }, 10000); // 10秒超时
    }

    /**
     * Get city information from coordinates using Geocoder
     */
    public City getCityFromCoordinates(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String cityName = address.getLocality();
                if (cityName == null || cityName.isEmpty()) {
                    cityName = address.getSubAdminArea();
                }
                if (cityName == null || cityName.isEmpty()) {
                    cityName = address.getAdminArea();
                }

                String state = address.getAdminArea();
                String country = address.getCountryName();

                return new City(cityName, latitude, longitude, country, state);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error getting address from coordinates: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get coordinates from a city name or address using Geocoder
     */
    public City getCoordinatesFromAddress(String addressStr) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(addressStr, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                double latitude = address.getLatitude();
                double longitude = address.getLongitude();

                String city = address.getLocality();
                if (city == null || city.isEmpty()) {
                    city = address.getSubAdminArea();
                }
                if (city == null || city.isEmpty()) {
                    city = address.getAdminArea();
                }

                String state = address.getAdminArea();
                String country = address.getCountryName();

                return new City(city, latitude, longitude, country, state);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error getting coordinates from address: " + e.getMessage());
        }
        return null;
    }

    /**
     * Stop location updates
     */
    public void stopLocationUpdates() {
        if (locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
            locationCallback = null;
        }
    }
}
