package com.example.firstaid.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.firstaid.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private String address = "No GPS Found";

    private LocationManager locationManager;
    private static double lat;
    private static double lng;
    private Geocoder geocoder;
    private List<Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        Button startButton = findViewById(R.id.startButton);
        Button viewReportsButton = findViewById(R.id.viewReportsButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });

        viewReportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SavedReportsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void startFirstAid(String location) {
        Intent intent = new Intent(this, FirstAidActivity.class);
        intent.putExtra("Address", location);
        startActivity(intent);
    }

    private void getLocation() {
        geocoder = new Geocoder(this, Locale.getDefault());
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location != null) {
                lat = location.getLatitude();
                lng = location.getLongitude();
                addresses = geocoder.getFromLocation(lat, lng, 1);
                address = addresses.get(0).getAddressLine(0);
            }else{
                lat = 51.279643;
                lng = 1.089364;
                addresses = geocoder.getFromLocation(lat, lng, 1);
                address = addresses.get(0).getAddressLine(0);
            }
        }
        catch(SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        startFirstAid(address);
    }
}
