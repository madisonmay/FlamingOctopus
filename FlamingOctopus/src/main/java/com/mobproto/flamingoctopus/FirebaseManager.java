package com.mobproto.flamingoctopus;

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
