package com.example.weatherforecast.network;

import com.example.weatherforecast.models.CurrentWeather;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static final String BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/";
    private static Retrofit retrofit = null;

    /**
     * Get Retrofit client instance
     * @return Retrofit client
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            // Setup logging interceptor
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Setup OkHttp client with timeouts
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(logging);

            // Configure Gson for custom deserialization
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(CurrentWeather.class, new WeatherDeserializer())
                    .create();

            // Build Retrofit instance
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }

    /**
     * Custom deserializer for Visual Crossing API responses
     * Handles the specific format of the API response
     */
    private static class WeatherDeserializer implements JsonDeserializer<CurrentWeather> {
        @Override
        public CurrentWeather deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {

            // Visual Crossing API returns a complex nested JSON structure
            // This deserializer maps the relevant fields to our CurrentWeather model

            // Parse the main object using default GSON behavior
            CurrentWeather weather = new Gson().fromJson(json, CurrentWeather.class);

            // Extract location information and other metadata from the response
            if (json.isJsonObject()) {
                JsonElement addressElement = json.getAsJsonObject().get("address");
                if (addressElement != null && !addressElement.isJsonNull()) {
                    weather.setLocationName(addressElement.getAsString());
                }

                JsonElement latitudeElement = json.getAsJsonObject().get("latitude");
                JsonElement longitudeElement = json.getAsJsonObject().get("longitude");

                if (latitudeElement != null && !latitudeElement.isJsonNull() &&
                        longitudeElement != null && !longitudeElement.isJsonNull()) {
                    weather.setLatitude(latitudeElement.getAsDouble());
                    weather.setLongitude(longitudeElement.getAsDouble());
                }

                // Add more custom parsing as needed for the specific API response structure
            }

            return weather;
        }
    }
}
