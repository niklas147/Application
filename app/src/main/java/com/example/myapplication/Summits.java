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

public class Summits extends AppCompatActivity {
    //Initialize variable
    TextView TextView;
    ImageView imageViewTop;
    ListView ListViewYes, ListViewNot;
    DrawerLayout drawerLayout;
    ImageView btMenu;
    RecyclerView recyclerView;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summits);
        Intent intent = getIntent();
        String berg = intent.getExtras().getString("berg");
        ListViewYes = (ListView)findViewById(R.id.ListViewYes);
        ListViewNot = (ListView)findViewById(R.id.ListViewNot);
        ArrayList<String> arrayListYes =  new ArrayList<>();
        ArrayList<String> arrayListNot =  new ArrayList<>();
        ArrayList<String> arrayListUser =  new ArrayList<>();

        TextView = (TextView)findViewById(R.id.textView2);

        ArrayAdapter arrayAdapterNot = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayListNot);

        ArrayAdapter arrayAdapterYes = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayListYes);



        //Drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        btMenu = findViewById(R.id.bt_menu);
        recyclerView = findViewById(R.id.recycler_view);
        //Drawer end

        fAuth           = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getInstance().getCurrentUser();

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
        imageViewTop = findViewById(R.id.imageViewTop);

        int res = getResources().getIdentifier(berg, "drawable", getPackageName());
        imageViewTop.setImageResource(res);


        if (user !=null){
            db.collection("Benutzer").document(user.getEmail()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    String grade = documentSnapshot.getString("Klasse");
                    db.collection(grade).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(Task<QuerySnapshot> task) {
                            if(task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult())     {


                                    arrayListUser.add(document.getId());
                                        for(int i = 0;i < arrayListUser.size(); i ++){
                                        db.collection(grade).document(arrayListUser.get(i)).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                String name = documentSnapshot.getString("Schloss Schwetzingen");
                                                String Benutzername = documentSnapshot.getString("Benutzername");


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



                                                ListViewYes.setAdapter(arrayAdapterYes);
                                                ListViewNot.setAdapter(arrayAdapterNot);
                                            }
                                        });



                                        }
                                }
                            }}});


                }
            });

    }
        Toast.makeText(Summits.this, berg, Toast.LENGTH_SHORT).show();
    }
    public void gotoStartseite(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
        startActivity(intent);

    }
}