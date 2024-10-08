package com.example.nationalparks;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.nationalparks.adaptor.CustomInfoWindow;
import com.example.nationalparks.data.Repository;
import com.example.nationalparks.model.Park;
import com.example.nationalparks.model.ParkViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.nationalparks.databinding.ActivityMapsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener {

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

        //Getting Ready Custom Window
        mMap.setInfoWindowAdapter(new CustomInfoWindow(getApplicationContext()));

        /*Registering This map to receiving the acknowledge
            the click event when the window is clicked
        * */
        mMap.setOnInfoWindowClickListener(this);
        parkList= new ArrayList<>();
        parkList.clear();

        // Add a marker in Sydney and move the camera


        Repository.getParks(parks -> {
            parkList=parks;
            for (Park park:parks)
            {
                LatLng location = new LatLng(Double.parseDouble(park.getLatitude()),
                        Double.parseDouble(park.getLongitude()));
                MarkerOptions markerOptions=
                        new MarkerOptions()
                                .position(location)
                                .title(park.getName())
                                //Changing the Icon Color Of Marker
                                .icon(BitmapDescriptorFactory.defaultMarker(
                                        BitmapDescriptorFactory.HUE_GREEN
                                ))
                                .snippet(park.getStates());

                Marker marker= mMap.addMarker(markerOptions); //Adding marker
                marker.setTag(park); //adding object to marker so can use anywhere.

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,8));
                Log.d("Parks","onMapReady :"+park.getFullName());
            }
            parkViewModel.setSelectedParks(parkList);
        });
    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        goToDetailsFragment(marker);
    }

    private void goToDetailsFragment(@NonNull Marker marker) {
        //go to DetailsFragment
        parkViewModel.setSelectPark((Park) marker.getTag());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.map,DetailsFragment.newInstance())
                .commit();
    }
}