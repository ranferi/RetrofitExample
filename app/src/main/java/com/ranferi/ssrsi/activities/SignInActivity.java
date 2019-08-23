package com.ranferi.ssrsi.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ranferi.ssrsi.R;
import com.ranferi.ssrsi.api.APIService;
import com.ranferi.ssrsi.api.APIUrl;
import com.ranferi.ssrsi.helper.SharedPrefManager;
import com.ranferi.ssrsi.model.UserResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditTextEmail, mEditTextPassword;
    private Button mButtonSignIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, HomeActivity.class));
        }

        mEditTextEmail = findViewById(R.id.email_edit_text);
        mEditTextPassword = findViewById(R.id.password_edit_text);

        mButtonSignIn = findViewById(R.id.login_button);
        mButtonSignIn.setOnClickListener(this);

        Button buttonRegisterLink = findViewById(R.id.register_button_link);
        buttonRegisterLink.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
            finish();
            SignInActivity.this.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });
    }

    @Override
    public void onClick(View v) {
        if (v == mButtonSignIn) {
            userSignIn();
        }
    }

    private void userSignIn() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Iniciando sesión...");
        progressDialog.show();

        String email = mEditTextEmail.getText().toString().trim();
        final String password = mEditTextPassword.getText().toString().trim();

        // Log.d("ActividadPT", "Estás en userSignIn, antes de service.userLogin" );


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<UserResponse> call = service.userLogin(email, password);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {

                progressDialog.dismiss();
                //TODO: me muestra java.lang.NullPointerException: Attempt to invoke virtual method
                // 'java.lang.Boolean com.ranferi.ssrsi.model.UserResponse.getError()' on a null object reference
                // cuando no hay usuario
                if (response.body() != null) {
                    if (!response.body().getError()) {
                        UserResponse userResponse = response.body();
                        // Log.d("ActividadPT", "en call.enqueue, onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body().toString()));
                        finish();
                        // Log.d("ActividadPT", "Estás signinactivity onResponse, response: " + password );
                        Toast.makeText(getApplicationContext(), userResponse.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d("ActividadPT", "en call.enqueue, onResponse: " + userResponse.getUser().getEmail() + " " + userResponse.getUser().getUser());

                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getUser());
                        SharedPrefManager.getInstance(getApplicationContext()).setPassword(password);
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Email o password inválidos", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Problema con la conexión", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
