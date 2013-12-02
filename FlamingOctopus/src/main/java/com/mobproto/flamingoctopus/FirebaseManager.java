package com.mobproto.flamingoctopus;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.FirebaseException;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mmay on 11/19/13.
 */
public class FirebaseManager {
    public String number;
    public Long score;
    public ArrayList<HashMap<String, String>> contacts;
    public Firebase ref;
    public Firebase users;
    public Firebase user;
    public ArrayList<String> friends;


    public FirebaseManager(String number, ArrayList<HashMap<String, String>> contacts) {
        this.number = number;
        this.contacts = contacts;
    }

    public void setup(final String username) {
        //Initialize firebase connection
        // Create a reference to a Firebase location
        Firebase ref = new Firebase("https://flamingoctopus.firebaseIO.com/");
        this.ref = ref;
        this.users = ref.child("users");
        this.user = users.child(number);

        // Set score to 0 if user does not have score already
        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    user.child("score").setValue(0);
                    user.child("name").setValue(username);
                    score = 0L;
                } else {
                    score = (Long) ((HashMap) snapshot.getValue()).get("score");
                }
            }

            @Override
            public void onCancelled(FirebaseError e) {
            }
        });
    }


    public void getActiveUsers(final ArrayList<String> phoneNumbers) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Object value = snapshot.getValue();
                if (value == null) {
                   //users ref doesn't exist
                } else {
                    for (String phone: phoneNumbers) {
                        Object user = ((Map) value).get(phone);
                        if (user != null) {
                            MainActivity.activeUsers.add(phone);
                        }
                    }
                    MainActivity.usersFound();
                }
            }

            @Override
            public void onCancelled(FirebaseError e) {
            }
        });
    }

    public boolean increment() {
        //increments users score by one when note is completed
        try {
            score++;
            user.setValue(score);
            return true; //if successful
        } catch (FirebaseException e) {
            e.printStackTrace();
            return false;
        }
    }



}
