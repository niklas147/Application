package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;


import java.io.File;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    //Initialize variable
    DrawerLayout drawerLayout;
    ImageView btMenu;
    RecyclerView recyclerView;
    static ArrayList<String> arrayList= new ArrayList<>();
    MainAdapter adapter;
    private ImageView ivUploadImage;
    public Uri imageUri;


    public static void closeDrawer(DrawerLayout drawerLayout) {
        //Check condition
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            //when drawer is open
            //close drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

       


        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);
        btMenu= findViewById(R.id.bt_menu);
        recyclerView = findViewById(R.id.recycler_view);

        //Clear array list
        arrayList.clear();

        //Add menu item in array list
        arrayList.add("Home");
        arrayList.add("7 Summits");
        arrayList.add("Konto");


        //Initialize adapter
        adapter = new MainAdapter(this,arrayList);
        //set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //set adapter
        recyclerView.setAdapter(adapter);

        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open drawer
                drawerLayout.openDrawer(GravityCompat.START);


            }
        });


    }







    @Override
    protected void onPause() {
        super.onPause();
        //close drawer
        closeDrawer(drawerLayout);

    }
}