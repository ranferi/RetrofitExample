package com.ranferi.ssrsi.helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ranferi.ssrsi.R;
import com.ranferi.ssrsi.model.Nombre;
import com.ranferi.ssrsi.model.Places;

import java.util.List;

public class PlacessAdapter extends RecyclerView.Adapter<PlacessAdapter.PlacesHolder> {
    private List<Places> mPlacess;
    private Context sContext;

    public PlacessAdapter(List<Places> places, Context context) {
        Log.d("ActividadPT", String.valueOf(places.isEmpty()));
        this.mPlacess = places;
        this.sContext = context;
    }

    @NonNull
    @Override
    public PlacessAdapter.PlacesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        return new PlacessAdapter.PlacesHolder(layoutInflater, viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacessAdapter.PlacesHolder placeHolder, int i) {
        Places place = mPlacess.get(i);
        placeHolder.bind(place);
    }

    @Override
    public int getItemCount() {
        return mPlacess.size();
    }

    public class PlacesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNameTextView;
        private TextView mAddressTextView;
        private Places mPlace;
        private ImageView mSolvedImageView;

        public PlacesHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_place, parent, false));
            itemView.setOnClickListener(this);
            mNameTextView = (TextView) itemView.findViewById(R.id.place_name);
            mAddressTextView = (TextView) itemView.findViewById(R.id.place_address);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.place_solved);
        }

        public void bind(Places place) {
            mPlace = place;
            Nombre nombre1 = mPlace.getNombres().get(0);
            mNameTextView.setText(nombre1.getNombreSitio());
            mAddressTextView.setText(mPlace.getDireccion());
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
