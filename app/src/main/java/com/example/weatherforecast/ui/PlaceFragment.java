package com.example.weatherforecast.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.weatherforecast.MainActivity;
import com.example.weatherforecast.R;
import com.example.weatherforecast.models.Place;
import com.example.weatherforecast.repository.Repository;
import com.example.weatherforecast.ui.adapter.PlaceAdapter;
import com.example.weatherforecast.ui.viewmodel.PlaceViewModel;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

public class PlaceFragment extends Fragment {
    private PlaceViewModel viewModel;
    private PlaceAdapter adapter;
    private List<Place> placeList = new ArrayList<>();
    
    private View rootView;
    private RecyclerView recyclerView;
    private TextInputEditText searchEditText;
    private View backgroundImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_place, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        viewModel = new PlaceViewModel();
        initViews();
        setupRecyclerView();
        setupSearchListener();
        checkSavedPlace();
    }

    private void initViews() {
        recyclerView = rootView.findViewById(R.id.recyclerView);
        searchEditText = rootView.findViewById(R.id.searchPlaceEdit);
        backgroundImageView = rootView.findViewById(R.id.bgImageView);

    }

    private void setupRecyclerView() {
        adapter = new PlaceAdapter(placeList, place -> {
            // Handle place selection
            viewModel.savePlace(place);
            if (getActivity() instanceof MainActivity) {
                // Navigate to weather activity
                Intent intent = new Intent(getActivity(), WeatherActivity.class);
                intent.putExtra("location_lng", place.getLocation().getLng());
                intent.putExtra("location_lat", place.getLocation().getLat());
                intent.putExtra("place_name", place.getName());
                startActivity(intent);
                getActivity().finish();
            }
        });
        
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void setupSearchListener() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString();
                if (!content.isEmpty()) {
                    searchPlaces(content);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    backgroundImageView.setVisibility(View.VISIBLE);
                    placeList.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void searchPlaces(String query) {
        Repository.searchPlaces(query, new Repository.SearchCallback() {
            @Override
            public void onSuccess(List<Place> places) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        recyclerView.setVisibility(View.VISIBLE);
                        backgroundImageView.setVisibility(View.GONE);
                        placeList.clear();
                        placeList.addAll(places);
                        adapter.notifyDataSetChanged();
                    });
                }
            }

            @Override
            public void onError(String error) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Cannot search any place", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    private void checkSavedPlace() {
        if (getActivity() instanceof MainActivity && viewModel.isPlaceSaved()) {
            Place place = viewModel.getSavedPlace();
            if (place != null) {
                Intent intent = new Intent(getContext(), WeatherActivity.class);
                intent.putExtra("location_lng", place.getLocation().getLng());
                intent.putExtra("location_lat", place.getLocation().getLat());
                intent.putExtra("place_name", place.getName());
                startActivity(intent);
                getActivity().finish();
            }
        }
    }
} 