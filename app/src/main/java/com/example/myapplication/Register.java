package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Register extends AppCompatActivity {


    FirebaseFirestore db = FirebaseFirestore.getInstance();



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


        // Prüfen ob jm eingeloggt ist
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), Konto.class));
            finish();
        }
        // Click Listener Anmelden btn
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username     = etUsername.getText().toString().trim();
                String name         = etName.getText().toString().trim();
                String lastname     = etLastname.getText().toString().trim();
                String password     = etPassword.getText().toString().trim();
                String grade        = etGrade.getText().toString().trim();
                String rePassword   = etRePassword.getText().toString().trim();

                HashMap hashMap = new HashMap();


                //Fehler abfangen
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
                    return;
                }

                if (!password.equals(rePassword)){
                    etRePassword.setError("Passwörter stimmen nicht überein");
                    etRePassword.setText("");
                    etPassword.setText("");
                    return;

                }

                //Alle Daten richtig eingegeben
                progressBar.setVisibility(View.VISIBLE);

                //add key and Values to hash Map
                hashMap.put("Benutzername", username.toLowerCase());
                hashMap.put("Name", name);
                hashMap.put("Nachname", lastname);
                hashMap.put("Klasse", grade);


                //Register the User

                fAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            // add User Data to Database
                            db.collection(username.toLowerCase()).document("Persönliche Daten").set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(Register.this, "Erfolgreich Registriert", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Register.this, "Erfolgreich Registriert, Datenspeicherung fehlgeschlagen", Toast.LENGTH_SHORT).show();
                                }
                            });


                            startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                        }else{
                            progressBar.setVisibility(View.VISIBLE);
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
