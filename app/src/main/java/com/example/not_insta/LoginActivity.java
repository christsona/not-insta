package com.example.not_insta;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int SIGN_UP_ACTIVITY_REQUEST_CODE = 23;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null){
            goMainActivity();
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                login(username, password);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(LoginActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                goSignUp();
            }
        });
    }

    private void login(String username, String password){
        // Log.i(TAG, "info" + username + " " + password);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (user != null){
                    goMainActivity();
                    return;
                }
                Log.e(TAG, "login exception");
                e.printStackTrace();
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void signup(final String signupUsername, final String signupPassword, String signupEmail){
        Log.i(TAG, "signing up " + signupUsername + " " + signupPassword + " " + signupEmail);
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(signupUsername);
        user.setPassword(signupPassword);
        user.setEmail(signupEmail);
        // Set custom properties
        user.put("handle", signupUsername);
        //user.put("phone", "650-253-0000");
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    Log.d(TAG, "no errors");
                    login(signupUsername, signupPassword);
                } else {
                    Log.d(TAG, "has errors");
                    e.printStackTrace();
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });
    }

    private void goSignUp(){
        Intent j = new Intent(this, SignupActivity.class);
        startActivityForResult(j, SIGN_UP_ACTIVITY_REQUEST_CODE);
//        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_UP_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            if (data != null) {
                Log.d(TAG, "data is not null");
                String signupUsername = data.getStringExtra("signupUsername");
                String signupPassword = data.getStringExtra("signupPassword");
                String signupEmail = data.getStringExtra("signupEmail");
                signup(signupUsername, signupPassword, signupEmail);
                return;
            }
            Log.d(TAG, "data is null");
        }
    }
}
