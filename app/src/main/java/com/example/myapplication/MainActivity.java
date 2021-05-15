package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bGotoLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bGotoLogin = (Button) findViewById(R.id.bGotoLogin);
        bGotoLogin.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()) {


            case R.id.bGotoLogin:
                startActivity(new Intent(this, Login.class));
                break;
        }
        }
    }