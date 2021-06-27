package com.example.myapplication;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.myapplication.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Intent intent = getIntent();
        String berg = intent.getExtras().getString("berg");
        float zoom = getIntent().getFloatExtra("zoom", 15);
        // Add a marker in Sydney and move the camera
        LatLng Marker = new LatLng(0,0);
        if(berg.equals("stuhlberg_cut")){
             Marker = new LatLng(49.542395, 9.020482);
            mMap.addMarker(new MarkerOptions().position(Marker).title("Falkenberg"));

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Marker,10.0f));
             }
        if(berg.equals("barockschloss")){
            Marker = new LatLng(49.482959, 8.459597);
            mMap.addMarker(new MarkerOptions().position(Marker).title("Barockschloss"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Marker,10.0f));}
        if(berg.equals("weisser_stein")){
            Marker = new LatLng(49.4536241,8.7145782);
            mMap.addMarker(new MarkerOptions().position(Marker).title("Weisser Stein"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Marker,10.0f));}
        if(berg.equals("heiligenberg")){
            Marker = new LatLng(47.8412777,9.2357223);
            mMap.addMarker(new MarkerOptions().position(Marker).title("Heiligenberg"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Marker,10.0f));}
        if(berg.equals("wildgehege_karlstern")){
            Marker = new LatLng(49.5329372,8.5152871);
            mMap.addMarker(new MarkerOptions().position(Marker).title("Wildgehege"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Marker,10.0f));}
        if(berg.equals("schloss_schwetzingen")){
            Marker = new LatLng(49.3841299,8.5683959);
            mMap.addMarker(new MarkerOptions().position(Marker).title("Schloss Schwetzingen"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Marker,10.0f));}
        if(berg.equals("taubenkopf")){
            Marker = new LatLng(49.6459921,7.7967165);
            mMap.addMarker(new MarkerOptions().position(Marker).title("Barockschloss"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Marker,10.0f));}
    }
}