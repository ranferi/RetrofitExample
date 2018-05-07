package com.ranferi.retrofitexample.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ranferi.retrofitexample.R;
import com.ranferi.retrofitexample.helper.SharedPrefManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButtonSignIn, mButtonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // si un usuario ya inicio sesión, abrir la actividad de perfil
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, HomeActivity.class));
        }

        mButtonSignIn = (Button) findViewById(R.id.buttonSignIn);
        mButtonSignUp = (Button) findViewById(R.id.buttonSignUp);

        mButtonSignIn.setOnClickListener(this);
        mButtonSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mButtonSignIn) {
            startActivity(new Intent(this, SignInActivity.class));
        } else if (v == mButtonSignUp) {
            startActivity(new Intent(this, SignUpActivity.class));
        }
    }
}
