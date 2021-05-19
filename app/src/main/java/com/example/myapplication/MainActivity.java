package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;




import com.google.firebase.firestore.FirebaseFirestore;



public class MainActivity extends AppCompatActivity  {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText etUpdateName, etUpdateLast;
    private  Button bUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);







    }
    public void gotoLogin(View view) {
        startActivity(new Intent(getApplicationContext(), Login.class));
    }


    }

