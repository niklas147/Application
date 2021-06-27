package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.HashMap;

public class Summits extends AppCompatActivity {
    //Initialize variable
    TextView TextView;
    ImageView imageViewTop;
    ListView ListViewYes, ListViewNot;
    DrawerLayout drawerLayout;
    ImageView btMenu;
    RecyclerView recyclerView;
    Button buttonBesucht;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summits);

        //conection to firebase
        fAuth           = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getInstance().getCurrentUser();

        //get Summit which was clicked
        Intent intent = getIntent();
        String berg = intent.getExtras().getString("berg");

        //adds Objects
        buttonBesucht = (Button) findViewById(R.id.buttonBesucht);
        ListViewYes = (ListView)findViewById(R.id.ListViewYes);
        ListViewNot = (ListView)findViewById(R.id.ListViewNot);
        TextView = findViewById(R.id.textViewBesucht);
        imageViewTop = findViewById(R.id.imageViewTop);

        //Lists for "besucht" "nicht besucht"
        ArrayList<String> arrayListYes =  new ArrayList<>();
        ArrayList<String> arrayListNot =  new ArrayList<>();
        ArrayList<String> arrayListUser =  new ArrayList<>();


        //Array Adapter for ListView
        ArrayAdapter arrayAdapterNot = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayListNot);
        ArrayAdapter arrayAdapterYes = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayListYes);



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

        //sets imageView to current Summit
        int res = getResources().getIdentifier(berg, "drawable", getPackageName());
        imageViewTop.setImageResource(res);


        //checks if current user visited summit and disables button if so
        db.collection("Benutzer").document(user.getEmail()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String Berg = documentSnapshot.getString(berg);
                if(Berg.equals("besucht")){
                    buttonBesucht.setEnabled(false);
                    buttonBesucht.setText("bereits besucht");
                }
            }
        });



        //save User to arrayListYes and arrayListNot if visited current summit or not
        if (user !=null){

            //gets grade from current user
            db.collection("Benutzer").document(user.getEmail()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    String grade = documentSnapshot.getString("Klasse");

                    //gets all users within same grade
                    db.collection(grade).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(Task<QuerySnapshot> task) {
                            if(task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult())     {

                                    arrayListUser.add(document.getId());

                                        //checks if "besucht" or "nicht besucht"
                                        for(int i = 0;i < arrayListUser.size(); i ++){
                                        db.collection(grade).document(arrayListUser.get(i)).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                String name = documentSnapshot.getString(berg);
                                                String Benutzername = documentSnapshot.getString("Benutzername");

                                                //checks for doubles in array
                                                if(name.equals("besucht")){
                                                    if(arrayListYes.isEmpty()){
                                                        arrayListYes.add(Benutzername);
                                                    }
                                                    int count = 0;
                                                    for(int i = 0; i < arrayListYes.size();i++ ){

                                                        if(arrayListYes.get(i).equals(Benutzername)){
                                                            count = count +1;
                                                        }
                                                    }
                                                    if(count==0){
                                                        arrayListYes.add(Benutzername);
                                                    }}

                                                if(name.equals("nicht besucht")){
                                                    if(arrayListNot.isEmpty()){
                                                        arrayListNot.add(Benutzername);
                                                    }
                                                    int count = 0;
                                                    for(int i = 0; i <arrayListNot.size();i++ ){

                                                        if(arrayListNot.get(i).equals(Benutzername)){
                                                            count = count +1;
                                                        }
                                                    }
                                                    if(count==0){
                                                        arrayListNot.add(Benutzername);
                                                    }}
                                                //sets Listview with users
                                                ListViewYes.setAdapter(arrayAdapterYes);
                                                ListViewNot.setAdapter(arrayAdapterNot);
                                            }
                                        });
                                        }
                                }
                            }
                        }
                    });
                }
            });
        }
    }


    public void gotoMap(View view) {
        Intent intent = getIntent();
        String berg = intent.getExtras().getString("berg");
        Intent intent1 = new Intent(getApplicationContext(), MapsActivity.class);


        intent1.putExtra("berg", berg);
        startActivity(intent1);
    }


    //changed Current logged in User for Current Summit to "besucht" and adds +1 to overall visited
    public void change(View view) {
        Intent intent = getIntent();
        String berg = intent.getExtras().getString("berg");
        FirebaseUser user = fAuth.getInstance().getCurrentUser();
        HashMap hashMap = new HashMap();
        hashMap.put(berg,"besucht");
        db.collection("Benutzer").document(user.getEmail()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String grade = documentSnapshot.getString("Klasse");
                String besuchtString = documentSnapshot.getString("besucht");
                int besuchtInt = Integer.parseInt(besuchtString);
                besuchtInt = besuchtInt +1;
                besuchtString = Integer.toString(besuchtInt);

                hashMap.put("besucht",besuchtString);
                db.collection(grade).document(user.getEmail()).update(hashMap);
                db.collection("Benutzer").document(user.getEmail()).update(hashMap);
            }
        });

    }
}