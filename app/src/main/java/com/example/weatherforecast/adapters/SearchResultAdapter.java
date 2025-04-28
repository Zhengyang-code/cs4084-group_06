package com.example.weatherforecast.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforecast.R;
import com.example.weatherforecast.models.City;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private final List<City> cities;
    private final OnCitySelectedListener listener;

    public interface OnCitySelectedListener {
        void onCitySelected(City city);
    }

    public SearchResultAdapter(List<City> cities, OnCitySelectedListener listener) {
        this.cities = cities;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        City city = cities.get(position);
        holder.bind(city);
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCityName;
        private final TextView tvCityDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCityName = itemView.findViewById(R.id.tv_city_name);
            tvCityDetails = itemView.findViewById(R.id.tv_city_details);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onCitySelected(cities.get(position));
                }
            });
        }

        public void bind(City city) {
            tvCityName.setText(city.getName());

            // Show country and state if available
            String details = "";
            if (city.getState() != null && !city.getState().isEmpty()) {
                details += city.getState();
            }
            if (city.getCountry() != null && !city.getCountry().isEmpty()) {
                if (!details.isEmpty()) {
                    details += ", ";
                }
                details += city.getCountry();
            }

            // Add coordinates
            if (!details.isEmpty()) {
                details += " • ";
            }
            details += String.format("%.2f, %.2f", city.getLatitude(), city.getLongitude());

            tvCityDetails.setText(details);
        }
    }
}