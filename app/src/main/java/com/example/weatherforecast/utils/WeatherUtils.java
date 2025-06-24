package com.example.weatherforecast.utils;

import com.example.weatherforecast.R;
import java.util.HashMap;
import java.util.Map;

public class WeatherUtils {
    
    // 天气描述映射
    private static final Map<String, String> weatherDescriptions = new HashMap<>();
    private static final Map<String, Integer> weatherIcons = new HashMap<>();
    
    static {
        // 初始化天气描述
        weatherDescriptions.put("CLEAR_DAY", "晴天");
        weatherDescriptions.put("CLEAR_NIGHT", "晴夜");
        weatherDescriptions.put("PARTLY_CLOUDY_DAY", "多云");
        weatherDescriptions.put("PARTLY_CLOUDY_NIGHT", "多云");
        weatherDescriptions.put("CLOUDY", "阴天");
        weatherDescriptions.put("WIND", "大风");
        weatherDescriptions.put("LIGHT_RAIN", "小雨");
        weatherDescriptions.put("MODERATE_RAIN", "中雨");
        weatherDescriptions.put("HEAVY_RAIN", "大雨");
        weatherDescriptions.put("STORM_RAIN", "暴雨");
        weatherDescriptions.put("THUNDER_SHOWER", "雷阵雨");
        weatherDescriptions.put("SLEET", "雨夹雪");
        weatherDescriptions.put("LIGHT_SNOW", "小雪");
        weatherDescriptions.put("MODERATE_SNOW", "中雪");
        weatherDescriptions.put("HEAVY_SNOW", "大雪");
        weatherDescriptions.put("STORM_SNOW", "暴雪");
        weatherDescriptions.put("HAIL", "冰雹");
        weatherDescriptions.put("LIGHT_HAZE", "轻度雾霾");
        weatherDescriptions.put("MODERATE_HAZE", "中度雾霾");
        weatherDescriptions.put("HEAVY_HAZE", "重度雾霾");
        weatherDescriptions.put("FOG", "雾");
        weatherDescriptions.put("DUST", "浮尘");
        
        // 初始化天气图标
        weatherIcons.put("CLEAR_DAY", R.drawable.ic_clear_day);
        weatherIcons.put("CLEAR_NIGHT", R.drawable.ic_clear_night);
        weatherIcons.put("PARTLY_CLOUDY_DAY", R.drawable.ic_partly_cloud_day);
        weatherIcons.put("PARTLY_CLOUDY_NIGHT", R.drawable.ic_partly_cloud_night);
        weatherIcons.put("CLOUDY", R.drawable.ic_cloudy);
        weatherIcons.put("WIND", R.drawable.ic_cloudy);
        weatherIcons.put("LIGHT_RAIN", R.drawable.ic_light_rain);
        weatherIcons.put("MODERATE_RAIN", R.drawable.ic_moderate_rain);
        weatherIcons.put("HEAVY_RAIN", R.drawable.ic_heavy_rain);
        weatherIcons.put("STORM_RAIN", R.drawable.ic_storm_rain);
        weatherIcons.put("THUNDER_SHOWER", R.drawable.ic_thunder_shower);
        weatherIcons.put("SLEET", R.drawable.ic_sleet);
        weatherIcons.put("LIGHT_SNOW", R.drawable.ic_light_snow);
        weatherIcons.put("MODERATE_SNOW", R.drawable.ic_moderate_snow);
        weatherIcons.put("HEAVY_SNOW", R.drawable.ic_heavy_snow);
        weatherIcons.put("STORM_SNOW", R.drawable.ic_heavy_snow);
        weatherIcons.put("HAIL", R.drawable.ic_hail);
        weatherIcons.put("LIGHT_HAZE", R.drawable.ic_light_haze);
        weatherIcons.put("MODERATE_HAZE", R.drawable.ic_moderate_haze);
        weatherIcons.put("HEAVY_HAZE", R.drawable.ic_heavy_haze);
        weatherIcons.put("FOG", R.drawable.ic_fog);
        weatherIcons.put("DUST", R.drawable.ic_fog);
    }
    
    /**
     * 获取天气描述
     */
    public static String getWeatherDescription(String skycon) {
        return weatherDescriptions.getOrDefault(skycon, "未知天气");
    }
    
    /**
     * 获取天气图标资源ID
     */
    public static int getWeatherIcon(String skycon) {
        return weatherIcons.getOrDefault(skycon, R.drawable.ic_clear_day);
    }
    
    /**
     * 获取天气建议
     */
    public static String getWeatherAdvice(String skycon, double temperature) {
        StringBuilder advice = new StringBuilder();
        
        // 根据天气类型给出建议
        switch (skycon) {
            case "CLEAR_DAY":
                if (temperature > 30) {
                    advice.append("天气炎热，注意防晒补水");
                } else if (temperature < 10) {
                    advice.append("天气寒冷，注意保暖");
                } else {
                    advice.append("天气适宜，适合户外活动");
                }
                break;
            case "LIGHT_RAIN":
            case "MODERATE_RAIN":
                advice.append("有雨，出门请带伞");
                break;
            case "HEAVY_RAIN":
            case "STORM_RAIN":
                advice.append("暴雨天气，建议减少外出");
                break;
            case "LIGHT_SNOW":
            case "MODERATE_SNOW":
                advice.append("有雪，注意保暖防滑");
                break;
            case "HEAVY_SNOW":
            case "STORM_SNOW":
                advice.append("暴雪天气，建议减少外出");
                break;
            case "WIND":
                advice.append("大风天气，注意安全");
                break;
            case "LIGHT_HAZE":
            case "MODERATE_HAZE":
            case "HEAVY_HAZE":
                advice.append("雾霾天气，建议戴口罩");
                break;
            default:
                advice.append("天气正常");
                break;
        }
        
        return advice.toString();
    }
    
    /**
     * 获取穿衣建议
     */
    public static String getClothingAdvice(double temperature) {
        if (temperature >= 28) {
            return "建议穿短袖、短裤、凉鞋";
        } else if (temperature >= 20) {
            return "建议穿T恤、长裤、运动鞋";
        } else if (temperature >= 10) {
            return "建议穿长袖、外套、长裤";
        } else if (temperature >= 0) {
            return "建议穿厚外套、毛衣、围巾";
        } else {
            return "建议穿羽绒服、厚毛衣、帽子手套";
        }
    }
    
    /**
     * 获取空气质量等级
     */
    public static String getAirQualityLevel(double aqi) {
        if (aqi <= 50) {
            return "优";
        } else if (aqi <= 100) {
            return "良";
        } else if (aqi <= 150) {
            return "轻度污染";
        } else if (aqi <= 200) {
            return "中度污染";
        } else if (aqi <= 300) {
            return "重度污染";
        } else {
            return "严重污染";
        }
    }
} 