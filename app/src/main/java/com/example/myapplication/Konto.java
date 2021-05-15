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
    EditText etName, etLastname, etUsername, etGrade;
    UserLocalStore userLocalStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konto);

        etName = (EditText) findViewById(R.id.etVorname);
        etLastname = (EditText) findViewById(R.id.etNachname);
        etUsername = (EditText) findViewById(R.id.etBenutzername);
        etGrade = (EditText) findViewById(R.id.etGrade);

        bLogout = (Button) findViewById(R.id.bLogout);

        bLogout.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);
    }

    @Override
    protected void onStart(){
        super.onStart();

        if (authenticate() == true){
            displayUserDetails();
        }

    }

    private boolean authenticate(){
        return userLocalStore.getUserLoggedIn();
    }

    private void displayUserDetails(){
        User user = userLocalStore.getLoggedInUser();

        etUsername.setText(user.username);
        etName.setText(user.name);
        etLastname.setText(user.lastname);
        etGrade.setText(user.grade);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bLogout:
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);

                startActivity(new Intent(this, Login.class));
                break;




        }
    }
}