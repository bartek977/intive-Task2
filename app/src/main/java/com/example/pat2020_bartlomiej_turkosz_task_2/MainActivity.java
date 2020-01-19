package com.example.pat2020_bartlomiej_turkosz_task_2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    final static String EMAIL_KEY = "email";
    final static String PASSWORD_KEY = "password";
    final static String LOGIN_KEY = "login";
    final static String ACCOUNT_KEY = "account";
    final static String DEFAULT_VALUE = "null";

    private String email;

    private Button loginButton;
    private Button logoutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.loginButton);
        logoutButton = findViewById(R.id.logoutButton);

        SharedPreferences sharedPreferences = getSharedPreferences(ACCOUNT_KEY, MODE_PRIVATE);
        email = sharedPreferences.getString(EMAIL_KEY, DEFAULT_VALUE);

    }

    @Override
    protected void onResume() {

        super.onResume();

        if (email != null && email.equals(DEFAULT_VALUE)) {
            loginButton.setVisibility(View.VISIBLE);
            logoutButton.setVisibility(View.INVISIBLE);
        } else {
            loginButton.setVisibility(View.INVISIBLE);
            logoutButton.setVisibility(View.VISIBLE);
        }

    }

    public void login(View view) {

        Intent startActivity = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(startActivity);

    }


    public void logout(View view) {

        SharedPreferences sharedPref = getSharedPreferences(ACCOUNT_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(EMAIL_KEY, DEFAULT_VALUE);
        editor.putString(LOGIN_KEY, DEFAULT_VALUE);
        editor.putString(PASSWORD_KEY, DEFAULT_VALUE);
        editor.commit();

        Intent startActivity = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(startActivity);

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

}
