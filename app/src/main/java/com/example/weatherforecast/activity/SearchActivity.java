package com.example.weatherforecast.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforecast.R;
import com.example.weatherforecast.adapters.SearchResultAdapter;
import com.example.weatherforecast.models.City;
import com.example.weatherforecast.models.SearchResponse;
import com.example.weatherforecast.network.APIClient;
import com.example.weatherforecast.network.WeatherService;
import com.example.weatherforecast.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private EditText editSearch;
    private ImageButton btnSearch;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView tvNoResults;

    private SearchResultAdapter adapter;
    private WeatherService weatherService;
    private List<City> searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Search City");
        }

        // Initialize views
        editSearch = findViewById(R.id.edit_search);
        btnSearch = findViewById(R.id.btn_search);
        recyclerView = findViewById(R.id.rv_search_results);
        progressBar = findViewById(R.id.progress_bar);
        tvNoResults = findViewById(R.id.tv_no_results);

        // Initialize API service
        weatherService = APIClient.getClient().create(WeatherService.class);

        // Initialize RecyclerView
        searchResults = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchResultAdapter(searchResults, city -> {
            // Add selected city to saved list
            List<City> savedCities = SharedPrefsUtils.getSavedCities(SearchActivity.this);
            if (savedCities == null) {
                savedCities = new ArrayList<>();
            }

            // Check if city already exists in list
            boolean cityExists = false;
            for (City savedCity : savedCities) {
                if (savedCity.getName().equals(city.getName())) {
                    cityExists = true;
                    break;
                }
            }

            if (!cityExists) {
                savedCities.add(city);
                SharedPrefsUtils.saveCities(SearchActivity.this, savedCities);
                Toast.makeText(SearchActivity.this, "City added", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(SearchActivity.this, "City already in your list", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);

        // Set search button click listener
        btnSearch.setOnClickListener(v -> {
            String query = editSearch.getText().toString().trim();
            if (!query.isEmpty()) {
                searchCity(query);
            } else {
                Toast.makeText(SearchActivity.this, "Please enter a city name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchCity(String query) {
        showProgress(true);

        String apiKey = getString(R.string.weather_api_key);

        // 添加日志以调试请求
        Log.d("API_DEBUG", "Searching for city: " + query);

        Call<SearchResponse> call = weatherService.searchCity(query, apiKey);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchResponse> call, @NonNull Response<SearchResponse> response) {
                showProgress(false);

                // 打印完整的请求URL以进行调试
                Log.d("API_DEBUG", "Request URL: " + call.request().url().toString());

                if (response.isSuccessful() && response.body() != null) {
                    SearchResponse searchResponse = response.body();

                    // 打印响应详情
                    Log.d("API_DEBUG", "Response successful");
                    Log.d("API_DEBUG", "Resolved Address: " + searchResponse.getResolvedAddress());
                    Log.d("API_DEBUG", "Latitude: " + searchResponse.getLatitude());
                    Log.d("API_DEBUG", "Longitude: " + searchResponse.getLongitude());

                    List<City> results = searchResponse.getResults();
                    if (results != null && !results.isEmpty()) {
                        searchResults.clear();
                        searchResults.addAll(results);
                        adapter.notifyDataSetChanged();
                        showNoResults(false);

                        // 打印找到的城市信息
                        for (City city : results) {
                            Log.d("API_DEBUG", "Found city: " + city.getName() + ", Lat: " + city.getLatitude() + ", Lon: " + city.getLongitude());
                        }
                    } else {
                        Log.d("API_DEBUG", "No cities found in results");
                        searchResults.clear();
                        adapter.notifyDataSetChanged();
                        showNoResults(true);
                    }
                } else {
                    Log.d("API_DEBUG", "Response not successful: " + response.code());
                    try {
                        if (response.errorBody() != null) {
                            Log.d("API_DEBUG", "Error body: " + response.errorBody().string());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(SearchActivity.this, "Error searching for city", Toast.LENGTH_SHORT).show();
                    showNoResults(true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SearchResponse> call, @NonNull Throwable t) {
                showProgress(false);
                Log.e("API_DEBUG", "Network error: " + t.getMessage(), t);
                Toast.makeText(SearchActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                showNoResults(true);
            }
        });
    }

    private void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    private void showNoResults(boolean show) {
        tvNoResults.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}