package com.ranferi.retrofitexample.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ranferi.retrofitexample.R;
import com.ranferi.retrofitexample.api.APIService;
import com.ranferi.retrofitexample.api.APIUrl;
import com.ranferi.retrofitexample.helper.SharedPrefManager;
import com.ranferi.retrofitexample.model.Result;
import com.ranferi.retrofitexample.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButtonSignUp;
    private EditText mEditTextName, mEditTextEmail, mEditTextPassword;
    private RadioGroup mRadioGender;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mButtonSignUp = (Button) findViewById(R.id.buttonSignUpNow);

        mEditTextName = (EditText) findViewById(R.id.editTextName);
        mEditTextEmail = (EditText) findViewById(R.id.editTextEmail);
        mEditTextPassword = (EditText) findViewById(R.id.editTextPassword);

        mRadioGender = (RadioGroup) findViewById(R.id.radioGender);

        mButtonSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mButtonSignUp) {
            userSignUp();
        }
    }

    private void userSignUp() {

        // defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registrandote...");
        progressDialog.show();

        // getting the user values
        final RadioButton radioGender = (RadioButton) findViewById(mRadioGender.getCheckedRadioButtonId());

        String name = mEditTextName.getText().toString().trim();
        String email = mEditTextEmail.getText().toString().trim();
        String password = mEditTextPassword.getText().toString().trim();
        String gender = radioGender.getText().toString();

        // building retrofit object
        // problema cuando la url no es correcta
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        // defining the user object as we need to pass it with the call
        User user = new User(name, email, password, gender);

        // defining the call
        Call<Result> call = service.createUser(
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getGender()
        );

        // calling the api
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                // hiding progress dialog
                progressDialog.dismiss();

                // displaying the message from the response as toast
                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                // if there is no error
                if (!response.body().getError()) {
                    // starting profile activity
                    finish();
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getUser());
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
