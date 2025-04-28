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
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnTokenCanceledListener;

import java.io.IOException;
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
    private com.google.android.gms.location.LocationCallback gmsLocationCallback;

    /**
     * Constructor
     * @param context Application context
     */
    public LocationUtils(Context context) {
        this.context = context;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    /**
     * Interface for location result callback
     */
    public interface LocationCallback {
        void onLocationResult(Location location);
    }

    /**
     * Get the current device location
     * @param callback Callback to return location result
     */
    public void getCurrentLocation(final LocationCallback callback) {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            callback.onLocationResult(null);
            return;
        }

        // Try to get last known location first (faster)
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, new CancellationToken() {
            @Override
            public boolean isCancellationRequested() {
                return false;
            }

            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                return this;
            }
        }).addOnSuccessListener(location -> {
            if (location != null) {
                callback.onLocationResult(location);
            } else {
                // If location is null, request a fresh location
                requestNewLocation(callback);
            }
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Error getting last location: " + e.getMessage());
            requestNewLocation(callback);
        });
    }

    /**
     * Request a new location update
     * @param callback Callback to return location result
     */
    private void requestNewLocation(final LocationCallback callback) {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            callback.onLocationResult(null);
            return;
        }

        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, LOCATION_UPDATE_INTERVAL)
                .setMinUpdateIntervalMillis(LOCATION_FASTEST_INTERVAL)
                .build();

        gmsLocationCallback = new com.google.android.gms.location.LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                fusedLocationClient.removeLocationUpdates(gmsLocationCallback);

                if (!locationResult.getLocations().isEmpty()) {
                    Location location = locationResult.getLocations().get(0);
                    callback.onLocationResult(location);
                } else {
                    callback.onLocationResult(null);
                }
            }
        };

        fusedLocationClient.requestLocationUpdates(locationRequest,
                gmsLocationCallback, Looper.getMainLooper());

        // Set a timeout to stop location updates if we don't get a response in a reasonable time
        new android.os.Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (gmsLocationCallback != null) {
                fusedLocationClient.removeLocationUpdates(gmsLocationCallback);
                callback.onLocationResult(null);
            }
        }, 10000); // 10 seconds timeout
    }

    /**
     * Get city information from coordinates using Geocoder
     * @param latitude Latitude coordinate
     * @param longitude Longitude coordinate
     * @return City object or null if geocoding fails
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
     * @param addressStr Address or city name string
     * @return City object or null if geocoding fails
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
        if (gmsLocationCallback != null) {
            fusedLocationClient.removeLocationUpdates(gmsLocationCallback);
            gmsLocationCallback = null;
        }
    }
}