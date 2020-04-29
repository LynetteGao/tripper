package com.tripper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;



/* This class is the layout of the trip overview page with the bottomnavigation*/

public class TripOverview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);  //custom theme for app
        setContentView(R.layout.activity_trip_overview);

        BottomNavigationView bottomNav = findViewById(R.id.tripoverview_bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        if (savedInstanceState == null) {
            bottomNav.setSelectedItemId(R.id.nav_map);
        }
    }

    //This method is to set up the bottom navigation function
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_map:
                            selectedFragment = new OverviewMapFragment();
                            break;
                        case R.id.nav_plan:
                            selectedFragment = new OverviewPlanFragment();
                            selectedFragment.setArguments(getIntent().getExtras());
                            break;
                        case R.id.nav_diary:
                            selectedFragment = new OverviewDiaryFragment();
                            break;


                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
             };
}
