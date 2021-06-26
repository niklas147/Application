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
import android.widget.ListView;


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

public class Top extends AppCompatActivity {


    //Drawer
    DrawerLayout drawerLayout;
    ImageView btMenu;
    RecyclerView recyclerView;
    //Drawer end

    ListView ListViewTop;
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
        ListViewTop = findViewById(R.id.ListViewTop);

        //Lists for "User" "Count" "Ergebnis"
        ArrayList<String> arrayListUser =  new ArrayList<>();
        ArrayList<String> arrayListUserFinal =  new ArrayList<>();
        ArrayList<Integer> arrayListCount =  new ArrayList<>();


        //Array Adapter for ListView
        ArrayAdapter arrayAdapterErgebnis = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayListUserFinal);

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
                                        db.collection("Benutzer").document(arrayListUser.get(i)).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                String AnzahlString = documentSnapshot.getString("besucht");
                                                int AnzahlInt = Integer.parseInt(AnzahlString);
                                                String Benutzername = documentSnapshot.getString("Benutzername");
                                                String AnzahlBenutzer = Benutzername +" "+ AnzahlString;
                                                //checks for doubles in array

                                                    if(arrayListUserFinal.isEmpty()){
                                                        arrayListUserFinal.add(0,Benutzername + " Summits: " +AnzahlInt);
                                                        arrayListCount.add(0,AnzahlInt);
                                                    }
                                                    int count = 0;
                                                    for(int i = 0; i < arrayListUserFinal.size();i++ ){

                                                        if(arrayListUserFinal.get(i).equals(Benutzername + " Summits: " +AnzahlInt)){
                                                            count = count +1;
                                                        }
                                                    }
                                                    if(count==0){
                                                        arrayListUserFinal.add(Benutzername + " Summits: " +AnzahlInt);

                                                    for(int i = 0; i<arrayListUserFinal.size(); i++){
                                                        if(arrayListUserFinal.get(i).equals(Benutzername + " Summits: " +AnzahlInt)){
                                                            arrayListCount.add(i,AnzahlInt);
                                                        }
                                                    }}

                                                    int temp;
                                                    String tempString;

                                                    for(int i = arrayListCount.size()-1; i >0 ; i--){
                                                        for(int j = 0; j <i; j++) {
                                                            if(arrayListCount.get(j) < arrayListCount.get(j+1)){
                                                                temp = arrayListCount.get(j);
                                                                arrayListCount.set(j,arrayListCount.get(j+1));
                                                                arrayListCount.set(j+1,temp);
                                                                tempString = arrayListUserFinal.get(j);
                                                                arrayListUserFinal.set(j,arrayListUserFinal.get(j+1));
                                                                arrayListUserFinal.set(j+1,tempString);
                                                            }
                                                        }
                                                    }

                                                ListViewTop.setAdapter(arrayAdapterErgebnis);

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
}