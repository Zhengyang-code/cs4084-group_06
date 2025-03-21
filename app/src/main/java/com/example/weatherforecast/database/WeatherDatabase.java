package com.example.weatherforecast.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.weatherforecast.database.dao.CityDao;
import com.example.weatherforecast.database.dao.WeatherDao;
import com.example.weatherforecast.database.dao.AlertDao;
import com.example.weatherforecast.database.entity.CityEntity;
import com.example.weatherforecast.database.entity.WeatherEntity;
import com.example.weatherforecast.database.entity.AlertEntity;
import com.example.weatherforecast.database.converters.DateConverter;

@Database(entities = {CityEntity.class, WeatherEntity.class, AlertEntity.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class WeatherDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "weather_db";
    private static WeatherDatabase instance;

    public abstract CityDao cityDao();
    public abstract WeatherDao weatherDao();
    public abstract AlertDao alertDao();

    public static synchronized WeatherDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            WeatherDatabase.class,
                            DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
