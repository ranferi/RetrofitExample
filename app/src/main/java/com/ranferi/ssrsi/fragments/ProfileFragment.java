package com.ranferi.ssrsi.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ranferi.ssrsi.R;
import com.ranferi.ssrsi.api.APIService;
import com.ranferi.ssrsi.api.APIUrl;
import com.ranferi.ssrsi.helper.SharedPrefManager;
import com.ranferi.ssrsi.model.UserResponse;
import com.ranferi.ssrsi.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private Button buttonUpdate;
    private TextInputEditText mEditTextName, mEditTextLastName, mEditTextMaidenName, mEditTextUser, mEditTextEmail, mEditTextPassword, mEditTextRePassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        buttonUpdate = (Button) view.findViewById(R.id.buttonUpdate);
        mEditTextName = (TextInputEditText) view.findViewById(R.id.editTextNameProfile);
        mEditTextLastName = (TextInputEditText) view.findViewById(R.id.editTextLastNameProfile);
        mEditTextMaidenName = (TextInputEditText) view.findViewById(R.id.editTextMaidenNameProfile);
        mEditTextUser = (TextInputEditText) view.findViewById(R.id.editTextUserProfile);
        mEditTextEmail = (TextInputEditText) view.findViewById(R.id.editTextEmailProfile);
        mEditTextPassword = (TextInputEditText) view.findViewById(R.id.editTextPasswordProfile);
        mEditTextRePassword = (TextInputEditText) view.findViewById(R.id.editTextRePasswordProfile);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        User user = SharedPrefManager.getInstance(getActivity()).getUser();

        Log.d("ActividadPT", "Estás en onViewCreated " + " id: " + user.toString() + ", pass: " + SharedPrefManager.getInstance(getActivity()).getpassword());

        buttonUpdate.setOnClickListener(this);

        mEditTextName.setText(user.getName());
        mEditTextLastName.setText(user.getLastName());
        mEditTextMaidenName.setText(user.getMothersMaidenName());
        mEditTextUser.setText(user.getUser());
        mEditTextEmail.setText(user.getEmail());
        mEditTextPassword.setText(SharedPrefManager.getInstance(getActivity()).getpassword());
        mEditTextRePassword.setText(SharedPrefManager.getInstance(getActivity()).getpassword());
    }

    private void updateUser() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Actualizando...");
        progressDialog.show();

        String name = toStringACharSequence(mEditTextName.getText()).trim();
        String last = toStringACharSequence(mEditTextLastName.getText()).trim();
        String maiden = toStringACharSequence(mEditTextMaidenName.getText()).trim();
        String userName = toStringACharSequence(mEditTextUser.getText()).trim();
        String email = toStringACharSequence(mEditTextEmail.getText()).trim();
        final String password = toStringACharSequence(mEditTextPassword.getText()).trim();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        User user = new User(SharedPrefManager.getInstance(getActivity()).getUser().getId(), name, last, maiden, userName, email, password);

        Call<UserResponse> call = service.updateUser(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getMothersMaidenName(),
                user.getUser(),
                user.getEmail(),
                user.getPassword()
        );

        /*Call<UserResponse> call = service.updateUser(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getGender()
        );*/

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                if (!response.body().getError()) {
                    SharedPrefManager.getInstance(getActivity()).userLogin(response.body().getUser());
                    SharedPrefManager.getInstance(getActivity()).Setpassword(password);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == buttonUpdate) {
            updateUser();
        }
    }

    String toStringACharSequence(CharSequence charSequence) {
        final StringBuilder sb = new StringBuilder(charSequence.length());
        sb.append(charSequence);
        return sb.toString();
    }

}