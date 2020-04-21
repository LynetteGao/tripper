package com.tripper;

public class TagItem {

    private int tagImage;
    private String tagText;
    private boolean isSelected = false;

    public TagItem(int image, String text){
        tagImage = image;
        tagText = text;
    }

    public int getTagImage(){
        return tagImage;
    }

    public String getTagText(){
        return tagText;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}