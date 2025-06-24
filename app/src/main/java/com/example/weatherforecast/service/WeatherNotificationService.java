package com.example.weatherforecast.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.example.weatherforecast.R;
import com.example.weatherforecast.ui.WeatherActivity;
import com.example.weatherforecast.utils.WeatherUtils;

/**
 * 天气提醒服务
 * 这是一个独特功能，可以根据天气情况发送智能提醒
 */
public class WeatherNotificationService {
    
    private static final String CHANNEL_ID = "weather_notification";
    private static final int NOTIFICATION_ID = 1001;
    
    private Context context;
    private NotificationManager notificationManager;
    
    public WeatherNotificationService(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
    }
    
    /**
     * 创建通知渠道（Android 8.0+）
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "天气提醒",
                NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("智能天气提醒服务");
            notificationManager.createNotificationChannel(channel);
        }
    }
    
    /**
     * 发送天气提醒通知
     */
    public void sendWeatherNotification(String placeName, String weatherType, double temperature, double aqi) {
        String title = "天气提醒 - " + placeName;
        String content = generateNotificationContent(weatherType, temperature, aqi);
        
        // 创建点击意图
        Intent intent = new Intent(context, WeatherActivity.class);
        intent.putExtra("place_name", placeName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        
        PendingIntent pendingIntent = PendingIntent.getActivity(
            context, 0, intent, 
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        
        // 构建通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_clear_day)
            .setContentTitle(title)
            .setContentText(content)
            .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent);
        
        // 发送通知
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
    
    /**
     * 生成通知内容
     */
    private String generateNotificationContent(String weatherType, double temperature, double aqi) {
        StringBuilder content = new StringBuilder();
        
        // 天气描述
        content.append("当前天气: ").append(WeatherUtils.getWeatherDescription(weatherType));
        content.append("\n温度: ").append(String.format("%.1f°C", temperature));
        
        // 天气建议
        String advice = WeatherUtils.getWeatherAdvice(weatherType, temperature);
        if (!advice.isEmpty()) {
            content.append("\n建议: ").append(advice);
        }
        
        // 穿衣建议
        String clothingAdvice = WeatherUtils.getClothingAdvice(temperature);
        content.append("\n穿衣: ").append(clothingAdvice);
        
        // 空气质量
        if (aqi > 0) {
            String aqiLevel = WeatherUtils.getAirQualityLevel(aqi);
            content.append("\n空气质量: ").append(aqiLevel).append(" (AQI: ").append(String.format("%.0f", aqi)).append(")");
        }
        
        return content.toString();
    }
    
    /**
     * 发送特殊天气预警
     */
    public void sendWeatherAlert(String placeName, String alertType, String alertMessage) {
        String title = "天气预警 - " + placeName;
        
        Intent intent = new Intent(context, WeatherActivity.class);
        intent.putExtra("place_name", placeName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        
        PendingIntent pendingIntent = PendingIntent.getActivity(
            context, 1, intent, 
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_storm_rain)
            .setContentTitle(title)
            .setContentText(alertMessage)
            .setStyle(new NotificationCompat.BigTextStyle().bigText(alertMessage))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setVibrate(new long[]{0, 500, 200, 500}); // 震动提醒
        
        notificationManager.notify(NOTIFICATION_ID + 1, builder.build());
    }
    
    /**
     * 取消所有通知
     */
    public void cancelAllNotifications() {
        notificationManager.cancelAll();
    }
} 