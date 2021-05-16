package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Konto extends AppCompatActivity  {

    Button bLogout;
    EditText etName, etLastname, etUsername, etGrade;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konto);

        etName = (EditText) findViewById(R.id.etVorname);
        etLastname = (EditText) findViewById(R.id.etNachname);
        etUsername = (EditText) findViewById(R.id.etBenutzername);
        etGrade = (EditText) findViewById(R.id.etGrade);
        bLogout = (Button) findViewById(R.id.bLogout);

    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }


    }
