package com.tripper;

import androidx.appcompat.app.AppCompatActivity;
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
import com.tripper.db.entities.Trip;
import com.tripper.viewmodels.CreateNewTripViewModel;

import java.util.Arrays;
import java.util.Calendar;

public class CreateNewTrip extends AppCompatActivity {

    private int mYear, mMonth, mDay;
    private Calendar startDate;
    private Calendar endDate;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_create_new_trip);

        txtEditStartDate = findViewById(R.id.txtEditStartDate);
        txtEditEndDate = findViewById(R.id.txtEditEndDate);
        txtInputStartDate = findViewById(R.id.txtInputStartDate);
        txtInputEndDate = findViewById(R.id.txtInputEndDate);
        txtEditTripName = findViewById(R.id.txtEditTripName);
        txtInputTripName = findViewById(R.id.txtInputTripName);
        txtPlaceError = findViewById(R.id.txtPlaceError);

        Button btnCreateTrip = findViewById(R.id.btnCreateTrip);

        tripViewModel = new ViewModelProvider(this).get(CreateNewTripViewModel.class);

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

                    Trip trip = new Trip();
                    trip.name = txtEditTripName.getText().toString();
                    trip.startDate = startDate;
                    trip.endDate = endDate;
                    trip.locationLat = Double.toString(tripPlace.getLatLng().latitude);
                    trip.locationLon = Double.toString(tripPlace.getLatLng().longitude);
                    trip.destination = tripPlace.getName();
                    tripViewModel.insert(trip);

                    Intent intent = new Intent(v.getContext(), TagSuggestion.class);
                    v.getContext().startActivity(intent);
                }
                else {
                    return;
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
                        calendar.set(year, month, dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

}
