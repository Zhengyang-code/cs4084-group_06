package com.example.weatherforecast.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.location.Priority;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.weatherforecast.models.City;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class LocationUtils {

    private static final String TAG = "LocationUtils";
    private static final long LOCATION_UPDATE_INTERVAL = TimeUnit.MINUTES.toMillis(15);
    private static final long LOCATION_FASTEST_INTERVAL = TimeUnit.MINUTES.toMillis(5);

    private final Context context;
    private final FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    public LocationUtils(Context context) {
        this.context = context.getApplicationContext(); // 避免内存泄漏，使用ApplicationContext
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public interface LocationResultListener {
        void onLocationResult(Location location);
        void onLocationFailure(Exception e);
    }

    public void getCurrentLocation(final LocationResultListener listener) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            listener.onLocationFailure(new SecurityException("Location permission not granted"));
            return;
        }

        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, LOCATION_UPDATE_INTERVAL)
                .setMinUpdateIntervalMillis(LOCATION_FASTEST_INTERVAL)
                .build();

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult.getLocations().size() > 0) {
                    Location latestLocation = locationResult.getLastLocation();
                    listener.onLocationResult(latestLocation);
                } else {
                    listener.onLocationFailure(new Exception("No location data available"));
                }
                // 获取到一次定位就停止更新，节省资源
                stopLocationUpdates();
            }
        };

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    public void stopLocationUpdates() {
        if (locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    public City getCityFromLocation(Location location) {
        if (location == null) return null;
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String cityName = address.getLocality();
                String countryName = address.getCountryName();
                if (cityName != null && countryName != null) {
                    return new City(cityName, countryName);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Geocoder failed", e);
        }
        return null;
    }
}
