package com.example.weatherforecast.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforecast.R;
import com.example.weatherforecast.models.City;

import java.util.List;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder> {

    private final List<City> cities;
    private final OnCityActionListener listener;

    public interface OnCityActionListener {
        void onCitySelected(City city, int position);
        void onCityRemoved(City city, int position);
        void onCityFavoriteToggled(City city, int position);
    }

    public CityListAdapter(List<City> cities, OnCityActionListener listener) {
        this.cities = cities;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        City city = cities.get(position);
        holder.bind(city, position);
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCityName;
        private final TextView tvCityDetails;
        private final ImageButton btnFavorite;
        private final ImageButton btnRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCityName = itemView.findViewById(R.id.tv_city_name);
            tvCityDetails = itemView.findViewById(R.id.tv_city_details);
            btnFavorite = itemView.findViewById(R.id.btn_favorite);
            btnRemove = itemView.findViewById(R.id.btn_remove);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onCitySelected(cities.get(position), position);
                }
            });

            btnFavorite.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onCityFavoriteToggled(cities.get(position), position);
                }
            });

            btnRemove.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onCityRemoved(cities.get(position), position);
                }
            });
        }

        public void bind(City city, int position) {
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
            tvCityDetails.setText(details);

            // Set favorite icon
            btnFavorite.setImageResource(city.isFavorite() ?
                    R.drawable.ic_favorite : R.drawable.ic_favorite_border);
        }
    }
}
