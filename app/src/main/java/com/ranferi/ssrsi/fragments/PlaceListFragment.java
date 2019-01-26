package com.ranferi.ssrsi.fragments;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ranferi.ssrsi.R;
import com.ranferi.ssrsi.activities.PlacePagerActivity;
import com.ranferi.ssrsi.api.APIService;
import com.ranferi.ssrsi.api.APIUrl;
import com.ranferi.ssrsi.helper.MessageAdapter;
import com.ranferi.ssrsi.helper.PlaceAdapter;
import com.ranferi.ssrsi.helper.PlaceLab;
import com.ranferi.ssrsi.helper.PlacessAdapter;
import com.ranferi.ssrsi.helper.SharedPrefManager;
import com.ranferi.ssrsi.model.Messages;
import com.ranferi.ssrsi.model.Place;
import com.ranferi.ssrsi.model.Places;
import com.ranferi.ssrsi.model.PlacesResponse;
import com.ranferi.ssrsi.model.Placess;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceListFragment extends Fragment {
    private RecyclerView mPlaceRecyclerView;
    private RecyclerView.Adapter mAdapter;

    public PlaceListFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_list, container, false);
        // mPlaceRecyclerView = (RecyclerView) view
        //        .findViewById(R.id.place_recycler_view);
        //mPlaceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //updateUI();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getActivity() != null){
            getActivity().setTitle("Sitios");
        }

        mPlaceRecyclerView = (RecyclerView) view.findViewById(R.id.place_recycler_view);
        mPlaceRecyclerView.setHasFixedSize(true);
        mPlaceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        final int user = SharedPrefManager.getInstance(getActivity()).getUser().getId();

        Call<Placess> call = service.getPlaces();

        call.enqueue(new Callback<Placess>() {

            @Override
            public void onResponse(Call<Placess> call, Response<Placess> response) {
                if (response.isSuccessful()) {
                    Log.d("ActividadPT", "PlaceListFragment: Estás en onResponse ");
                    List<Places> placess = response.body().getPlacess();
                    if (placess != null) {
                        Log.d("ActividadPT", "11ass  ");
                        mAdapter = new PlacessAdapter(placess, getActivity());
                        mPlaceRecyclerView.setAdapter(mAdapter);
                    } else {
                        Log.d("ActividadPT", "PlaceListFragment: List<> empty ");
                    }
                } else {
                    ResponseBody errorBody = response.errorBody();
                    if (errorBody != null)
                        Log.d("ActividadTT", "en PlaceListFragment, onResponse not successful, error: " + errorBody.toString());
                }
            }

            @Override
            public void onFailure(Call<Placess> call, Throwable t) {
                Log.d("ActividadPT", "Estás en onFailure " + t.getMessage());
                Toast.makeText(getActivity(), user + " er " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        // updateUI();
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
