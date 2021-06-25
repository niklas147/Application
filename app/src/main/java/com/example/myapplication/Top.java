package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Top extends AppCompatActivity {


    //Drawer
    DrawerLayout drawerLayout;
    ImageView btMenu;
    RecyclerView recyclerView;
    //Drawer end

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        //Drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        btMenu = findViewById(R.id.bt_menu);
        recyclerView = findViewById(R.id.recycler_view);

        //Set Layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Set adapter
        recyclerView.setAdapter(new MainAdapter(this,MainActivity2.arrayList));

        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open drawer
                drawerLayout.openDrawer(GravityCompat.START);


            }
        });
        //Drawer End

        //conection to firebase
        fAuth           = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getInstance().getCurrentUser();


        //Lists for "User" "Count" "Ergebnis"
        ArrayList<String> arrayListUser =  new ArrayList<>();
        ArrayList<String> arrayListErgebnis =  new ArrayList<>();
        ArrayList<String> arrayListCount =  new ArrayList<>();


        //Array Adapter for ListView
        ArrayAdapter arrayAdapterNot = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayListErgebnis);



    }
}