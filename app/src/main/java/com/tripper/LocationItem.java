package com.tripper;

import com.google.maps.model.PlacesSearchResult;

public class LocationItem {

    private boolean isSelected;
    private PlacesSearchResult placesSearchResult;

    public LocationItem(PlacesSearchResult placesSearchResult){
        this.placesSearchResult = placesSearchResult;
        this.isSelected = false;
    }

    public PlacesSearchResult getPlacesSearchResult() {
        return this.placesSearchResult;
    }

    public boolean isSelected() {
        return isSelected;
    }

}