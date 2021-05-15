package com.example.myapplication;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityRegisterBinding;

public class Register extends AppCompatActivity implements View.OnClickListener{

    Button bRegister;
    EditText etVorname, etNachname, etBenutzername, etPasswort, etRePasswort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etVorname = (EditText) findViewById(R.id.etVorname);
        etNachname= (EditText) findViewById(R.id.etNachname);
        etBenutzername= (EditText) findViewById(R.id.etBenutzername);
        etPasswort= (EditText) findViewById(R.id.etPasswort);
        etRePasswort= (EditText) findViewById(R.id.etRePasswort);
        bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(this);
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bRegister:

                
                break;
        }
        }
    }
}