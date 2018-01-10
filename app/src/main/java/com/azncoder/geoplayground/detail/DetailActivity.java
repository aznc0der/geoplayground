package com.azncoder.geoplayground.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.azncoder.geoplayground.common.BaseActivity;
import com.azncoder.geoplayground.IntentIdentifier;
import com.azncoder.geoplayground.R;

/**
 * Created by aznc0der on 2/1/2018.
 */

public class DetailActivity extends BaseActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = new Bundle();
        arguments.putParcelable(IntentIdentifier.DELIVERY_ITEM, getIntent().getParcelableExtra(IntentIdentifier.DELIVERY_ITEM));
        arguments.putBoolean(IntentIdentifier.IS_TABLET_MODE, false);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
