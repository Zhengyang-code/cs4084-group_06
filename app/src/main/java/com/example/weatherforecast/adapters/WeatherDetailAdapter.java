package com.example.weatherforecast.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforecast.R;

import java.util.ArrayList;
import java.util.List;

public class WeatherDetailAdapter extends RecyclerView.Adapter<WeatherDetailAdapter.ViewHolder> {

    public static class WeatherDetail {
        private final int iconResId;
        private final String label;
        private final String value;

        public WeatherDetail(@DrawableRes int iconResId, String label, String value) {
            this.iconResId = iconResId;
            this.label = label;
            this.value = value;
        }

        public int getIconResId() {
            return iconResId;
        }

        public String getLabel() {
            return label;
        }

        public String getValue() {
            return value;
        }
    }

    private List<WeatherDetail> details;

    public WeatherDetailAdapter() {
        this.details = new ArrayList<>();
    }

    public void updateData(List<WeatherDetail> details) {
        this.details = details;
        notifyDataSetChanged();
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
        holder.bind(detail);
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivIcon;
        private final TextView tvLabel;
        private final TextView tvValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvLabel = itemView.findViewById(R.id.tv_label);
            tvValue = itemView.findViewById(R.id.tv_value);
        }

        public void bind(WeatherDetail detail) {
            ivIcon.setImageResource(detail.getIconResId());
            tvLabel.setText(detail.getLabel());
            tvValue.setText(detail.getValue());
        }
    }
}