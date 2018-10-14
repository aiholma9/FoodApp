package com.holmadesigns.foodapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.holmadesigns.foodapp.Model.Users;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUpActivity extends AppCompatActivity {

    MaterialEditText first_name, surname, email, phone_number, address, password, confirm_password;
    Button sign_up, cancel;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        first_name = (MaterialEditText)findViewById(R.id.first_name);
        surname = (MaterialEditText)findViewById(R.id.surname);
        email = (MaterialEditText)findViewById(R.id.email);
        phone_number = (MaterialEditText)findViewById(R.id.phone_number);
        address = (MaterialEditText)findViewById(R.id.address);
        password = (MaterialEditText)findViewById(R.id.password);
        confirm_password = (MaterialEditText)findViewById(R.id.confirm_password);

        sign_up = (Button)findViewById(R.id.btn_sign_up);
        cancel = (Button)findViewById(R.id.btn_cancel);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Users users = new Users(first_name.getText().toString(), surname.getText().toString(),
                        email.getText().toString(), phone_number.getText().toString(),
                        password.getText().toString(), confirm_password.getText().toString());

                String userEmail = email.getText().toString();
                final String userPassword = password.getText().toString();

                signUp(userEmail, userPassword);
                signUpToDatabase(users);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first_name.setText("");
                surname.setText("");
                email.setText("");
                phone_number.setText("");
                address.setText("");
                password.setText("");
                confirm_password.setText("");
            }
        });
    }

    private void signUpToDatabase(final Users users){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(users.getUserPhone()).exists()){
                    Toast.makeText(SignUpActivity.this, "User already exists !", Toast.LENGTH_SHORT).show();
                }

                else {
                    databaseReference.child(users.getUserPhone()).setValue(users);
                    Toast.makeText(SignUpActivity.this, "User registration success !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void signUp(String userEmail, String userPassword) {
        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            Toast.makeText(SignUpActivity.this, firebaseUser.getEmail()+" successfully Sign Up", Toast.LENGTH_SHORT).show();
                        }

                        else {
                            Toast.makeText(SignUpActivity.this, "Incorrect Email or Password.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
