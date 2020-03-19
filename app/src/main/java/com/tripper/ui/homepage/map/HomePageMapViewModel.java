package com.tripper.ui.homepage.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomePageMapViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomePageMapViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Place holder for map view");
    }

    public LiveData<String> getText() {
        return mText;
    }
}