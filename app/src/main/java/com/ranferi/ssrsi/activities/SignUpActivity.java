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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButtonSignUp;
    private Button mButtonLoginLink;
    private EditText mEditTextUser, mEditTextName, mEditTextLastName, mEditTextMaidenName, mEditTextEmail, mEditTextPassword, mReEnterPasswordText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mEditTextUser = (EditText) findViewById(R.id.editTextUser);
        mEditTextName = (EditText) findViewById(R.id.editTextName);
        mEditTextLastName = (EditText) findViewById(R.id.editTextLastName);
        mEditTextMaidenName = (EditText) findViewById(R.id.editTextMaidenName);
        mEditTextEmail = (EditText) findViewById(R.id.editTextEmail);
        mEditTextPassword = (EditText) findViewById(R.id.editTextPassword);
        mReEnterPasswordText = (EditText) findViewById(R.id.editTextRePassword);

        mButtonSignUp = (Button) findViewById(R.id.buttonSignUpNow);
        mButtonSignUp.setOnClickListener(this);

        mButtonLoginLink = (Button) findViewById(R.id.login_button_link);
        mButtonLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
                finish();
                SignUpActivity.this.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == mButtonSignUp) {
            userSignUp();
        }
    }

    private void userSignUp() {

        if (!validate()) {
            onSignupFailed();
            return;
        }

        mButtonSignUp.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registrandote...");
        progressDialog.show();

        String name = mEditTextName.getText().toString().trim();
        String lastName = mEditTextLastName.getText().toString().trim();
        String maidenName = mEditTextMaidenName.getText().toString().trim();
        String userName = mEditTextUser.getText().toString().trim();
        String email = mEditTextEmail.getText().toString().trim();
        final String password = mEditTextPassword.getText().toString().trim();

        /*HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();*/


        // armar un objeto retrofit
        // problema cuando la url no es correcta
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        // defining the user object as we need to pass it with the call
        // -----> User user = new User(name, email, password, gender);
        User user = new User(name, lastName, maidenName, userName, email, password);

        // defining the call
        Call<UserResponse> call = service.createUser(
                user.getName(),
                user.getLastName(),
                user.getMothersMaidenName(),
                user.getUser(),
                user.getEmail(),
                user.getPassword()
        );

        /*Call<UserResponse> call = service.createUser(
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getGender()
        );*/

        // calling the api
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                // se esconde el dialogo de progreso
                progressDialog.dismiss();

                // se envia un mensaje de respuesta en un toast
                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                Log.d("TT", "en call.enqueue, onResponse");

                // si no hay error
                if (!response.body().getError()) {
                    // inicia la actividad 'Home'
                    finish();
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getUser());
                    SharedPrefManager.getInstance(getApplicationContext()).Setpassword(password);
                    mButtonSignUp.setEnabled(true);
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("TT", "en call.enqueue, onFailure");
            }
        });

    }

    public boolean validate() {
        boolean valid = true;

        String name = mEditTextName.getText().toString();
        String lastName = mEditTextLastName.getText().toString();
        String maidenName = mEditTextMaidenName.getText().toString();
        String email = mEditTextEmail.getText().toString();
        String password = mEditTextPassword.getText().toString();
        String reEnterPassword = mReEnterPasswordText.getText().toString();

        if (name.length() < 3) {
            mEditTextName.setError("Al menos 3 caracteres");
            valid = false;
        } else {
            mEditTextName.setError(null);
        }

        if (lastName.length() < 3) {
            mEditTextLastName.setError("Al menos 3 caracteres");
            valid = false;
        } else {
            mEditTextLastName.setError(null);
        }

        if (maidenName.length() < 3) {
            mEditTextMaidenName.setError("Al menos 3 caracteres");
            valid = false;
        } else {
            mEditTextMaidenName.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEditTextEmail.setError("Ingresa un email válido");
            valid = false;
        } else {
            mEditTextEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mEditTextPassword.setError("Entre 4 y 10 caracteres alfanuméricos");
            valid = false;
        } else {
            mEditTextPassword.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            mReEnterPasswordText.setError("El password no coincide");
            valid = false;
        } else {
            mReEnterPasswordText.setError(null);
        }

        return valid;
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Falló el inicio de sesión", Toast.LENGTH_LONG).show();

        mButtonSignUp.setEnabled(true);
    }
}
