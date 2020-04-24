package com.tripper.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tripper.db.entities.Tag;
import com.tripper.repositories.TripRepository;

import java.util.List;

public class TagSuggestionViewModel extends AndroidViewModel {
    private TripRepository tripRepository;

    public TagSuggestionViewModel(Application application) {
        super(application);
        tripRepository = new TripRepository(application);
    }

    public List<Tag> getDefaultTags() {
        return tripRepository.getDefaultTags();
    }
}
