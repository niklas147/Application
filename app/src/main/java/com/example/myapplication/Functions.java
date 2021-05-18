package com.example.myapplication;

public interface Functions {

    public default String emailtoString(String email){
        StringBuffer s1 = new StringBuffer();
        s1.setLength(0);
        String user = null;
        for(int i = 0; i < email.length(); i++){
            Character charI = email.charAt(i);
            s1.append(charI);

            user = s1.toString();

        }
        return user;
    }

    String x = "hey";

}

