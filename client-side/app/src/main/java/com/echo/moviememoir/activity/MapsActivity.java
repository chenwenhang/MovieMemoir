package com.echo.moviememoir.activity;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import com.echo.moviememoir.R;
import com.echo.moviememoir.api.BoxMapAPI;
import com.echo.moviememoir.entity.Cinema;
import com.echo.moviememoir.entity.User;
import com.echo.moviememoir.restful.RestClient;
import com.echo.moviememoir.utils.LocalStorage;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private TitleBar titleBar;
    private GoogleMap mMap;
    private LatLng userPosition;
    private LatLng[] cinemaPositions;
    private List<Cinema> cinemas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Strict mode, since new thread must be created after android4.0 if we want to create a request
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        titleBar = findViewById(R.id.maps_title_bar);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        String str = RestClient.findAllCinemas();
        JSONArray res = null;

        User user = LocalStorage.getUser();

        try {
            res = new JSONArray(str);
            for (int i = 0; i < res.length(); i++) {
                Cinema cinema = new Cinema();
                cinema.setCinemaId((int) (res.getJSONObject(i).get("cinemaId")));
                cinema.setLocation((String) (res.getJSONObject(i).get("location")));
                cinema.setPostcode((String) (res.getJSONObject(i).get("postcode")));
                cinema.setCinemaName((String) (res.getJSONObject(i).get("cinemaName")));
                cinemas.add(cinema);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert res != null;
        cinemaPositions = new LatLng[res.length()];
        for (int i = 0; i < res.length(); i++) {
            String[] cinemaPos = BoxMapAPI.getCoordinates(BoxMapAPI.getAddressInfo(cinemas.get(i).getLocation()));
            cinemaPositions[i] = new LatLng(Double.parseDouble(cinemaPos[1]), Double.parseDouble(cinemaPos[0]));
        }

        String[] userPos = BoxMapAPI.getCoordinates(BoxMapAPI.getAddressInfo(user.getAddress()));
        userPosition = new LatLng(Double.parseDouble(userPos[1]), Double.parseDouble(userPos[0]));


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
        mMap.addMarker(new MarkerOptions().position(userPosition).title("My location")
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.drawable.my_location))));
        for (int i = 0; i < cinemaPositions.length; i++) {
            mMap.addMarker(new MarkerOptions().position(cinemaPositions[i]).title(cinemas.get(i).getCinemaName()));
        }

        // Add a marker in Sydney and move the camera
        float zoomLevel = (float) 5.0;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userPosition, zoomLevel));
    }
}
