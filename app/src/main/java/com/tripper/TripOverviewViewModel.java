package com.tripper;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tripper.db.entities.Trip;
import com.tripper.db.relationships.TripWithDaysAndDaySegments;
import com.tripper.repositories.TripRepository;

import java.util.List;

public class TripOverviewViewModel extends AndroidViewModel {
    private MutableLiveData<String> mText;
    private TripRepository tripRepository;

    public TripOverviewViewModel(@NonNull Application application) {
        super(application);
        tripRepository = new TripRepository(application);

        mText = new MutableLiveData<>();
        mText.setValue("Placeholder for list view");
    }

    public TripWithDaysAndDaySegments getTripWithDaysAndSegment(long tripID) {
        return tripRepository.getTripWithDaysAndSegmentsById(tripID);
    }

}
