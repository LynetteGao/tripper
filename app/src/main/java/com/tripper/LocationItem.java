package com.tripper;

public class LocationItem {

    private int locationImage;
    private String locationText;
    private boolean isSelected = false;

    public LocationItem(int image, String text){
        locationImage = image;
        locationText = text;
    }

    public int getLocationImage(){
        return locationImage;
    }

    public String getLocationText(){
        return locationText;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}