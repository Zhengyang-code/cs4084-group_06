package com.example.weatherforecast.utils;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Utility class for date and time operations
 */
public class DateTimeUtils {

    // Date format constants
    private static final String API_DATE_FORMAT = "yyyy-MM-dd";
    private static final String API_TIME_FORMAT = "HH:mm:ss";
    private static final String API_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    private static final String DISPLAY_DATE_FORMAT = "MMM d";
    private static final String DISPLAY_TIME_FORMAT = "h:mm a";
    private static final String DISPLAY_DAY_FORMAT = "EEEE";

    /**
     * Format a date string to display format
     * @param dateStr Date string in API format (yyyy-MM-dd)
     * @return Formatted date string (e.g., "Jan 15")
     */
    public static String formatDate(String dateStr) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat(API_DATE_FORMAT, Locale.US);
            SimpleDateFormat outputFormat = new SimpleDateFormat(DISPLAY_DATE_FORMAT, Locale.US);

            Date date = inputFormat.parse(dateStr);
            if (date != null) {
                return outputFormat.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * Format a time string to display format
     * @param timeStr Time string in API format (HH:mm:ss)
     * @return Formatted time string (e.g., "3:30 PM")
     */
    public static String formatTime(String timeStr) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat(API_TIME_FORMAT, Locale.US);
            SimpleDateFormat outputFormat = new SimpleDateFormat(DISPLAY_TIME_FORMAT, Locale.US);

            Date time = inputFormat.parse(timeStr);
            if (time != null) {
                return outputFormat.format(time);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStr;
    }

    /**
     * Get day of week from date string
     * @param dateStr Date string in API format (yyyy-MM-dd)
     * @return Day of week (e.g., "Monday")
     */
    public static String getDayOfWeek(String dateStr) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat(API_DATE_FORMAT, Locale.US);
            SimpleDateFormat outputFormat = new SimpleDateFormat(DISPLAY_DAY_FORMAT, Locale.US);

            Date date = inputFormat.parse(dateStr);
            if (date != null) {
                return outputFormat.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * Get relative time span string (e.g., "5 minutes ago")
     * @param dateTimeStr DateTime string in API format (yyyy-MM-dd'T'HH:mm:ss)
     * @return Relative time span string
     */
    public static String getRelativeTimeSpanString(String dateTimeStr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(API_DATETIME_FORMAT, Locale.US);
            Date dateTime = format.parse(dateTimeStr);

            if (dateTime != null) {
                long timeMillis = dateTime.getTime();
                long now = System.currentTimeMillis();

                // If the time difference is within 1 minute, show "just now"
                if (now - timeMillis < DateUtils.MINUTE_IN_MILLIS) {
                    return "just now";
                }

                // Otherwise, use Android's relative time span
                return DateUtils.getRelativeTimeSpanString(
                        timeMillis, now, DateUtils.MINUTE_IN_MILLIS,
                        DateUtils.FORMAT_ABBREV_RELATIVE).toString();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "recently";
    }

    /**
     * Get current date in API format
     * @return Current date as yyyy-MM-dd
     */
    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(API_DATE_FORMAT, Locale.US);
        return dateFormat.format(new Date());
    }

    /**
     * Get date for N days from today in API format
     * @param days Number of days to add
     * @return Future date as yyyy-MM-dd
     */
    public static String getDatePlusDays(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, days);

        SimpleDateFormat dateFormat = new SimpleDateFormat(API_DATE_FORMAT, Locale.US);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * Convert datetime string from one timezone to another
     * @param dateTimeStr DateTime string in source format
     * @param srcFormat Source format pattern
     * @param destFormat Destination format pattern
     * @param srcTimeZone Source timezone
     * @param destTimeZone Destination timezone
     * @return Converted datetime string
     */
    public static String convertTimeZone(String dateTimeStr, String srcFormat, String destFormat,
                                         TimeZone srcTimeZone, TimeZone destTimeZone) {
        try {
            SimpleDateFormat srcFormatter = new SimpleDateFormat(srcFormat, Locale.US);
            srcFormatter.setTimeZone(srcTimeZone);

            Date date = srcFormatter.parse(dateTimeStr);

            SimpleDateFormat destFormatter = new SimpleDateFormat(destFormat, Locale.US);
            destFormatter.setTimeZone(destTimeZone);

            if (date != null) {
                return destFormatter.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateTimeStr;
    }

    /**
     * Parse a date string to a Date object
     * @param dateStr Date string in API format
     * @param format Format pattern
     * @return Date object or null if parsing fails
     */
    public static Date parseDate(String dateStr, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Format a Date object to a string
     * @param date Date object
     * @param format Format pattern
     * @return Formatted date string
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        return dateFormat.format(date);
    }

    /**
     * Check if a date is today
     * @param dateStr Date string in API format (yyyy-MM-dd)
     * @return true if the date is today
     */
    public static boolean isToday(String dateStr) {
        String today = getCurrentDate();
        return today.equals(dateStr);
    }

    /**
     * Format a full date-time string to a short, user-friendly form.
     * Input  : 2025-04-30T15:15:00
     * Output : Apr 30, 3:15 PM
     *
     * @param dateTimeStr Date-time string in API format (yyyy-MM-dd'T'HH:mm:ss)
     * @return Formatted string, or original text if parsing fails
     */
    public static String formatDateTime(String dateTimeStr) {
        try {
            // 1. 解析 API 字符串
            SimpleDateFormat inputFormat = new SimpleDateFormat(API_DATETIME_FORMAT, Locale.US);
            // ⚠️ 如果接口时间是 UTC，请保留下面这行；若已是本地时区，则删除
            // inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date = inputFormat.parse(dateTimeStr);
            if (date == null) return dateTimeStr;

            // 2. 组合自己想要的显示格式，这里是 "Apr 30, 3:15 PM"
            SimpleDateFormat outputFormat =
                    new SimpleDateFormat(DISPLAY_DATE_FORMAT + ", " + DISPLAY_TIME_FORMAT, Locale.US);
            return outputFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return dateTimeStr;
        }
    }

}
