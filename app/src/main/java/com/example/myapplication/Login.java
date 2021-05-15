package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityLoginBinding;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button bLogin, bRegisterLink;
    EditText etUsername, etPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etBenutzername);
        etPassword = (EditText) findViewById(R.id.etPasswort);
        bLogin = (Button) findViewById(R.id.bLogin);
        bRegisterLink = (Button) findViewById(R.id.bRegisterLink);
        bLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bLogin:


                break;
            case R.id.bRegisterLink:
                startActivity(new Intent(this, Register.class));
                break;

        }
    }
}