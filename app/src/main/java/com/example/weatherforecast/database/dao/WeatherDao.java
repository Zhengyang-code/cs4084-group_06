package com.example.weatherforecast.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.weatherforecast.database.entity.WeatherEntity;

@Dao
public interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(WeatherEntity weather);

    @Update
    void update(WeatherEntity weather);

    @Query("SELECT * FROM weather WHERE cityId = :cityId")
    LiveData<WeatherEntity> getWeatherForCity(long cityId);

    @Query("DELETE FROM weather WHERE cityId = :cityId")
    void deleteWeatherForCity(long cityId);

    @Query("SELECT * FROM weather WHERE cityId = :cityId")
    WeatherEntity getWeatherForCitySync(long cityId);
}