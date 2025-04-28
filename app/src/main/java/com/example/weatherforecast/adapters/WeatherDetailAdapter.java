package com.example.weatherforecast.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforecast.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for displaying weather details in a RecyclerView
 */
public class WeatherDetailAdapter extends RecyclerView.Adapter<WeatherDetailAdapter.ViewHolder> {

    private List<WeatherDetail> details;

    public WeatherDetailAdapter() {
        this.details = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weather_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherDetail detail = details.get(position);

        holder.imageView.setImageResource(detail.getIconResId());
        holder.titleTextView.setText(detail.getTitle());
        holder.valueTextView.setText(detail.getValue());
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    /**
     * Update adapter with new data
     * @param newDetails List of weather details
     */
    public void updateData(List<WeatherDetail> newDetails) {
        this.details.clear();
        this.details.addAll(newDetails);
        notifyDataSetChanged();
    }

    /**
     * ViewHolder for weather detail items
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView valueTextView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_icon);
            titleTextView = itemView.findViewById(R.id.tv_title);
            valueTextView = itemView.findViewById(R.id.tv_value);
        }
    }

    /**
     * Data class for weather detail items
     */
    public static class WeatherDetail {
        private final int iconResId;
        private final String title;
        private final String value;

        public WeatherDetail(int iconResId, String title, String value) {
            this.iconResId = iconResId;
            this.title = title;
            this.value = value;
        }

        public int getIconResId() {
            return iconResId;
        }

        public String getTitle() {
            return title;
        }

        public String getValue() {
            return value;
        }
    }
}