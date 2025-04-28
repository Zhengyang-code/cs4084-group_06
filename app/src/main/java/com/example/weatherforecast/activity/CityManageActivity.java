package com.example.weatherforecast.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforecast.R;
import com.example.weatherforecast.adapters.CityListAdapter;
import com.example.weatherforecast.models.City;
import com.example.weatherforecast.utils.SharedPrefsUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CityManageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CityListAdapter adapter;
    private List<City> cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_manage);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Manage Cities");
        }

        recyclerView = findViewById(R.id.rv_city_list);
        FloatingActionButton fabAdd = findViewById(R.id.fab_add_city);

        // Initialize city list
        cityList = SharedPrefsUtils.getSavedCities(this);
        if (cityList == null) {
            cityList = new ArrayList<>();
        }

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CityListAdapter(cityList, new CityListAdapter.OnCityActionListener() {
            @Override
            public void onCitySelected(City city, int position) {
                // Return selected city to MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selected_city", city);
                setResult(RESULT_OK, resultIntent);
                finish();
            }

            @Override
            public void onCityRemoved(City city, int position) {
                // Remove city from list
                cityList.remove(position);
                adapter.notifyItemRemoved(position);
                SharedPrefsUtils.saveCities(CityManageActivity.this, cityList);
                Toast.makeText(CityManageActivity.this, "City removed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCityFavoriteToggled(City city, int position) {
                // Toggle favorite status
                city.setFavorite(!city.isFavorite());
                adapter.notifyItemChanged(position);
                SharedPrefsUtils.saveCities(CityManageActivity.this, cityList);
            }
        });

        recyclerView.setAdapter(adapter);

        // Set FAB click listener
        fabAdd.setOnClickListener(v -> {
            // Open search activity
            Intent searchIntent = new Intent(CityManageActivity.this, SearchActivity.class);
            startActivity(searchIntent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh city list in case new cities were added
        List<City> updatedList = SharedPrefsUtils.getSavedCities(this);
        if (updatedList != null) {
            cityList.clear();
            cityList.addAll(updatedList);
            adapter.notifyDataSetChanged();
        }
    }
}