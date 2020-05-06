package com.tripper.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.tripper.db.entities.Diary;
import com.tripper.db.entities.DiaryEntry;
import com.tripper.db.relationships.TripWithDaysAndDaySegments;
import com.tripper.repositories.TripRepository;

import java.util.List;

public class DiaryEditViewModel extends AndroidViewModel {
    private TripRepository tripRepository;

    public DiaryEditViewModel(@NonNull Application application) {
        super(application);
        tripRepository = new TripRepository(application);
    }

    public void insertDiary(Diary diary){
        tripRepository.insertDiarySync(diary);
    }

    public Diary getDiaryById(long segmentId){
        return tripRepository.getDiaryById(segmentId);
    }

    public DiaryEntry getDiaryEntryById(long diaryId) {
        return tripRepository.getDiaryEntriesById(diaryId).get(0);
    }

    public void insertDiaryEntry(DiaryEntry diaryEntry){
        tripRepository.insertDiaryEntrySync(diaryEntry);
    }

    public void updateDiaryEntry(DiaryEntry diaryEntry){
        tripRepository.updateDiaryEntry(diaryEntry);
    }
}
