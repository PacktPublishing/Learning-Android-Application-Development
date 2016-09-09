package com.packt.rrafols.calculatortestexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private static final String USERNAME = "john@doe.net";
    private static final String PASSWORD = "admin1234";

    private EditText usernameEditText;
    private EditText passwordEditText;
    private TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_login);

        usernameEditText = (EditText) findViewById(R.id.login_username);
        passwordEditText = (EditText) findViewById(R.id.login_password);
        errorTextView = (TextView) findViewById(R.id.login_error_text);

        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if(!isUsernameValid(username)) {
                    showError(R.string.invalid_username);
                } else if(!isPasswordValid(password)) {
                    showError(R.string.invalid_password);
                } else if(!areCredentialsCorrect(username, password)) {
                    showError(R.string.incorrect_credentials);
                } else {
                    startActivity(new Intent(LoginActivity.this, CalculatorActivity.class));
                    finish();
                }
            }
        });
    }

    private void showError(int stringResource) {
        errorTextView.setText(getString(stringResource));
        errorTextView.setVisibility(View.VISIBLE);
    }

    private static int countDigits(String str) {
        int digits = 0;
        for(int i = 0; str != null && i < str.length(); i++) {
            if(Character.isDigit(str.charAt(i))) digits++;
        }
        return digits;
    }

    private static int countLetters(String str) {
        int letters = 0;
        for(int i = 0; str != null && i < str.length(); i++) {
            if(Character.isLetter(str.charAt(i))) letters++;
        }
        return letters;
    }

    static boolean isUsernameValid(String username) {
        if (username == null) return false;
        if (username.length() < 6) return false;
        if (username.indexOf('@') == -1) return false;
        if (username.indexOf('.', username.indexOf('@')) == -1) return false;

        return true;
    }

    static boolean isPasswordValid(String password) {
        if (password == null) return false;
        if (password.length() < 6) return false;
        if (countDigits(password) == 0 || countLetters(password) == 0) return false;

        return true;
    }

    static boolean areCredentialsCorrect(String username, String password) {
        return USERNAME.equals(username) && PASSWORD.equals(password);
    }
}
