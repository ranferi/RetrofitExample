package com.ranferi.ssrsi.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ranferi.ssrsi.R;
import com.ranferi.ssrsi.api.APIService;
import com.ranferi.ssrsi.api.APIUrl;
import com.ranferi.ssrsi.helper.UserAdapter;
import com.ranferi.ssrsi.model.User;
import com.ranferi.ssrsi.model.Users;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private static final String LOG_TAG = HomeFragment.class.getSimpleName();

    private RecyclerView recyclerViewUsers;
    private ProgressBar mProgressBar;
    private RecyclerView.Adapter adapter;
    private List<User> mUsers = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerViewUsers = (RecyclerView) view.findViewById(R.id.recyclerViewUsers);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupRecyclerView(recyclerViewUsers);
        fetchUsers();
    }

    private void fetchUsers() {
        mProgressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        Call<Users> call = service.getUsers();

        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(@NonNull Call<Users> call, @NonNull Response<Users> response) {
                if (response.isSuccessful()) {
                    List<User> users = response.body().getUsers();
                    mUsers.clear();
                    mUsers.addAll(users);

                    adapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                    Log.e(LOG_TAG, "onResponse(): Error code = " + statusCode);
                }
                mProgressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NonNull Call<Users> call, @NonNull Throwable t) {
                mProgressBar.setVisibility(View.GONE);

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        adapter = new UserAdapter(mUsers, getActivity());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }
}
