package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.StringValue;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Konto extends AppCompatActivity  {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Button bLogout, bgetInfo;
    TextView etName, etLastname, etUsername, etGrade;
    String email;
    FirebaseAuth fAuth;
    String email2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konto);

        etName =  findViewById(R.id.etVorname);
        etLastname =  findViewById(R.id.etNachname);
        etUsername =  findViewById(R.id.etBenutzername);
        etGrade =  findViewById(R.id.etGrade);
        bLogout =  findViewById(R.id.bLogout);
        bgetInfo =  findViewById(R.id.bgetInfo);

        fAuth           = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getInstance().getCurrentUser();





        email = user.getEmail();

        email2 = String.valueOf(email);

        bgetInfo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                StringBuffer s1 = new StringBuffer();
                s1.setLength(0);
                email = user.getEmail();

                for(int i = 0; i < email.length(); i++){
                    Character charI = email.charAt(i);
                    s1.append(charI);
                }

                /*Character b1 = 'n';
                Character b2 = '@';
                Character b3 = 'g';
                Character b4 = '.';
                Character b5 = 'c';
                Character b6 = 'o';
                Character b7 = 'm';
                StringBuffer s1 = new StringBuffer().append(b1).append(b2).append(b3).append(b4).append(b5).append(b6).append(b7);*/

                String userda = s1.toString();

                db.collection("users").document(userda).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {


                Toast.makeText(Konto.this, userda, Toast.LENGTH_SHORT).show();

                    String name = documentSnapshot.getString("Name");
                    String lastname = documentSnapshot.getString("Nachname");
                    String grade = documentSnapshot.getString("Klasse");
                    String username = documentSnapshot.getString("Benutzername");

                    etLastname.setText(lastname);
                    etName.setText(name);
                    etGrade.setText(grade);
                    etUsername.setText(username);



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Konto.this, "Konto konnte nicht geladen werden", Toast.LENGTH_SHORT).show();
            }
        });
    }
});
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }


    }
