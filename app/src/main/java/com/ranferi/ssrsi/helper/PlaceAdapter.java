package com.ranferi.ssrsi.helper;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ranferi.ssrsi.R;
import com.ranferi.ssrsi.activities.PlacePagerActivity;
import com.ranferi.ssrsi.model.Place1;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceHolder> {
    private List<Place1> mPlaces;
    private Context sContext;

    public PlaceAdapter(List<Place1> places, Context context) {
        this.mPlaces = places;
        this.sContext = context;
    }

    @NonNull
    @Override
    public PlaceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        return new PlaceHolder(layoutInflater, viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceHolder placeHolder, int i) {
        Place1 place = mPlaces.get(i);
        placeHolder.bind(place);
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }

    public class PlaceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNameTextView;
        private TextView mAddressTextView;
        private Place1 mPlace;
        private ImageView mSolvedImageView;

        public PlaceHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_place, parent, false));
            itemView.setOnClickListener(this);
            mNameTextView = (TextView) itemView.findViewById(R.id.place_name);
            mAddressTextView = (TextView) itemView.findViewById(R.id.place_address);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.place_solved);
        }

        public void bind(Place1 place) {
            mPlace = place;
            mNameTextView.setText(mPlace.getNombre());
            mAddressTextView.setText(mPlace.getDate().toString());
            mSolvedImageView.setVisibility(place.isMusica() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {
            //Intent intent = new Intent(getActivity(), PlaceActivity.class);
            // Intent intent = PlaceActivity.newIntent(getActivity(), mPlace.getId()); CrimeActivity
            //Intent intent = PlacePagerActivity.newIntent(sContext, mPlace.getId()); // PlacePagerActivity
            //sContext.startActivity(intent);

            //Toast.makeText(getActivity(),
            //        mPlace.getNombre() + " clicked!", Toast.LENGTH_SHORT)
            //        .show();
        }
    }
}