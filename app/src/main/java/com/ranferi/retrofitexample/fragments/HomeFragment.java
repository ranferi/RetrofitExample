package com.ranferi.retrofitexample.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ranferi.retrofitexample.R;
import com.ranferi.retrofitexample.api.APIService;
import com.ranferi.retrofitexample.api.APIUrl;
import com.ranferi.retrofitexample.helper.UserAdapter;
import com.ranferi.retrofitexample.model.Users;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewUsers;
    private RecyclerView.Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Inicio");

        recyclerViewUsers = (RecyclerView) view.findViewById(R.id.recyclerViewUsers);
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getActivity()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<Users> call = service.getUsers();

        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                adapter = new UserAdapter(response.body().getUsers(), getActivity());
                recyclerViewUsers.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });
    }

}
