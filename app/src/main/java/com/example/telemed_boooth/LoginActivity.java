package com.example.telemed_boooth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // link UI elements
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        // handle login button click
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            } else if (email.equals("admin@gmail.com") && pass.equals("123456")) {
                // successful login
                Toast.makeText(LoginActivity.this, "Login successful 🎉", Toast.LENGTH_SHORT).show();
                // start HomeActivity instead of MainActivity
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish(); // close login so user can’t go back to it
            } else {
                Toast.makeText(LoginActivity.this, "Invalid credentials ❌", Toast.LENGTH_SHORT).show();
            }
        });

        // handle "Register" text click
        tvRegister.setOnClickListener(v -> {
            // open RegisterPatientActivity
            Intent intent = new Intent(LoginActivity.this, RegisterPatientActivity.class);
            startActivity(intent);
        });
    }
}
