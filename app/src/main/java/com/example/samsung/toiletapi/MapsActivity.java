package com.example.samsung.toiletapi;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener,OnInfoWindowClickListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);


        for (int i = 0; i < MainActivity.g_idx; i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions
                    .position(new LatLng(MainActivity.w[i], MainActivity.g[i]))
                    .title(MainActivity.s[i]);
            mMap.addMarker(markerOptions);
        }

        //마커 클릭에 대한 이벤트 처리.
        //mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);

        LatLng first = new LatLng(35.1476022, 126.856865);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(first));
    }
    public void onInfoWindowClick(Marker marker){
        Toast.makeText(this,"Info window clicked", Toast.LENGTH_SHORT).show();
    }

    public  boolean onMarkerClick(Marker marker){
        Toast.makeText(this, marker.getTitle() + "\n" + marker.getPosition(), Toast.LENGTH_SHORT).show();
        return true;
    }


}
