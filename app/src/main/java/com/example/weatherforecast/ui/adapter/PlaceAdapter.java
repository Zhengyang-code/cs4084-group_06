package com.example.weatherforecast.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.weatherforecast.R;
import com.example.weatherforecast.models.Place;
import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {
    private List<Place> placeList;
    private OnPlaceClickListener listener;

    public interface OnPlaceClickListener {
        void onPlaceClick(Place place);
    }

    public PlaceAdapter(List<Place> placeList, OnPlaceClickListener listener) {
        this.placeList = placeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Place place = placeList.get(position);
        holder.placeName.setText(place.getName());
        holder.placeAddress.setText(place.getAddress());
        
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onPlaceClick(place);
            }
        });
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView placeName;
        TextView placeAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.placeName);
            placeAddress = itemView.findViewById(R.id.placeAddress);
        }
    }
} 