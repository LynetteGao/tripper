package com.tripper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.tripper.db.entities.Day;
import com.tripper.db.entities.DaySegment;
import com.tripper.db.entities.Trip;
import com.tripper.db.relationships.DaySegmentWithEvents;
import com.tripper.db.relationships.DayWithSegmentsAndEvents;
import com.tripper.db.relationships.TripWithDaysAndDaySegments;
import com.tripper.viewmodels.CreateNewTripViewModel;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;

public class CreateNewTrip extends AppCompatActivity {

    private int mYear, mMonth, mDay;
    private Calendar startDate;
    private Calendar endDate;
    private TextView txtHeader;
    private TextInputEditText txtEditStartDate;
    private TextInputEditText txtEditEndDate;
    private TextInputLayout txtInputStartDate;
    private TextInputLayout txtInputEndDate;
    private TextInputEditText txtEditTripName;
    private TextInputLayout txtInputTripName;
    private TextView txtPlaceError;
    private CreateNewTripViewModel tripViewModel;
    private AutocompleteSupportFragment autocompleteSupportFragment;
    private Place tripPlace;
    private TripWithDaysAndDaySegments tripToUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_create_new_trip);

        txtHeader = findViewById(R.id.lblNewTripHeader);
        txtEditStartDate = findViewById(R.id.txtEditStartDate);
        txtEditEndDate = findViewById(R.id.txtEditEndDate);
        txtInputStartDate = findViewById(R.id.txtInputStartDate);
        txtInputEndDate = findViewById(R.id.txtInputEndDate);
        txtEditTripName = findViewById(R.id.txtEditTripName);
        txtInputTripName = findViewById(R.id.txtInputTripName);
        txtPlaceError = findViewById(R.id.txtPlaceError);
        Button btnCreateTrip = findViewById(R.id.btnCreateTrip);
        tripViewModel = new ViewModelProvider(this).get(CreateNewTripViewModel.class);

        long tripId = getIntent().getLongExtra("tripId", -1);

        if (tripId != -1) {
            tripToUpdate = tripViewModel.getTrip(tripId);
            txtHeader.setText("Update " + tripToUpdate.trip.name);
            btnCreateTrip.setText("Update Trip");
        }

        txtEditStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDate = Calendar.getInstance();
                txtInputStartDate.setError(null);
                getDateText(txtEditStartDate, startDate);
            }
        });

        txtEditEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endDate = Calendar.getInstance();
                txtInputEndDate.setError(null);
                getDateText(txtEditEndDate, endDate);
            }
        });

        btnCreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputNotNull()) {
                    Long startMillis = startDate.getTimeInMillis();
                    Long endMillis = endDate.getTimeInMillis();

                    boolean sameDate = startDate.get(Calendar.YEAR) == endDate.get(Calendar.YEAR)
                            && startDate.get(Calendar.DAY_OF_YEAR) == endDate.get(Calendar.DAY_OF_YEAR);

                    if (startMillis >= endMillis || sameDate) {
                        txtInputEndDate.setError("End date must be later than start date");
                        return;
                    }

                    // create a new trip and all the days
                    if (tripToUpdate == null) {
                        Trip trip = new Trip();
                        trip.name = txtEditTripName.getText().toString();
                        trip.startDate = startDate;
                        trip.endDate = endDate;
                        trip.locationLat = Double.toString(tripPlace.getLatLng().latitude);
                        trip.locationLon = Double.toString(tripPlace.getLatLng().longitude);
                        trip.destination = tripPlace.getName();
                        Long tripId = tripViewModel.insertTrip(trip);

                        Calendar curDate = trip.startDate;
                        while (curDate.getTimeInMillis() <= trip.endDate.getTimeInMillis()) {
                            Day day = new Day();
                            day.date = curDate;
                            day.tripId = tripId;
                            Long dayId = tripViewModel.insertDay(day);

                            for (int i = 0; i < 3; i++) {
                                DaySegment daySegment = new DaySegment();
                                daySegment.dayId = dayId;
                                daySegment.segment = i;
                                tripViewModel.insertDaySegment(daySegment);
                            }
                            curDate.add(Calendar.DATE, 1);
                        }

                        Intent intent = new Intent(getApplicationContext(), TagSuggestion.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("tripId", tripId);
                        getApplicationContext().startActivity(intent);
                    }
                    else {
                        Calendar oldStart = tripToUpdate.trip.startDate;
                        Calendar oldEnd = tripToUpdate.trip.endDate;

                        tripToUpdate.trip.name = txtEditTripName.getText().toString();
                        tripToUpdate.trip.startDate = startDate;
                        tripToUpdate.trip.endDate = endDate;
                        tripToUpdate.trip.locationLat = Double.toString(tripPlace.getLatLng().latitude);
                        tripToUpdate.trip.locationLon = Double.toString(tripPlace.getLatLng().longitude);
                        tripToUpdate.trip.destination = tripPlace.getName();
                        tripViewModel.updateTrip(tripToUpdate.trip);

                        // check what days need to be added or deleted
                        List<DayWithSegmentsAndEvents> days = tripToUpdate.days;
                        Calendar curDate = startDate;
                        int dayIndex = 0;
                        while (curDate.getTimeInMillis() <= endDate.getTimeInMillis()) {
                            if (dayIndex < days.size()) {
                                days.get(dayIndex).day.date = curDate;
                                tripViewModel.updateDay(days.get(dayIndex).day);
                            }
                            else {
                                // need to insert a new day
                                Log.d("update trip", "add new day");
                                Day day = new Day();
                                day.date = curDate;
                                day.tripId = tripId;
                                Long dayId = tripViewModel.insertDay(day);

                                for (int i = 0; i < 3; i++) {
                                    DaySegment daySegment = new DaySegment();
                                    daySegment.dayId = dayId;
                                    daySegment.segment = i;
                                    tripViewModel.insertDaySegment(daySegment);
                                }
                            }

                            dayIndex++;
                            curDate.add(Calendar.DATE, 1);
                        }

                        for (int i = dayIndex; i < days.size(); i++) {
                            // there are leftover days, delete them
                            Log.d("update trip", "delete old day");
                            tripViewModel.deleteDay(days.get(i).day);
                        }

                        Intent intent = new Intent(getApplicationContext(), HomePage.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);

                    }
                }
            }
        });


        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.places_api_key));
        }

        autocompleteSupportFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteSupportFragment.setHint("Search destinations");
        autocompleteSupportFragment.setTypeFilter(TypeFilter.REGIONS);
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                tripPlace = place;
                txtEditTripName.setText("Trip to " + tripPlace.getName());
                txtPlaceError.setVisibility(View.INVISIBLE);
                Log.i("place", "Place: " + tripPlace.getLatLng() + ", " + tripPlace.getId());
            }

            @Override
            public void onError(Status status) {
                txtEditTripName.setText("");
                tripPlace = null;
                Log.i("place", "An error occurred: " + status);
            }
        });
    }

    private boolean inputNotNull() {
        boolean valid = true;
        if (txtEditStartDate.getText() == null || txtEditStartDate.getText().toString().isEmpty()) {
            txtInputStartDate.setError("Start date is required");
            valid = false;
        }
        if (txtEditEndDate.getText() == null || txtEditEndDate.getText().toString().isEmpty()) {
            txtInputEndDate.setError("End date is required");
            valid = false;
        }
        if (txtEditTripName.getText() == null || txtEditTripName.getText().toString().isEmpty()) {
            txtInputTripName.setError("Trip name is required");
            valid = false;
        }

        if (tripPlace == null) {
            valid = false;
            txtPlaceError.setVisibility(View.VISIBLE);
        }

        return valid;
    }
    private void getDateText(final EditText editText, Calendar calendar) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editText.setText((month + 1) + "/" + dayOfMonth + "/" + year);
                        calendar.set(year, month, dayOfMonth, 0, 0, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

}
