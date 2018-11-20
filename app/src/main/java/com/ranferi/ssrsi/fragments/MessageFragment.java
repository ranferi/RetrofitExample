package com.ranferi.ssrsi.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ranferi.ssrsi.R;
import com.ranferi.ssrsi.api.APIService;
import com.ranferi.ssrsi.api.APIUrl;
import com.ranferi.ssrsi.helper.MessageAdapter;
import com.ranferi.ssrsi.helper.SharedPrefManager;
import com.ranferi.ssrsi.model.Messages;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageFragment extends Fragment {

    private RecyclerView recyclerViewMessages;
    private RecyclerView.Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Sitios");

        recyclerViewMessages = (RecyclerView) view.findViewById(R.id.recyclerViewMessages);
        recyclerViewMessages.setHasFixedSize(true);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(getActivity()));

        /*HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();*/

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        final int user = SharedPrefManager.getInstance(getActivity()).getUser().getId();

        Call<Messages> call = service.getMessages(user);

        call.enqueue(new Callback<Messages>() {

            @Override
            public void onResponse(Call<Messages> call, Response<Messages> response) {
                Log.d("TT", "Estás en onResponse ");
                if (!response.isSuccessful()) {
                    Log.d("TT", "Algo fallo");
                }
                adapter = new MessageAdapter(response.body().getMessages(), getActivity());
                recyclerViewMessages.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Messages> call, Throwable t) {
                Log.d("TT", "Estás en onFailure " + t.getMessage());
                Toast.makeText(getActivity(), user + " er " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
