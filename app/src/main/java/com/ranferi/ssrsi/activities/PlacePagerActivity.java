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
import com.ranferi.ssrsi.model.Place;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class PlacePagerActivity extends AppCompatActivity {

    private static final String EXTRA_PLACE_ID = "com.ranferi.ssrsi.place_id";

    private Realm realm;

    public static Intent newIntent(Context packageContext, int placeId) {
        Intent intent = new Intent(packageContext, PlacePagerActivity.class);
        intent.putExtra(EXTRA_PLACE_ID, placeId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_pager);

        int placeId = (int) getIntent()
                .getSerializableExtra(EXTRA_PLACE_ID);

        realm = Realm.getDefaultInstance();

        FragmentManager fragmentManager = getSupportFragmentManager();
        RealmQuery<Place> placesList = realm.where(Place.class);
        RealmResults<Place> places = placesList.sort("id").findAll();

        ViewPager viewPager = findViewById(R.id.place_view_pager);
        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Place place = places.get(position);
                return PlaceFragment.newInstance(place.getId());
            }
            @Override
            public int getCount() {
                return places.size();
            }
        });

        for (int i = 0; i < places.size(); i++) {
            if (places.get(i).getId() == placeId) {
                viewPager.setCurrentItem(i);
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
