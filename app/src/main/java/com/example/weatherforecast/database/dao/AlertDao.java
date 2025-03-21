package com.example.weatherforecast.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.weatherforecast.database.entity.AlertEntity;

import java.util.List;

@Dao
public interface AlertDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AlertEntity> alerts);

    @Query("SELECT * FROM alerts WHERE cityId = :cityId")
    LiveData<List<AlertEntity>> getAlertsForCity(long cityId);

    @Query("DELETE FROM alerts WHERE cityId = :cityId")
    void deleteAlertsForCity(long cityId);

    @Query("DELETE FROM alerts WHERE endTime < :currentTime")
    void deleteExpiredAlerts(long currentTime);
}
