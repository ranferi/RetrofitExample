package com.ranferi.ssrsi.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ranferi.ssrsi.R;
import com.ranferi.ssrsi.helper.ViewPagerAdapter;
import com.ranferi.ssrsi.model.Place;
import com.rd.PageIndicatorView;

import io.realm.Realm;
import io.realm.RealmQuery;

public class PlaceFragment extends Fragment {

    private static final String ARG_PLACE_ID = "place_id";

    private Realm realm;

    public PlaceFragment() { }

    public static PlaceFragment newInstance(int placeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PLACE_ID, placeId);
        PlaceFragment fragment = new PlaceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_place, container, false);
        int placeId = (int) getArguments().getSerializable(ARG_PLACE_ID);
        realm = Realm.getDefaultInstance();

        RealmQuery<Place> query = realm.where(Place.class);
        Place place = query.equalTo("id", placeId).findFirst();

        TextView nameField = (TextView) v.findViewById(R.id.place_name);
        nameField.setText(place.getNombres().get(0).getNombreSitio());

        TextView addressField = (TextView) v.findViewById(R.id.place_address);
        addressField.setText(place.getNombres().get(0).getNombreSitio());

        CheckBox likedCheckBox = (CheckBox) v.findViewById(R.id.place_like);
        likedCheckBox.setChecked(place.isMusica());
        likedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // mPlace.setLiked(isChecked);
            }
        });

        CheckBox musicCheckBox = (CheckBox) v.findViewById(R.id.place_music);
        musicCheckBox.setChecked(place.isMusica());
        musicCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                place.setMusica(isChecked);
            }
        });

        Button dateButton = (Button) v.findViewById(R.id.crime_date);
        dateButton.setText(place.getDireccion());
        dateButton.setEnabled(false);

        ViewPager viewPager = (ViewPager) v.findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity());
        viewPager.setAdapter(viewPagerAdapter);

        final PageIndicatorView pageIndicatorView = v.findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setCount(viewPagerAdapter.getCount()); // especifica el total de indicadores

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageSelected(int i) {
                pageIndicatorView.setSelection(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) { }
        });

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realm.close();
    }
}
