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
import com.ranferi.ssrsi.helper.PlacesAdapter;
import com.ranferi.ssrsi.helper.SharedPrefManager;
import com.ranferi.ssrsi.model.Place;
import com.ranferi.ssrsi.model.User;
import com.ranferi.ssrsi.model.UserPlace;
import com.ranferi.ssrsi.model.Users;


import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VisitedFragment extends Fragment {
    private Realm realm;
    private RecyclerView mPlaceRecyclerView;
    private RecyclerView.Adapter mAdapter;

    public VisitedFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_place_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() != null) getActivity().setTitle("Visitados");

        final int user = SharedPrefManager.getInstance(getActivity()).getUser().getId();

        realm = Realm.getDefaultInstance();
        /*realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm bgRealm) {
                bgRealm.deleteAll();
            }
        });*/


        mPlaceRecyclerView = view.findViewById(R.id.place_recycler_view);
        mPlaceRecyclerView.setHasFixedSize(true);
        mPlaceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Retrofit retrofit = new Retrofit.Builder()
                //.client(client)
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        Call<Users> call = service.getVisited(user);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(@NonNull Call<Users> call, @NonNull Response<Users> response) {
                if (response.isSuccessful()) {

                    RealmList<User> users = response.body().getUsers();
                    RealmList<UserPlace> visitados = users.first().getVisito();

                    /*Iterator<UserPlace> visited = visitados.iterator();
                    while (visited.hasNext()) {
                        UserPlace userPlace = visited.next();
                        if (userPlace.getSitioSrc().equals(userPlaces.getSitioSrc())) visited.remove();
                    }*/
                    if (!visitados.isEmpty()) {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(@NonNull Realm bgRealm) {
                                bgRealm.copyToRealmOrUpdate(users);
                            }
                        });
                    } else {
                        Log.d("ActividadPT", "VisitedFragmentFragment: List<> empty ");
                    }
                    RealmQuery<Place> query = realm.where(Place.class).equalTo("visitaron.visitantes.id", user);
                    List<Place> places = query.findAll();

                    mAdapter = new PlacesAdapter(places, getActivity());
                    mPlaceRecyclerView.setAdapter(mAdapter);
                } else {
                    int statusCode = response.code();
                    Log.d("ActividadTT", "VisitedFragmentFragment onResponse(): Error code = " + statusCode);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Users> call, @NonNull Throwable t) {
                Log.d("ActividadPT", "Estás en onFailure " + t.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realm.close();
    }

}
