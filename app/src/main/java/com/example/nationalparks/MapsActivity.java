package com.example.nationalparks;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.nationalparks.data.Repository;
import com.example.nationalparks.model.Park;
import com.example.nationalparks.model.ParkViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.nationalparks.databinding.ActivityMapsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private ParkViewModel parkViewModel;
    private List<Park> parkList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parkViewModel=new ViewModelProvider(this)
                .get(ParkViewModel.class);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        BottomNavigationView bottomNavigationView=
                findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment=null;
            int id =item.getItemId();
            if (id==R.id.maps_nav_button)
            {

                mMap.clear(); //Clear the marker aand rerenders again
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.map,mapFragment)
                        .commit();//without it,It Doesn't Work
                mapFragment.getMapAsync(this);/*this will Help to get Everything
                     Such as markers,or else Map Screen will show you blank Screenc
                */
                return  true;

            }else if (id==R.id.parks_nav_button)
            {
                selectedFragment=ParksFragment.newInstance();
            }
            assert selectedFragment != null;
            getSupportFragmentManager().beginTransaction()
                        .replace(R.id.map,selectedFragment)
                        .commit();

            
            return true;
        });


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
        parkList= new ArrayList<>();
        parkList.clear();

        // Add a marker in Sydney and move the camera


        Repository.getParks(parks -> {
            parkList=parks;
            for (Park park:parks)
            {
                LatLng sydney = new LatLng(Double.parseDouble(park.getLatitude()),
                        Double.parseDouble(park.getLongitude()));
                mMap.addMarker(new MarkerOptions().position(sydney).title(park.getFullName()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,8));
                Log.d("Parks","onMapReady :"+park.getFullName());
            }
            parkViewModel.setSelectedParks(parkList);
        });
    }
}