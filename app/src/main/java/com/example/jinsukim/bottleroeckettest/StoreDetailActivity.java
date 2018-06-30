package com.example.jinsukim.bottleroeckettest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jinsukim.bottleroeckettest.models.StoreModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class StoreDetailActivity extends FragmentActivity implements OnMapReadyCallback {
    public static final String EXTRA_STORE_DETAIL = "EXTRA_STORE_DETAIL";
    private GoogleMap mMap;
    private StoreModel mStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);

        mStore = (StoreModel) getIntent().getExtras().getSerializable(EXTRA_STORE_DETAIL);
        initUI();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng storeLatLng = new LatLng(mStore.getLat(), mStore.getLng());
        mMap.addMarker(new MarkerOptions().position(storeLatLng).title(mStore.getName()));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(storeLatLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(storeLatLng, 16));
    }

    private void initUI(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ((TextView) findViewById(R.id.name)).setText(mStore.getName());
        ((TextView) findViewById(R.id.address)).setText(mStore.getAddress());
        ((TextView) findViewById(R.id.city)).setText(mStore.getCity());
        ((TextView) findViewById(R.id.state)).setText(", " + mStore.getState());
        ((TextView) findViewById(R.id.phone)).setText(mStore.getPhone());
        ((TextView) findViewById(R.id.zipcode)).setText(", " + mStore.getZip());


        //load thumbnail from url
        Glide.with(StoreDetailActivity.this).load(mStore.getLogoURL())
                .into(((ImageView) findViewById(R.id.iv_thumbnail)));
    }
}