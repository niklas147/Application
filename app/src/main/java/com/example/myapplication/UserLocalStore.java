package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class UserLocalStore {
    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context){
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("name", user.name);
        spEditor.putString("lastname", user.lastname);
        spEditor.putString("username", user.username);
        spEditor.putString("password", user.password);
        spEditor.putString("grade", user.grade);
        spEditor.commit();
    }
    public User getLoggedInUser(){
        String name = userLocalDatabase.getString("name", "");
        String lastname = userLocalDatabase.getString("lastname", "");
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password", "");
        String grade = userLocalDatabase.getString("grade", "");

        User storedUser = new User(name, lastname, username, password, grade);

        return storedUser;
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();

    }
}

