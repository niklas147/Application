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
import com.google.firebase.firestore.SetOptions;

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
                HashMap hashMapMnt = new HashMap();
                HashMap hashMapDummy = new HashMap();
                String[] mntarray = new String[] {"stuhlberg","barockschloss","taubenkopf","wildgehege","schloss_schwetzingen","heiligenberg","weisser_stein"};

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
                hashMap.put("stuhlberg_cut", "nicht besucht");
                hashMap.put("barockschloss", "nicht besucht");
                hashMap.put("taubenkopf", "nicht besucht");
                hashMap.put("wildgehege_karlstern", "nicht besucht");
                hashMap.put("schloss_schwetzingen", "nicht besucht");
                hashMap.put("heiligenberg", "nicht besucht");
                hashMap.put("weisser_stein", "nicht besucht");
                hashMap.put("besucht", "0");

                hashMapMnt.put(username.toLowerCase(), username.toLowerCase());




                //Register the User

                fAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            // add User Data to Database
                            db.collection(grade).document(username.toLowerCase()).set(hashMap);


                            db.collection("Benutzer").document(username.toLowerCase()).set(hashMap);


                            for( int i = 0; i < mntarray.length; i++){
                                db.collection(grade+"BergeNot").document(mntarray[i]).set(hashMapMnt,SetOptions.merge())
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Register.this, "Datenspeicherung fehlgeschlagen", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }





                            startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                        }else{
                            progressBar.setVisibility(View.INVISIBLE);
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
