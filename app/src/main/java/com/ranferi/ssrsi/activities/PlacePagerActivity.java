package com.ranferi.ssrsi.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ranferi.ssrsi.R;
import com.ranferi.ssrsi.fragments.PlaceFragment;
import com.ranferi.ssrsi.helper.PlaceLab;
import com.ranferi.ssrsi.model.Place;

import java.util.List;
import java.util.UUID;

public class PlacePagerActivity extends AppCompatActivity {

    private static final String EXTRA_CRIME_ID =
            "com.bignerdranch.android.criminalintent.place_id";

    private ViewPager mViewPager;
    private List<Place> mPlaces;

    public static Intent newIntent(Context packageContext, UUID placeId) {
        Intent intent = new Intent(packageContext, PlacePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, placeId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_pager);

        UUID placeId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_CRIME_ID);


        mViewPager = (ViewPager) findViewById(R.id.place_view_pager);
        mPlaces = PlaceLab.get(this).getPlaces();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Place place = mPlaces.get(position);
                return PlaceFragment.newInstance(place.getId());
            }
            @Override
            public int getCount() {
                return mPlaces.size();
            }
        });

        for (int i = 0; i < mPlaces.size(); i++) {
            if (mPlaces.get(i).getId().equals(placeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
