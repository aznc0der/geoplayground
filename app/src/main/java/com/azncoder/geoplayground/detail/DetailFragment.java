package com.azncoder.geoplayground.detail;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.azncoder.geoplayground.IntentIdentifier;
import com.azncoder.geoplayground.R;
import com.azncoder.geoplayground.common.BaseFragment;
import com.azncoder.geoplayground.data.local.Delivery;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;

/**
 * Created by aznc0der on 3/1/2018.
 */

public class DetailFragment extends BaseFragment implements DetailView, OnMapReadyCallback {
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.iv_image)
    SimpleDraweeView ivImage;
    private GoogleMap mMap;
    private DetailPresenter mPresenter;
    private boolean isTabletMode;

    @Override
    public int getLayoutViewId() {
        return R.layout.fragment_detail;
    }

    @Override
    public int getToolbarTitle() {
        return R.string.title_delivery_detail_activity;
    }

    @Override
    public boolean enableHomeAsUp() {
        return true;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Delivery delivery = getArguments().getParcelable(IntentIdentifier.DELIVERY_ITEM);
        isTabletMode = getArguments().getBoolean(IntentIdentifier.IS_TABLET_MODE, false);
        if (delivery != null) {
            mPresenter = new DetailPresenter(this, delivery);
            mPresenter.setDeliveryObject();
        }
        if (!isTabletMode) {
            tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        }
        tvDescription.setMovementMethod(new ScrollingMovementMethod());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.support_map_fragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void updateDescription(String description, String address, String imageUrl) {
        String phrase = getString(R.string.label_at, description, address);
        tvDescription.setText(phrase);
        ivImage.setImageURI(imageUrl);
    }

    @Override
    public void addMarkerToMap(LatLng point, float zoomLevel) {
        if (mMap != null) {
            CameraPosition cameraPosition = new CameraPosition.Builder().target(point).zoom(zoomLevel).build();
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            mMap.addMarker(new MarkerOptions()
                    .position(point)
                    .icon(bitmapDescriptorFromVector(isTabletMode ? R.drawable.ic_pin_large : R.drawable.ic_pin))
                    .anchor(0.35f, 0.85f));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mPresenter.onMapReady();
    }

    private BitmapDescriptor bitmapDescriptorFromVector(int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(getActivity(), vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
