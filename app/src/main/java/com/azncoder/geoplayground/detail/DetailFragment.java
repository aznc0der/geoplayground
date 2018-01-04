package com.azncoder.geoplayground.detail;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.azncoder.geoplayground.IntentIdentifier;
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
 * Created by aznc0der on 3/1/2018.
 */

public class DetailFragment extends Fragment implements OnMapReadyCallback {

    private Delivery mDelivery;
    private final float ZOOM_LEVEL = 20f;
    private GoogleMap googleMap;
    private TextView tvDescription;
    private SimpleDraweeView ivImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().getParcelable(IntentIdentifier.DELIVERY_ITEM) != null) {
            mDelivery = getArguments().getParcelable(IntentIdentifier.DELIVERY_ITEM);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((TextView) view.findViewById(R.id.toolbar).findViewById(R.id.title)).setText(R.string.title_delivery_detail_activity);
        tvDescription = view.findViewById(R.id.tv_description);
        tvDescription.setMovementMethod(new ScrollingMovementMethod());
        ivImage = view.findViewById(R.id.iv_image);
        if (mDelivery != null) {
            updateDescription(mDelivery);
            ivImage.setImageURI(mDelivery.getImageUrl());
            FragmentTransaction mTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            SupportMapFragment mapFragment = SupportMapFragment.newInstance();
            mTransaction.add(R.id.mapFragment, mapFragment);
            mTransaction.commit();
            try {
                MapsInitializer.initialize(getActivity());
            } catch (Exception e) {
                e.printStackTrace();
            }
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        updateCameraPosition(mDelivery);
    }

    public void updateDescription(Delivery delivery) {
        String phrase = getString(R.string.label_at, delivery.getDescription(), delivery.getLocation().getAddress());
        tvDescription.setText(phrase);
        ivImage.setImageURI(delivery.getImageUrl());
    }

    public void updateCameraPosition(Delivery delivery) {
        if (delivery != null && googleMap != null) {
            LatLng point = new LatLng(delivery.getLocation().getLat(), delivery.getLocation().getLng());
            CameraPosition cameraPosition = new CameraPosition.Builder().target(point).zoom(ZOOM_LEVEL).build();
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            googleMap.addMarker(new MarkerOptions()
                    .position(point)
                    .icon(bitmapDescriptorFromVector(R.drawable.ic_pin_large))
                    .anchor(0.35f, 0.85f));
        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(getActivity(), vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onDestroyView() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            getActivity().getSupportFragmentManager().beginTransaction().remove(mapFragment).commit();
        }
        super.onDestroyView();
    }
}
