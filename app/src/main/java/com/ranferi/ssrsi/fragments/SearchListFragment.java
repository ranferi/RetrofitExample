package com.ranferi.ssrsi.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ranferi.ssrsi.R;
import com.ranferi.ssrsi.api.APIService;
import com.ranferi.ssrsi.api.APIUrl;
import com.ranferi.ssrsi.helper.PlaceVisitedAdapter;
import com.ranferi.ssrsi.helper.SharedPrefManager;
import com.ranferi.ssrsi.model.Comentario;
import com.ranferi.ssrsi.model.Place;
import com.ranferi.ssrsi.model.Places;
import com.ranferi.ssrsi.model.User;
import com.ranferi.ssrsi.model.UserPlace;
import com.ranferi.ssrsi.model.Users;

import org.parceler.Parcels;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchListFragment extends Fragment {
    private RecyclerView mPlaceRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RealmList<Place> places;

    public SearchListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        places = (RealmList<Place>) Parcels.unwrap(getArguments().getParcelable("places"));
        return inflater.inflate(R.layout.fragment_place_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("ActividadPT", "------------ SearchListFragment, onViewCreated --- ");

        if (getActivity() != null) getActivity().setTitle("Busqueda");

        final int user = SharedPrefManager.getInstance(getActivity()).getUser().getId();

        mPlaceRecyclerView = view.findViewById(R.id.place_recycler_view);
        mPlaceRecyclerView.setHasFixedSize(true);
        mPlaceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new PlaceVisitedAdapter(places, getActivity(), user);
        mPlaceRecyclerView.setAdapter(mAdapter);

    }


}
