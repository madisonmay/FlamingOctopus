package com.mobproto.flamingoctopus;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.firebase.*;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lego6245 on 11/20/13.
 */
public class ScorboardListAdapter extends ArrayAdapter {
    private List<String> data;
    private Activity activity;
    final private Firebase ref;

    public ScorboardListAdapter(Activity a, int viewResourceId, List<String> data){
        super(a, viewResourceId, data);
        this.data = data;
        this.activity = a;

        //kick ass repo name
        ref = new Firebase("https://flamingoctopus.firebaseIO.com/").child("users");
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        View v = convertView;
        if (v==null){
            LayoutInflater vi = (LayoutInflater) activity.getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.scoreboard_list_item, null);
        }

        //retrieve values from view
        final TextView score = (TextView) v.findViewById(R.id.scoreboardScore);
        final TextView name = (TextView) v.findViewById(R.id.scorboardName);
        final String item = data.get(position);

        ref.child(item).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //live updating courtesy of firebase api
                Object value = dataSnapshot.getValue();
                if (value != null) {
                    if ((String) ((Map) value).get("name") == null || ((String) ((Map) value).get("name")).isEmpty()) {
                        name.setText("Opponent");
                    } else {
                        name.setText((String) ((Map) value).get("name"));
                    }
                    if (((Map) value).get("score") == null) {
                        score.setText("0");
                    } else {
                        score.setText((((Map) value).get("score")).toString());
                    }
                }

                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return v;
    }

}
