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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    Button bLogin;
    EditText etUsername, etPassword;
    TextView tvRegisterLink;
    ProgressBar progressBar2;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername      = findViewById(R.id.etBenutzername);
        etPassword      = findViewById(R.id.etPasswort);
        bLogin          = findViewById(R.id.bLogin);
        tvRegisterLink  = findViewById(R.id.tvRegisterLink);
        progressBar2    = findViewById(R.id.progressBar2);

        fAuth           = FirebaseAuth.getInstance();

        FirebaseUser user = fAuth.getInstance().getCurrentUser();

        if (user !=null){
            startActivity(new Intent(getApplicationContext(), MainActivity2.class));
        }

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (TextUtils.isEmpty(username)){
                    etUsername.setError("Benutzername wird benötigt");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    etPassword.setError("Passwort wird benötigt");
                    return;
                }

                if (password.length() < 6){
                    etPassword.setError("Passwort muss mindestens 6 Zeichen lang sein");
                }
                progressBar2.setVisibility(View.VISIBLE);


                //Authenticate user

                fAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Erfolgreich angemeldet", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                        }else{
                            Toast.makeText(Login.this, "Fehler beim Anmelden", Toast.LENGTH_SHORT).show();
                            progressBar2.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });
    }


    public void gotoRegister(View view) {
        startActivity(new Intent(getApplicationContext(), Register.class));
    }
}
