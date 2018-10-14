package com.holmadesigns.foodapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginActivityTest extends AppCompatActivity {

    MaterialEditText username, password;
    Button btnLogIn,btnSignUp, btnReset;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_test);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            Intent intent = new Intent(LoginActivityTest.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        username = (MaterialEditText) findViewById(R.id.email);
        password = (MaterialEditText) findViewById(R.id.password);

        btnLogIn = (Button) findViewById(R.id.btn_login);
        btnSignUp = (Button) findViewById(R.id.btn_sign_up);
        btnReset = (Button) findViewById(R.id.btn_reset_password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivityTest.this, SignupActivityTest.class);
                startActivity(intent);
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = username.getText().toString().trim();
                String pwd = password.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pwd)){
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                logIn(email, pwd);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivityTest.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void logIn(String email, final String pwd) {
        firebaseAuth.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(LoginActivityTest.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()){
                            if (pwd.length() < 6)
                                password.setError(getString(R.string.minimum_password));
                            else {
                                Toast.makeText(LoginActivityTest.this, getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
                            }
                        }

                        else {
                            Intent intent = new Intent(LoginActivityTest.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}
