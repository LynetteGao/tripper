package com.tripper;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import static android.graphics.Color.CYAN;

public class HomePage extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);  // custom theme for the app!
        setContentView(R.layout.activity_home_page);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        getLocationPermission();

    }

    public void onNewTripClick(View view) {
        Intent intent = new Intent(this, CreateNewTrip.class);
        startActivity(intent);
    }

    //This method is used to test the TripOverview Page
    public void onOverviewClick(View view){
        Intent intent = new Intent(this,TripOverview.class);
        startActivity(intent);
    }
  
    //This method is used to test the TagSuggestion Page
    public void onTagClick(View view){
        Intent intent = new Intent(this,TagSuggestion.class);
        startActivity(intent);
    }
//    public void onSelectClick(View v){
//          System.out.println("clicked");
//          //v.setBackgroundColor(CYAN);
//          ImageButton imgButton = findViewById(R.id.tagImage);
//          TextView txtButton = findViewById(R.id.tagText);
//          imgButton.setBackgroundColor(Color.CYAN);
//          txtButton.setBackgroundColor(CYAN);
//          //Button myBtn = findViewById(R.id.);
//          //boolean selected = false;
//          //selected = true;
//        //ImageButton imgButton = findByID(R.id);
//        //1. Put a bunch of buttons in the recycler view
//        //2. Make an onClick method
//        //3. In that onClick method, change the button's background color
//        //myBtn.setBackgroundColor();
//    }

    private void getLocationPermission() {
        int permission = ActivityCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

}
