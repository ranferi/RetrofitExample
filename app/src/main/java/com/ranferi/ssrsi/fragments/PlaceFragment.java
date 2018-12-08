package com.ranferi.ssrsi.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ranferi.ssrsi.R;
import com.ranferi.ssrsi.helper.PlaceLab;
import com.ranferi.ssrsi.model.Place;

import java.util.UUID;

public class PlaceFragment extends Fragment {

    private static final String ARG_PLACE_ID = "place_id";

    private Place mPlace;
    private TextView mNameField;
    private TextView mAddressField;
    private CheckBox mLikedCheckBox;
    private CheckBox mMusicCheckBox;

    private Button mDateButton;

    public static PlaceFragment newInstance(UUID placeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PLACE_ID, placeId);
        PlaceFragment fragment = new PlaceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mPlace = new Place();
        // UUID placeId = (UUID) getActivity().getIntent()
        //        .getSerializableExtra(PlaceActivity.EXTRA_CRIME_ID); private access
        UUID placeId = (UUID) getArguments().getSerializable(ARG_PLACE_ID);
        mPlace = PlaceLab.get(getActivity()).getPlace(placeId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_place, container, false);

        mNameField = (TextView) v.findViewById(R.id.place_name);
        mNameField.setText(mPlace.getNombre());

        mAddressField = (TextView) v.findViewById(R.id.place_address);
        mAddressField.setText(mPlace.getNombre() + "!");

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
        mDateButton.setText(mPlace.getDate().toString());
        mDateButton.setEnabled(false);

        return v;
    }
}
