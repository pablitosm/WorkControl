package com.workcontrol.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

    private static Context context;

    private FusedLocationProviderClient fusedLocationClient;
    public final List<LatLng> polylinePoints = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        InicioAdmin.context = getApplicationContext();
        setNavigationViewListener();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Verificar si se tienen los permisos de ubicación
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Los permisos ya están concedidos, solicita actualizaciones de ubicación
            startLocationUpdates();
        } else {
            // Los permisos no están concedidos, solicitarlos al usuario
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        }

    }

    private void startLocationUpdates() {
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        // Utiliza la ubicación actualizada como desees
        // Por ejemplo, mostrar las coordenadas en un TextView
        // Log.d(TAG, "Ubicación: Latitud " + latitude + ", Longitud " + longitude);


        polylinePoints.add(new LatLng(latitude, longitude));

        Log.d(TAG, "onLocationChanged: " + polylinePoints.toString());



        final Polyline polyline = mMap.addPolyline(new PolylineOptions()
                .addAll(polylinePoints)
                .color(Color.rgb(238, 164, 65))
                .width(10));

        polyline.setStartCap(new RoundCap());
//         polyline.setEndCap();
//        polyline.setEndCap(
//                new CustomCap(BitmapDescriptorFactory.fromResource(R.drawable.arrow),
//                        16));


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // El usuario concedió el permiso de ubicación, solicita actualizaciones de ubicación
                startLocationUpdates();
            } else {
                // El usuario denegó el permiso de ubicación, muestra un mensaje o realiza alguna acción apropiada
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
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

    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nvView);
        navigationView.setNavigationItemSelectedListener(this);
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