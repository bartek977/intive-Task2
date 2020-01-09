package com.example.pat2020_bartlomiej_turkosz_task_2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private String email;

    private Button loginButton;
    private Button logoutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.loginButton);
        logoutButton = findViewById(R.id.logoutButton);

        SharedPreferences sharedPreferences = getSharedPreferences("Account", MODE_PRIVATE);
        email = sharedPreferences.getString("email", "null");

    }

    @Override
    protected void onResume() {

        super.onResume();

        if (email!=null && email.equals("null")) {
            loginButton.setVisibility(View.VISIBLE);
            logoutButton.setVisibility(View.INVISIBLE);
        } else {
            loginButton.setVisibility(View.INVISIBLE);
            logoutButton.setVisibility(View.VISIBLE);
        }

    }

    public void Login(View view) {

        Intent startActivity = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(startActivity);

    }

    public void Logout(View view) {

        SharedPreferences sharedPref = getSharedPreferences("Account",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("email","null");
        editor.putString("login","null");
        editor.putString("password","null");
        editor.commit();

        Intent startActivity = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(startActivity);

    }
}
