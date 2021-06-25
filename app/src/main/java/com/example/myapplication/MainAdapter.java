package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    //Initialize variable
    Activity activity;
    ArrayList <String> arrayList;

    //Create constructor
    public MainAdapter(Activity activity,ArrayList<String> arrayList){
        this.activity = activity;
        this.arrayList =arrayList;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_drawer_main,parent,false);
        //Return holder view
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        //Set text on text view
        holder.textView.setText(arrayList.get(position));

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get clicked item position
                int position = holder.getAdapterPosition();
                //Check condition
                switch (position){
                    case 0:
                        //when position is equal to 0
                        //Redirect to home page
                        activity.startActivity(new Intent(activity, MainActivity2.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;

                    case 1:
                        //when position is equal to 2
                        //redirect to konto page
                        activity.startActivity(new Intent(activity,Konto.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 2:
                        //when position is equal to 2
                        //redirect to Top page
                        activity.startActivity(new Intent(activity,Top.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 3:
                        // when position is equal to 3
                        //initialize alert dialog
                        AlertDialog.Builder builder= new AlertDialog.Builder(activity);
                        //set title
                        builder.setTitle("Logout");
                        //set message
                        builder.setMessage("Are you sure you want to logout?");
                        //positive yes button
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //finish all activity
                                activity.finishAffinity();
                                //exit app
                                System.exit(0);
                            }
                        });
                        //negative cancel button
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //dismiss dialog
                                dialog.dismiss();
                            }
                        });
                        //show dialog
                        builder.show();
                        break;




                }
            }
        });


    }

    @Override
    public int getItemCount() {
        //Return array list size
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Initialize variable
        TextView textView;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variable
            textView = itemView.findViewById(R.id.text_view);

        }
    }
}
