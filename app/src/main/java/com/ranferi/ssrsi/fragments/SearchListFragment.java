package com.ranferi.ssrsi.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ranferi.ssrsi.R;
import com.ranferi.ssrsi.helper.PlaceVisitedAdapter;
import com.ranferi.ssrsi.helper.SharedPrefManager;
import com.ranferi.ssrsi.model.PlacesResponse;

import org.parceler.Parcels;

public class SearchListFragment extends Fragment {
    private PlacesResponse mPlacesResponse;

    public SearchListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPlacesResponse = Parcels.unwrap(getArguments().getParcelable("places"));
        return inflater.inflate(R.layout.fragment_place_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() != null) getActivity().setTitle("Busqueda");

        final int user = SharedPrefManager.getInstance(getActivity()).getUser().getId();

        RecyclerView placeRecyclerView = view.findViewById(R.id.place_recycler_view);
        placeRecyclerView.setHasFixedSize(true);
        placeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerView.Adapter adapter = new PlaceVisitedAdapter(mPlacesResponse.getPlaces(), getActivity(), user);
        placeRecyclerView.setAdapter(adapter);

    }


}
