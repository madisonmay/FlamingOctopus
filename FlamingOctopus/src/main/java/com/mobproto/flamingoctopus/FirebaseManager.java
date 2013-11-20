package com.mobproto.flamingoctopus;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.FirebaseException;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

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


    public FirebaseManager(String number, ArrayList<HashMap<String, String>> contacts) {
        this.number = number;
        this.contacts = contacts;
    }

    public void setup() {
        //Initialize firebase connection
        // Create a reference to a Firebase location
        Log.d("Setup", "Triggered");
        Firebase ref = new Firebase("https://flamingoctopus.firebaseIO.com/");
        this.ref = ref;
        this.users = ref.child("users");
        this.user = users.child(number);

        // Set score to 0 if user does not have score already
        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d("SNAPSHOT", String.valueOf(snapshot));
                if (snapshot.getValue() == null) {
                    user.child("score").setValue(0);
                    score = 0L;
                } else {
                    score = (Long) snapshot.getValue();
                }
            }

            @Override
            public void onCancelled(FirebaseError e) {
            }
        });
    }


    public void populateScores() {
        //Must be called before getTopNScores
        for (final HashMap<String, String> contact: contacts) {
            users.child(contact.get("number")).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot == null) {
                        user.child("score").setValue(0);
                        contact.put("score", "0");
                    } else {
                        score = (Long) snapshot.getValue();
                        contact.put("score", String.valueOf(score));
                    }
                }

                @Override
                public void onCancelled(FirebaseError e) {
                }
            });
        }
        //modifies contacts to add public scores to array list
    }


    public ArrayList<HashMap<String, String>> getTopNScores() {
        //Returns top n scores from this.contacts
        //Must be called after populating local arraylist
        return null;
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
