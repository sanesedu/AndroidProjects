package com.sanesedu.hikerswatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    String provider;
    TextView latTV;
    TextView lngTV;
    TextView accuracyTV;
    TextView speedTV;
    TextView bearingTV;
    TextView altitudeTV;
    TextView addressTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latTV = (TextView) findViewById(R.id.lat);
        lngTV = (TextView) findViewById(R.id.lng);
        accuracyTV = (TextView) findViewById(R.id.accuracy);
        speedTV = (TextView) findViewById(R.id.speed);
        bearingTV = (TextView) findViewById(R.id.bearing);
        altitudeTV = (TextView) findViewById(R.id.altitude);
        addressTV = (TextView) findViewById(R.id.address);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null){

            onLocationChanged(location);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, 400, 5, this);
    }

    @Override
    public void onLocationChanged(Location location) {

        double lat = location.getLatitude();
        double lng = location.getLongitude();
        double alt = location.getAltitude();
        float bearing = location.getBearing();
        float speed = location.getSpeed();
        float accuracy = location.getAccuracy();

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {

            List<Address> addressList = geocoder.getFromLocation(lat, lng, 1);

            if (addressList != null && addressList.size() > 0){

                String addressHolder = "";

                for (int i = 0; i <= addressList.get(0).getMaxAddressLineIndex(); i++){

                    addressHolder += addressList.get(0).getAddressLine(i) + "\n";

                }

                addressTV.setText("Address:\r\n" + addressHolder);

            }

        } catch (IOException e) {

            e.printStackTrace();
        }

        DecimalFormat df = new DecimalFormat("#.##");

        latTV.setText("Latitude: " + String.valueOf(df.format(lat)));
        lngTV.setText("Longitude: " + String.valueOf(df.format(lng)));
        accuracyTV.setText("Accuracy: " + accuracy + "m");
        speedTV.setText("Speed: " + String.valueOf(speed) + "m/s");
        bearingTV.setText("Bearing: " + String.valueOf(bearing));
        altitudeTV.setText("Altitude: " + String.valueOf(alt) + "m");

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
