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
import com.ranferi.ssrsi.helper.PlaceLab;
import com.ranferi.ssrsi.model.Place;

import java.util.List;

public class PlaceListFragment extends Fragment {
    private RecyclerView mPlaceRecyclerView;
    private PlaceAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
            mAdapter = new PlaceAdapter(crimes);
            mPlaceRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class PlaceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNameTextView;
        private TextView mAddressTextView;
        private Place mPlace;
        private ImageView mSolvedImageView;

        public PlaceHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_place, parent, false));
            itemView.setOnClickListener(this);
            mNameTextView = (TextView) itemView.findViewById(R.id.place_name);
            mAddressTextView = (TextView) itemView.findViewById(R.id.place_address);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.place_solved);
        }

        public void bind(Place place) {
            mPlace = place;
            mNameTextView.setText(mPlace.getNombre());
            mAddressTextView.setText(mPlace.getDate().toString());
            mSolvedImageView.setVisibility(place.isMusica() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {
            //Intent intent = new Intent(getActivity(), PlaceActivity.class);
            // Intent intent = PlaceActivity.newIntent(getActivity(), mPlace.getId()); CrimeActivity
            Intent intent = PlacePagerActivity.newIntent(getActivity(), mPlace.getId()); // PlacePagerActivity
            startActivity(intent);

            //Toast.makeText(getActivity(),
            //        mPlace.getNombre() + " clicked!", Toast.LENGTH_SHORT)
            //        .show();
        }
    }

    private class PlaceAdapter extends RecyclerView.Adapter<PlaceHolder> {
        private List<Place> mPlaces;

        public PlaceAdapter(List<Place> places) {
            mPlaces = places;
        }

        @NonNull
        @Override
        public PlaceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new PlaceHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull PlaceHolder placeHolder, int i) {
            Place place = mPlaces.get(i);
            placeHolder.bind(place);
        }

        @Override
        public int getItemCount() {
            return mPlaces.size();
        }
    }
}
