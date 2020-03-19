package com.tripper.ui.homepage.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomePageListViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomePageListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Placeholder for list view");
    }

    public LiveData<String> getText() {
        return mText;
    }
}