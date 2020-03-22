package com.tripper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.tripper.db.entities.Trip;
import com.tripper.viewmodels.CreateNewTripViewModel;

import java.util.Calendar;

public class CreateNewTrip extends AppCompatActivity {

    private int mYear, mMonth, mDay;
    private Calendar startDate;
    private Calendar endDate;
    private TextInputEditText txtEditStartDate;
    private TextInputEditText txtEditEndDate;
    private TextInputLayout txtInputStartDate;
    private TextInputLayout txtInputEndDate;
    private TextInputEditText txtEditDestination;
    private TextInputLayout txtInputDestination;
    private CreateNewTripViewModel tripViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_trip);

        txtEditStartDate = findViewById(R.id.txtEditStartDate);
        txtEditEndDate = findViewById(R.id.txtEditEndDate);
        txtInputStartDate = findViewById(R.id.txtInputStartDate);
        txtInputEndDate = findViewById(R.id.txtInputEndDate);
        txtEditDestination = findViewById(R.id.txtEditDestination);
        txtInputDestination = findViewById(R.id.txtInputDestination);

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
                    trip.name = txtEditDestination.getText().toString();
                    trip.startDate = startDate;
                    trip.endDate = endDate;
                    tripViewModel.insert(trip);

                }
                else {
                    return;
                }
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
        if (txtEditDestination.getText() == null || txtEditDestination.getText().toString().isEmpty()) {
            txtInputDestination.setError("Destination is required");
            valid = false;
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
