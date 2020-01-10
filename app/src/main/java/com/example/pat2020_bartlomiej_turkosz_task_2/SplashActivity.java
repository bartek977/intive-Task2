package com.example.pat2020_bartlomiej_turkosz_task_2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private String TIME_DELAY_KEY;
    long TIME_DELAY = 6000;
    boolean FromAnotherActivity = false;

    private TextView welcomeText;
    private long time;

    Handler handler = new Handler();
    Runnable myRunnable = new Runnable() {
        @Override
        public void run() {

            SharedPreferences sharedPreferences = getSharedPreferences("Account", MODE_PRIVATE);
            String email = sharedPreferences.getString("email","null");

            boolean logged = !email.equals("null");

            Intent startActivity = null;

            if(logged)
            {
                startActivity = new Intent(SplashActivity.this, MainActivity.class);
            }
            else
            {
                startActivity = new Intent(SplashActivity.this, LoginActivity.class);
            }
            startActivity(startActivity);

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            TIME_DELAY = savedInstanceState.getLong(TIME_DELAY_KEY);
        }


        welcomeText = findViewById(R.id.textWelcome);

        SharedPreferences sharedPreferences = getSharedPreferences("Account", MODE_PRIVATE);
        String email = sharedPreferences.getString("email","null");

        if (email.equals("null")) welcomeText.setText("Welcome My Friend");
        else welcomeText.setText("Welcome ".concat(email));

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(FromAnotherActivity)
        {
            TIME_DELAY = 6000;
        }

        time = System.currentTimeMillis();
        handler.postDelayed(myRunnable,TIME_DELAY);

    }

    @Override
    protected void onPause() {
        super.onPause();

        FromAnotherActivity = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        long timeNow = System.currentTimeMillis();
        long remainingTime = TIME_DELAY - (timeNow - time);

        savedInstanceState.putLong(TIME_DELAY_KEY, remainingTime);

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        handler.removeCallbacks(myRunnable);
        FromAnotherActivity = false;
    }
}