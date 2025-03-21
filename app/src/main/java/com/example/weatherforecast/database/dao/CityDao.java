package com.example.weatherforecast.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.weatherforecast.database.entity.CityEntity;

import java.util.List;

@Dao
public interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(CityEntity city);

    @Update
    void update(CityEntity city);

    @Delete
    void delete(CityEntity city);

    @Query("DELETE FROM cities WHERE id = :id")
    void deleteById(long id);

    @Query("SELECT * FROM cities")
    LiveData<List<CityEntity>> getAllCities();

    @Query("SELECT * FROM cities WHERE id = :id")
    LiveData<CityEntity> getCityById(long id);

    @Query("SELECT * FROM cities WHERE isFavorite = 1")
    LiveData<List<CityEntity>> getFavoriteCities();

    @Query("SELECT COUNT(*) FROM cities")
    int getCitiesCount();
}
