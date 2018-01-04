package com.azncoder.geoplayground.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.azncoder.geoplayground.BaseActivity;
import com.azncoder.geoplayground.IntentIdentifier;
import com.azncoder.geoplayground.MainApplication;
import com.azncoder.geoplayground.R;
import com.azncoder.geoplayground.data.local.Delivery;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by aznc0der on 2/1/2018.
 */

public class DetailActivity extends BaseActivity implements OnMapReadyCallback {
    private Delivery mDelivery;

    private final float ZOOM_LEVEL = 20f;

    @Override
    public int getContentViewId() {
        return R.layout.activity_delivery_detail;
    }

    @Override
    public int getToolbarTitle() {
        return R.string.title_delivery_detail_activity;
    }

    @Override
    public void renderView() {
        FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        mTransaction.add(R.id.map_fragment, mapFragment);
        mTransaction.commit();
        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainApplication) getApplication()).getComponents().inject(this);
        Intent intent = getIntent();
        mDelivery = intent.getParcelableExtra(IntentIdentifier.DELIVERY_ITEM);
        String phrase = getString(R.string.label_at, mDelivery.getDescription(), mDelivery.getLocation().getAddress());
        ((TextView) findViewById(R.id.tv_description)).setText(phrase);
        ((SimpleDraweeView) findViewById(R.id.iv_image)).setImageURI(mDelivery.getImageUrl());
        ((TextView) findViewById(R.id.tv_description)).setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng point = new LatLng(mDelivery.getLocation().getLat(), mDelivery.getLocation().getLng());
        CameraPosition cameraPosition = new CameraPosition.Builder().target(point).zoom(ZOOM_LEVEL).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        googleMap.addMarker(new MarkerOptions()
                .position(point)
                .icon(bitmapDescriptorFromVector(R.drawable.ic_pin))
                .anchor(0.35f, 0.85f));
    }

    private BitmapDescriptor bitmapDescriptorFromVector(int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(this, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
