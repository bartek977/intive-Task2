package com.example.pat2020_bartlomiej_turkosz_task_2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private TextView incorrectData;

    final static String EMAIL_KEY = "email";
    final static String PASSWORD_KEY = "password";
    final static String LOGIN_KEY = "login";
    final static String ACCOUNT_KEY = "account";
    final static String INCORRECT_DATA_KEY = "incorrectDataKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        incorrectData = findViewById(R.id.incorrectData);

        if (savedInstanceState != null) {

            String incorrect = savedInstanceState.getString(INCORRECT_DATA_KEY);
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
        EditText emailText = findViewById(R.id.emailInput);
        String email = emailText.getText().toString();

        EditText loginText = findViewById(R.id.loginInput);
        String login = loginText.getText().toString();

        EditText passwordText = findViewById(R.id.passwordInput);
        String password = passwordText.getText().toString();

        final String emailPattern = "[\\w\\d\\.\\-=\\+_]+@[\\w\\d\\-=\\+_]+\\.\\w+";
        final String loginPattern = "[a-z]+";

        if (email.matches(emailPattern) && login.matches(loginPattern) && isPasswordValid(password)) {

            SharedPreferences sharedPref = getSharedPreferences(ACCOUNT_KEY, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(EMAIL_KEY, email);
            editor.putString(LOGIN_KEY, login);
            editor.putString(PASSWORD_KEY, password);
            editor.commit();


            Intent startActivity = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(startActivity);
            finish();
        } else {
            StringBuilder message = new StringBuilder();

            if (!email.matches(emailPattern))
                message.append(getString(R.string.incorrect_email_text)).append("\n");
            if (!login.matches(loginPattern))
                message.append(getString(R.string.incorrect_login_text)).append("\n");
            if (!isPasswordValid(password)) message.append(getString(R.string.incorrect_password_text));

            incorrectData.setText(message.toString());
            incorrectData.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {


        savedInstanceState.putString(INCORRECT_DATA_KEY, incorrectData.getText().toString());

        super.onSaveInstanceState(savedInstanceState);
    }

    //Provided password should contain min 6 characters with min one number and
    //one special character

    public boolean isPasswordValid(String pass) {

        boolean result = false;

        if (pass.length() < 6) return false;

        for (char c : pass.toCharArray()) {
            if (Character.isDigit(c)) {
                result = true;
                break;
            }
        }

        if (!result) return false;
        result = false;

        final String specialChars = "/*!@#$%^&*()\"{}_[]|\\?/<>,.";

        for (int i = 0; i < pass.length(); i++) {
            if (specialChars.contains(pass.substring(i, i + 1))) {
                result = true;
                break;
            }
        }

        return result;
    }
}
