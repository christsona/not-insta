package com.example.not_insta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText etSignupUsername;
    private EditText etSignupPassword;
    private EditText etSignupEmail;
    private Button btnSignupSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etSignupUsername = findViewById(R.id.etSignupUsername);
        etSignupPassword = findViewById(R.id.etSignupPassword);
        etSignupEmail = findViewById(R.id.etSignupEmail);
        btnSignupSubmit = findViewById(R.id.btnSignupSubmit);

        btnSignupSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String signupUsername = etSignupUsername.getText().toString();
                String signupPassword = etSignupPassword.getText().toString();
                String signupEmail = etSignupEmail.getText().toString();
                Intent i = new Intent();
                i.putExtra("signupUsername", signupUsername);
                i.putExtra("signupPassword", signupPassword);
                i.putExtra("signupEmail", signupEmail);
                setResult(RESULT_OK, i);
                finish();
            }
        });

    }
}
