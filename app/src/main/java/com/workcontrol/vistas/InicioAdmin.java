package com.workcontrol.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.workcontrol.R;

import java.util.ArrayList;
import java.util.List;

public class InicioAdmin extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener, LocationListener{

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    FirebaseFirestore database;

    private GoogleMap mMap;
    GroundOverlay groundOverlay;
    BitmapDescriptor bitmapDescriptor;

    private final List<BitmapDescriptor> images = new ArrayList<BitmapDescriptor>();


    LocationManager locationManager;
    LocationListener locationListener;
    LatLng userLatLong;

    FusedLocationProviderClient fusedLocationProviderClient;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
        setNavigationViewListener();


    }

    public void getLastLocation () {

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                userLatLong = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(userLatLong).title("Ubicacion"));
            }
        };

        images.clear();
        images.add(BitmapDescriptorFactory.fromResource(R.drawable.png0));
        images.add(BitmapDescriptorFactory.fromResource(R.drawable.png1));
        images.add(BitmapDescriptorFactory.fromResource(R.drawable.png2));
        images.add(BitmapDescriptorFactory.fromResource(R.drawable.png3));

        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabledDuringRotateOrZoom(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        LatLng bmp0 = new LatLng(41.296692593054866, -1.5034166735097924);
        LatLng bmp1 = new LatLng(41.290326593054866, -1.5034166735097924);
        LatLng bmp2 = new LatLng(41.296702593054866, -1.4959166735097924);
        LatLng bmp3 = new LatLng(41.290326593054866, -1.4959166735097924);
//        mMap.addMarker(new MarkerOptions()
//                .position(myta)
//                .title("MYTA"));
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bmp2, 17));

        groundOverlay = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(images.get(0)).anchor(0, 1)
                .position(bmp0, 628, 640));
        groundOverlay = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(images.get(1)).anchor(0, 1)
                .position(bmp1, 628, 710));

        groundOverlay = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(images.get(2)).anchor(0, 1)
                .position(bmp2, 628, 640));


        groundOverlay = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(images.get(3)).anchor(0, 1)
                .position(bmp3, 628, 710));
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.iniciar_trabajo:
                startActivity(new Intent(InicioAdmin.this, trabajo.class));
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nvView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }
}