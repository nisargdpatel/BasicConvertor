package com.example.basicconvertor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private String file = "com.example.basicconvertor";

    private TemperatureFragment temperatureFragment;
    private DistanceFragment distanceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temperatureFragment = new TemperatureFragment();
        distanceFragment = new DistanceFragment();

        sharedPreferences = getSharedPreferences(file, MODE_PRIVATE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.placeHolderLayout, temperatureFragment);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (item.getItemId() == R.id.menu_temperature_id) {
            fragmentTransaction.replace(R.id.placeHolderLayout, temperatureFragment);
        }
        else if (item.getItemId() == R.id.menu_distance_id) {
            fragmentTransaction.replace(R.id.placeHolderLayout, distanceFragment);
        } else {
            return super.onContextItemSelected(item);
        }

        fragmentTransaction.commit();
        return true;
    }




}
