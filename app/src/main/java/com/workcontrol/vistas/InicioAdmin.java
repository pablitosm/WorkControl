package com.workcontrol.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.navigation.NavigationView;
import com.workcontrol.MainActivity;
import com.workcontrol.R;

import java.util.ArrayList;
import java.util.List;

public class InicioAdmin extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener, LocationListener {


    private GoogleMap mMap;
    GroundOverlay groundOverlay;

    private final List<BitmapDescriptor> images = new ArrayList<>();

    private static final String TAG = "MainActivity";
    int LOCATION_REQUEST_CODE = 10001;

    LocationManager locationManager;
    LocationListener locationListener;
    LatLng userLatLong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        locationListener = location -> {
            userLatLong = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(userLatLong).title("Ubicacion"));
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

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            final List<LatLng> polylinePoints = new ArrayList<>();
            polylinePoints.add(new LatLng(41.296692593054866, -1.5034166735097924));
            polylinePoints.add(new LatLng(41.290326593054866, -1.5034166735097924));
            polylinePoints.add(new LatLng(41.296702593054866, -1.4959166735097924));
            polylinePoints.add(new LatLng(41.290326593054866, -1.4959166735097924));


            final Polyline polyline = mMap.addPolyline(new PolylineOptions()
                    .addAll(polylinePoints)
                    .color(Color.rgb(238, 164, 65))
                    .width(10));

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.mapa:
                startActivity(new Intent(InicioAdmin.this, InicioAdmin.class));
                break;
            case R.id.panel_control:
                startActivity(new Intent(InicioAdmin.this, panel_control.class));
                break;
            case R.id.iniciar_trabajo:
                startActivity(new Intent(InicioAdmin.this, trabajo.class));
                break;

            case R.id.informes:
                startActivity(new Intent(InicioAdmin.this, informes.class));
                break;
            case R.id.turnos:
                startActivity(new Intent(InicioAdmin.this, turnos.class));
                break;

            case R.id.maquinaria:
                startActivity(new Intent(InicioAdmin.this, maquinaria.class));
                break;
            case R.id.operarios:
                startActivity(new Intent(InicioAdmin.this, operarios.class));
                break;
            case R.id.cerrar_sesion:
                startActivity(new Intent(InicioAdmin.this, MainActivity.class));
                break;

        }
        return true;
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