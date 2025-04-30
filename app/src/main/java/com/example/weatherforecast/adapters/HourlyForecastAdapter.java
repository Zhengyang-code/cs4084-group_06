package com.example.weatherforecast.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.weatherforecast.R;
import com.example.weatherforecast.models.HourlyForecast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Adapter for displaying hourly forecast data in a horizontal RecyclerView.
 */
public class HourlyForecastAdapter
        extends RecyclerView.Adapter<HourlyForecastAdapter.ViewHolder> {

    private final List<HourlyForecast> items = new ArrayList<>();

    /** 点击回调接口 */
    public interface OnItemClickListener {
        void onItemClick(HourlyForecast forecast, int position);
    }

    private OnItemClickListener clickListener;

    // ─────────────────────── 对外 API ─────────────────────── //

    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        this.clickListener = listener;
    }

    /** 刷新数据：高效 DiffUtil 实现 */
    public void updateData(@NonNull List<HourlyForecast> newData) {
        DiffUtil.DiffResult diff = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override public int getOldListSize() { return items.size(); }
            @Override public int getNewListSize() { return newData.size(); }

            /** 这里用时间字符串作为唯一键即可 */
            @Override
            public boolean areItemsTheSame(int oldPos, int newPos) {
                return items.get(oldPos).getTime()
                        .equals(newData.get(newPos).getTime());
            }

            @Override
            public boolean areContentsTheSame(int oldPos, int newPos) {
                return items.get(oldPos).equals(newData.get(newPos));
            }
        });

        items.clear();
        items.addAll(newData);
        diff.dispatchUpdatesTo(this);
    }

    // ─────────────── RecyclerView.Adapter 实现 ─────────────── //

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hourly_forecast, parent, false);
        TextView tvHour = v.findViewById(R.id.tv_hour);
        return new ViewHolder(tvHour, v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HourlyForecast forecast = items.get(position);
        holder.bind(forecast);

        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onItemClick(forecast, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // ─────────────────────── ViewHolder ─────────────────────── //

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvHour;
        private final ImageView ivIcon;
        private final TextView tvTemp;

        ViewHolder(TextView tvHour, @NonNull View itemView) {
            super(itemView);
            this.tvHour = tvHour;
            ivIcon = itemView.findViewById(R.id.iv_icon);   // 天气图标
            tvTemp = itemView.findViewById(R.id.tv_temp);   // 温度
        }

        void bind(HourlyForecast fc) {
            // 时间显示：使用模型里封装好的格式化方法
            tvHour.setText(fc.getFormattedTime());

            // 温度
            tvTemp.setText(String.format(Locale.US, "%.0f°", fc.getTemperature()));

            // 图标：示例用 Glide 加载网络 URL（若 API 返回 drawable 名称，请改用 setImageResource）
            // 例： https://cdn.weather.com/icons/{icon}.png
            String iconUrl = "https://cdn.weather.com/icons/" + fc.getIcon() + ".png";
        }
    }
}
