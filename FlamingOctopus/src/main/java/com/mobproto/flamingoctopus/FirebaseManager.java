package com.mobproto.flamingoctopus;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mmay on 11/19/13.
 */
public class FirebaseManager {
    String name;
    String number;
    Long score;
    ArrayList<HashMap<String, String>> contacts;


    public FirebaseManager(String name, String number, Long score, ArrayList<HashMap<String, String>> contacts) {
        this.name = name;
        this.number = number;
        this.score = score;
        this.contacts = contacts;
    }

    public void setup() {
        //Initialize firebase connection
        // Create a reference to a Firebase location
        Firebase ref = new Firebase("https://myapp.firebaseIO-demo.com/");

// Write data to Firebase
        ref.setValue("Do you have data? You'll love Firebase.");

// Read data and react to changes
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snap) {
                System.out.println(snap.getName() + " -> " + snap.getValue());
            }

            @Override public void onCancelled(FirebaseError e) { }
        });
    }


    public void populateScores() {
        //modifies contacts to add public scores to array list
    }


    public ArrayList<HashMap<String, String>> getTopNScores() {
        //Returns top n scores from this.contacts
        return null;
    }

    public boolean increment() {
        //increments users score by one when note is completed
        return true; //if successful
    }



}
