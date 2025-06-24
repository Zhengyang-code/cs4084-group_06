package com.example.weatherforecast.ui.viewmodel;

import com.example.weatherforecast.models.Place;
import com.example.weatherforecast.repository.Repository;

public class PlaceViewModel {
    
    public void savePlace(Place place) {
        Repository.savePlace(place);
    }
    
    public Place getSavedPlace() {
        return Repository.getSavedPlace();
    }
    
    public boolean isPlaceSaved() {
        return Repository.isPlaceSaved();
    }
} 