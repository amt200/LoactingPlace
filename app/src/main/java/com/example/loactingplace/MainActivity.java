package com.example.loactingplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    Button btnNorth, btnCentral, btnEast;
    TextView tvDetails;
    private GoogleMap map;
    private Marker north, central, east;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);

        tvDetails = findViewById(R.id.tvDetails);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                } else {
                    Log.e("GMap - Permission", "GPS access has not been granted");
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }

                LatLng northLatLng = new LatLng(1.461708, 103.813500);
                LatLng centralLatLng = new LatLng(1.300542, 103.841226);
                LatLng eastLatLng = new LatLng(1.350057, 103.934452);

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(northLatLng, 10));

                UiSettings ui = map.getUiSettings();
                ui.setCompassEnabled(true);
                ui.setZoomControlsEnabled(true);

                north = map.addMarker(new
                        MarkerOptions()
                        .position(northLatLng)
                        .title("HQ North")
                        .snippet("Block 333, Admiralty Ave 3, 765654 "+"\nOperating Hours: 10am - 5pm\n65433456")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                central = map.addMarker(new
                        MarkerOptions()
                        .position(centralLatLng)
                        .title("Central")
                        .snippet("Block 3A, Orchard Ave 3, 134524 "+"\nOperating Hours: 11am - 8pm\n67788652")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                east = map.addMarker(new
                        MarkerOptions()
                        .position(eastLatLng)
                        .title("East")
                        .snippet("Block 555, Tampines Ave, 287728 \"+\"\\nOperating Hours: 9am - 5pm\\n66776677")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        return false;
                    }
                });

            }
        });


        btnNorth = findViewById(R.id.btnNorth);
        btnCentral = findViewById(R.id.btnCentral);
        btnEast = findViewById(R.id.btnEast);

        btnNorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map != null && north != null) {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(north.getPosition(), 15));
                    tvDetails.setText(north.getSnippet());
                }
            }


        });

        btnCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map != null && central != null) {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(central.getPosition(), 15));
                }
            }
        });

        btnEast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map != null && east != null) {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(east.getPosition(), 15));
                }
            }
        });
    }
}