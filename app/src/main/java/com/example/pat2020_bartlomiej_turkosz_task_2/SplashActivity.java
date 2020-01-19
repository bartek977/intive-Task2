package com.example.pat2020_bartlomiej_turkosz_task_2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    final static long SPLASH_SCREEN_START_DURATION = 6000;

    long timeDelay = SPLASH_SCREEN_START_DURATION;

    final static String emailKey = "email";
    final static String timeDelayKey = "timeDelayKey";
    final static String accountKey = "account";

    private long time;

    final Handler handler = new Handler();
    final Runnable myRunnable = new Runnable() {
        @Override
        public void run() {

            SharedPreferences sharedPreferences = getSharedPreferences(accountKey, MODE_PRIVATE);
            String email = sharedPreferences.getString(emailKey, getString(R.string.default_value));

            boolean logged = email!=null && !email.equals(getString(R.string.default_value));

            Intent startActivity;

            if (logged) {
                startActivity = new Intent(SplashActivity.this, MainActivity.class);
            } else {
                startActivity = new Intent(SplashActivity.this, LoginActivity.class);
            }
            startActivity(startActivity);
            finish();

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            timeDelay = savedInstanceState.getLong(timeDelayKey);
        }


        TextView welcomeText = findViewById(R.id.textWelcome);

        SharedPreferences sharedPreferences = getSharedPreferences(accountKey, MODE_PRIVATE);
        String email = sharedPreferences.getString(emailKey, getString(R.string.default_value));

        if (email!=null && email.equals(getString(R.string.default_value)))
            welcomeText.setText(getString(R.string.welcome_text));
        else welcomeText.setText(getString(R.string.welcome_user, email));


    }

    @Override
    protected void onResume() {
        super.onResume();

        time = System.currentTimeMillis();
        handler.postDelayed(myRunnable, timeDelay);

    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        long timeNow = System.currentTimeMillis();
        long remainingTime = timeDelay - (timeNow - time);

        savedInstanceState.putLong(timeDelayKey, remainingTime);

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();

        handler.removeCallbacks(myRunnable);
    }
}