package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    Button bRegister;
    EditText etName, etLastname, etUsername, etPassword, etRePassword, etGrade;
    TextView tvLoginLink;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName          = findViewById(R.id.etVorname);
        etLastname      = findViewById(R.id.etNachname);
        etUsername      = findViewById(R.id.etBenutzername);
        etPassword      = findViewById(R.id.etPasswort);
        etRePassword    = findViewById(R.id.etRePasswort);
        etGrade         = findViewById(R.id.etGrade);

        progressBar     = findViewById(R.id.progressBar);
        tvLoginLink     = findViewById(R.id.tvRegisterLink);
        bRegister       = findViewById(R.id.bRegister);

        fAuth           = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), Konto.class));
            finish();
        }

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String name = etName.getText().toString().trim();
                String lastname = etLastname.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String grade = etGrade.getText().toString().trim();

                if (TextUtils.isEmpty(username)){
                    etUsername.setError("Benutzername wird benötigt");
                    return;
                }

                if (TextUtils.isEmpty(name)){
                    etName.setError("Name wird benötigt");
                    return;
                }

                if (TextUtils.isEmpty(lastname)){
                    etLastname.setError("Nachname wird benötigt");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    etPassword.setError("Passwort wird benötigt");
                    return;
                }

                if (TextUtils.isEmpty(grade)){
                    etGrade.setError("Klasse wird benötigt");
                    return;
                }

                if (password.length() < 6){
                    etPassword.setError("Passwort muss mindestens 6 Zeichen lang sein");
                }

                progressBar.setVisibility(View.VISIBLE);


                //Register the User

                fAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "Benutzer wurde erstellt", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Konto.class));

                        }else{
                            Toast.makeText(Register.this, "Fehler beim Erstellen des Benutzers" , Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

    }
    public void gotoLogin(View view) {
        startActivity(new Intent(getApplicationContext(), Login.class));
    }

    }
