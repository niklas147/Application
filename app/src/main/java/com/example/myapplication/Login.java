package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityLoginBinding;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    Button bLogin;
    EditText etUsername, etPassword;
    TextView tvRegisterLink;
    ProgressBar progressBar;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername      = findViewById(R.id.etBenutzername);
        etPassword      = findViewById(R.id.etPasswort);
        bLogin          = findViewById(R.id.bLogin);
        tvRegisterLink  = findViewById(R.id.tvRegisterLink);
        progressBar     = findViewById(R.id.progressBar);

        fAuth           = FirebaseAuth.getInstance();


        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
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

                progressBar.setVisibility(View.VISIBLE);


                //Authenticate user

                fAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Erfolgreich angemeldet", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Konto.class));
                        }else{

                        }
                    }
                });

                startActivity(new Intent(getApplicationContext(), Register.class));



            }
        });





    }


    }
