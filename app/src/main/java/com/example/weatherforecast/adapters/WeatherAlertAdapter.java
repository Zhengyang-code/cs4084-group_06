package com.example.weatherforecast.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.weatherforecast.R;
import com.example.weatherforecast.models.WeatherAlert;
import com.example.weatherforecast.utils.DateTimeUtils;

import java.util.List;

/**
 * Adapter for displaying weather alerts in a ListView
 */
public class WeatherAlertAdapter extends ArrayAdapter<WeatherAlert> {

    private Context context;
    private List<WeatherAlert> alerts;

    public WeatherAlertAdapter(Context context, List<WeatherAlert> alerts) {
        super(context, 0, alerts);
        this.context = context;
        this.alerts = alerts;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_weather_alert, parent, false);
        }

        WeatherAlert alert = getItem(position);
        if (alert == null) return convertView;

        // Set alert title
        TextView tvTitle = convertView.findViewById(R.id.tv_alert_title);
        tvTitle.setText(alert.getEvent());

        // Set alert time
        TextView tvTime = convertView.findViewById(R.id.tv_alert_time);
        String timeText = String.format("From: %s\nTo: %s",
                DateTimeUtils.formatDateTime(alert.getStartTime()),
                DateTimeUtils.formatDateTime(alert.getEndTime()));
        tvTime.setText(timeText);

        // Set alert description
        TextView tvDescription = convertView.findViewById(R.id.tv_alert_description);
        tvDescription.setText(alert.getDescription());

        // Set alert source
        TextView tvSource = convertView.findViewById(R.id.tv_alert_source);
        tvSource.setText(String.format("Source: %s", alert.getSource()));

        // Set severity indicator color
        View severityIndicator = convertView.findViewById(R.id.view_severity_indicator);
        severityIndicator.setBackgroundColor(alert.getAlertColor());

        return convertView;
    }
}