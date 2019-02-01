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
import com.ranferi.ssrsi.helper.PlaceLab;
import com.ranferi.ssrsi.helper.ViewPagerAdapter;
import com.ranferi.ssrsi.model.Place;
import com.rd.PageIndicatorView;

public class PlaceFragment extends Fragment {

    private static final String ARG_PLACE_ID = "place_id";

    private Place mPlace;
    private TextView mNameField;
    private TextView mAddressField;
    private CheckBox mLikedCheckBox;
    private CheckBox mMusicCheckBox;
    private Button mDateButton;
    private ViewPager mViewPager;

    public PlaceFragment() {
    }

    public static PlaceFragment newInstance(int placeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PLACE_ID, placeId);
        PlaceFragment fragment = new PlaceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mPlace = new Place1();
        // UUID placeId = (UUID) getActivity().getIntent()
        //        .getSerializableExtra(PlaceActivity.EXTRA_CRIME_ID); private access
        int placeId = (int) getArguments().getSerializable(ARG_PLACE_ID);
        mPlace = PlaceLab.get(getActivity()).getPlace(placeId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_place, container, false);

        mNameField = (TextView) v.findViewById(R.id.place_name);
        mNameField.setText(mPlace.getNombres().get(0).getNombreSitio());

        mAddressField = (TextView) v.findViewById(R.id.place_address);
        mAddressField.setText(mPlace.getNombres().get(0).getNombreSitio());

        mLikedCheckBox = (CheckBox)v.findViewById(R.id.place_like);
        mLikedCheckBox.setChecked(mPlace.isMusica());
        mLikedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // mPlace.setLiked(isChecked);
            }
        });

        mMusicCheckBox = (CheckBox)v.findViewById(R.id.place_music);
        mMusicCheckBox.setChecked(mPlace.isMusica());
        mMusicCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                mPlace.setMusica(isChecked);
            }
        });

        mDateButton = (Button) v.findViewById(R.id.crime_date);
        mDateButton.setText(mPlace.getDireccion());
        mDateButton.setEnabled(false);



        mViewPager = (ViewPager) v.findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity());
        mViewPager.setAdapter(viewPagerAdapter);

        final PageIndicatorView pageIndicatorView = v.findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setCount(viewPagerAdapter.getCount()); // especifica el total de indicadores

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                pageIndicatorView.setSelection(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });



        return v;
    }
}
