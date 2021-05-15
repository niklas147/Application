package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Konto extends AppCompatActivity implements View.OnClickListener {

    Button bLogout;
    EditText etName, etLastname, etUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konto);

        etName = (EditText) findViewById(R.id.etVorname);
        etLastname = (EditText) findViewById(R.id.etNachname);
        etUsername = (EditText) findViewById(R.id.etBenutzername);

        bLogout = (Button) findViewById(R.id.bLogout);

        bLogout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bLogout:


                break;

        }
    }
}