package com.ranferi.ssrsi.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ranferi.ssrsi.R;
import com.ranferi.ssrsi.activities.PlacePagerActivity;
import com.ranferi.ssrsi.helper.PlaceAdapter;
import com.ranferi.ssrsi.helper.PlaceLab;
import com.ranferi.ssrsi.model.Place;

import java.util.List;

public class PlaceListFragment extends Fragment {
    private RecyclerView mPlaceRecyclerView;
    private RecyclerView.Adapter mAdapter;

    public PlaceListFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_list, container, false);
        mPlaceRecyclerView = (RecyclerView) view
                .findViewById(R.id.place_recycler_view);
        mPlaceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        PlaceLab crimeLab = PlaceLab.get(getActivity());
        List<Place> crimes = crimeLab.getPlaces();
        if (mAdapter == null) {
            mAdapter = new PlaceAdapter(crimes, getActivity());
            mPlaceRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }


}
