package com.example.weatherforecast.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforecast.R;
import com.example.weatherforecast.models.DailyForecast;
import com.example.weatherforecast.utils.DateTimeUtils;
import com.example.weatherforecast.utils.IconUtils;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DailyForecastAdapter
        extends RecyclerView.Adapter<DailyForecastAdapter.ViewHolder> {

    private final List<DailyForecast> items = new ArrayList<>();

    /** 对外暴露的点击接口 */
    public interface OnItemClickListener {
        void onItemClick(DailyForecast forecast, int position);
    }

    private OnItemClickListener clickListener;

    /**
     * 供 Fragment/Activity 设置点击回调
     */
    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        this.clickListener = listener;
    }

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

    /**
     * 用新数据刷新列表。内部使用 DiffUtil 做最小更新。
     * 如果对性能要求不高，可以把整段替换为：
     *    items.clear();
     *    items.addAll(newData);
     *    notifyDataSetChanged();
     */
    public void updateData(@NonNull List<DailyForecast> newData) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() { return items.size(); }

            @Override
            public int getNewListSize() { return newData.size(); }

            @Override
            public boolean areItemsTheSame(int oldItemPos, int newItemPos) {
                // 如果你的 DailyForecast 有唯一 id，请替换下面逻辑
                return items.get(oldItemPos).getDate()
                        .equals(newData.get(newItemPos).getDate());
            }

            @Override
            public boolean areContentsTheSame(int oldItemPos, int newItemPos) {
                return items.get(oldItemPos).equals(newData.get(newItemPos));
            }
        });

        items.clear();
        items.addAll(newData);
        diffResult.dispatchUpdatesTo(this);
    }

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
