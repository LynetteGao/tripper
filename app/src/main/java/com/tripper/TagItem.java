package com.tripper;

import com.tripper.db.entities.Tag;

public class TagItem {

    private boolean isSelected;
    private Tag tag;
    private int imageResource;

    public TagItem(Tag tag, int imageResource){
        this.tag = tag;
        this.imageResource = imageResource;
        isSelected = false;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Tag getTag() { return this.tag; }

    public int getImageResource() { return this.imageResource; }

}