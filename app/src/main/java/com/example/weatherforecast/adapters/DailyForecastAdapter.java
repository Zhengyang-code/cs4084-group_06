package com.example.weatherforecast.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforecast.R;
import com.example.weatherforecast.models.DailyForecast;
import com.example.weatherforecast.utils.DateTimeUtils;
import com.example.weatherforecast.utils.IconUtils;

import java.util.ArrayList;
import java.util.List;

public class DailyForecastAdapter
        extends RecyclerView.Adapter<DailyForecastAdapter.ViewHolder> {

    private final List<DailyForecast> items = new ArrayList<>();

    public void submitData(List<DailyForecast> data) {
        items.clear();
        if (data != null) items.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_daily_forecast, parent, false);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(
            @NonNull ViewHolder h, int pos) {
        DailyForecast f = items.get(pos);

        h.tvDate.setText(DateTimeUtils.formatDate(f.getDate()));               // Mon
        h.tvHighLow.setText(f.getFormattedTemperature());                      // 12° / 6°
        h.ivIcon.setImageResource(
                IconUtils.getWeatherIconResource(f.getIcon()));

        // 如果你在布局里加了 tv_condition，这里也会填
        if (h.tvCondition != null) {
            h.tvCondition.setText(f.getConditions());
        }
    }

    @Override public int getItemCount() { return items.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvHighLow, tvCondition;  // tvCondition 可能为 null
        ImageView ivIcon;
        ViewHolder(View v) {
            super(v);
            tvDate     = v.findViewById(R.id.tv_date);
            tvHighLow  = v.findViewById(R.id.tv_high_low);   // ★ 用你的 id
            ivIcon     = v.findViewById(R.id.iv_icon);

            // 若布局里没有 tv_condition，返回 null 也不会崩
            tvCondition = v.findViewById(R.id.tv_condition);
        }
    }
}
