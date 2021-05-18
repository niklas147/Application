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



public class Konto extends AppCompatActivity implements Functions {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Button bLogout, bgetInfo;
    TextView etName, etLastname, etUsername, etGrade;

    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konto);

        etName          =  findViewById(R.id.etVorname);
        etLastname      =  findViewById(R.id.etNachname);
        etUsername      =  findViewById(R.id.etBenutzername);
        etGrade         =  findViewById(R.id.etGrade);
        bLogout         =  findViewById(R.id.bLogout);


        fAuth           = FirebaseAuth.getInstance();

        FirebaseUser user = fAuth.getInstance().getCurrentUser();

        if (user !=null){


            String emailcon = emailtoString(user.getEmail());

            db.collection(emailcon).document("Persönliche Daten").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    String name = documentSnapshot.getString("Name");
                    String lastname = documentSnapshot.getString("Nachname");
                    String grade = documentSnapshot.getString("Klasse");
                    String username = documentSnapshot.getString("Benutzername");

                    etLastname.setText(lastname);
                    etName.setText(name);
                    etGrade.setText(grade);
                    etUsername.setText(username);
                }
            });
        }
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }


}
