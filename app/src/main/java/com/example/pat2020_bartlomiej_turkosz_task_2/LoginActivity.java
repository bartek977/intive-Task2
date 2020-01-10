package com.example.pat2020_bartlomiej_turkosz_task_2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {



    private final String emailPattern = "[\\w\\d\\.\\-=\\+_]+@[\\w\\d\\-=\\+_]+\\.\\w+";
    private final String loginPattern = "[a-z]+";
    //password is checking in isPasswordValid method

    private String email;
    private String login;
    private String password;

    private EditText emailText;
    private EditText loginText;
    private EditText passwordText;
    private TextView incorrectData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_login_landscape);
        } else {
            setContentView(R.layout.activity_login);
        }
        incorrectData = findViewById(R.id.incorrectData);

        if (savedInstanceState != null) {

            String incorrect = savedInstanceState.getString(getString(R.string.incorrect_data_key));
            incorrectData.setText(incorrect);
            incorrectData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        Intent startActivity = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(startActivity);
    }


    public void login(View view) {
        emailText = findViewById(R.id.emailInput);
        email = emailText.getText().toString();

        loginText = findViewById(R.id.loginInput);
        login = loginText.getText().toString();

        passwordText = findViewById(R.id.passwordInput);
        password = passwordText.getText().toString();

        if(email.matches(emailPattern) && login.matches(loginPattern) && isPasswordValid(password)) {

            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.account_key),MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.email_key),email);
            editor.putString(getString(R.string.login_key),login);
            editor.putString(getString(R.string.password_key),password);
            editor.commit();



            Intent startActivity = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(startActivity);
            finish();
        }

        else{
            StringBuilder message = new StringBuilder();

            if(!email.matches(emailPattern)) message.append("Incorrect Email\n");
            if(!login.matches(loginPattern)) message.append("Incorrect login\n");
            if(!isPasswordValid(password)) message.append("Incorrect Password\n");

            incorrectData.setText(message.toString());
            incorrectData.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {


        savedInstanceState.putString(getString(R.string.incorrect_data_key), incorrectData.getText().toString());

        super.onSaveInstanceState(savedInstanceState);
    }

    //Provided password should contain min 6 characters with min one number and
    //one special character

    public boolean isPasswordValid(String pass) {

        boolean result = false;

        if(pass.length()<6) return false;

        for(char c : pass.toCharArray())
        {
            if(Character.isDigit(c))
            {
                result = true;
                break;
            }
        }

        if(!result) return false;
        result = false;

        String specialChars = "/*!@#$%^&*()\"{}_[]|\\?/<>,.";

        for(int i=0; i<pass.length(); i++)
        {
            if (specialChars.contains(pass.substring(i, i+1)))
            {
                result = true;
                break;
            }
        }

        return result;
    }
}
