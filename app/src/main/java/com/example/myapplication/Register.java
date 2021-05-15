package com.example.myapplication;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityRegisterBinding;

public class Register extends AppCompatActivity implements View.OnClickListener{

    Button bRegister;
    EditText etName, etLastname, etUsername, etPassword, etRePassword, etGrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = (EditText) findViewById(R.id.etVorname);
        etLastname= (EditText) findViewById(R.id.etNachname);
        etUsername= (EditText) findViewById(R.id.etBenutzername);
        etPassword= (EditText) findViewById(R.id.etPasswort);
        etRePassword= (EditText) findViewById(R.id.etRePasswort);
        bRegister = (Button) findViewById(R.id.bRegister);
        etGrade= (EditText) findViewById(R.id.etGrade);

        bRegister.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bRegister:

                
                break;

        }
    }
}