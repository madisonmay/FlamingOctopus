package com.mobproto.flamingoctopus;

import android.app.Activity;
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

import java.util.List;
import java.util.Map;

/**
 * Created by lego6245 on 11/20/13.
 */
public class ScorboardListAdapter extends ArrayAdapter {
    private List<String> data;
    private Activity activity;
    private Firebase ref;

    public ScorboardListAdapter(Activity a, int viewResourceId, List<String> data){
        super(a, viewResourceId, data);
        this.data = data;
        this.activity = a;
        Firebase ref = new Firebase("https://flamingoctopus.firebaseIO.com/").child("users");
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        View v = convertView;
        if (v==null){
            LayoutInflater vi = (LayoutInflater) activity.getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.scoreboard_list_item, null);
        }

        final TextView score = (TextView) v.findViewById(R.id.scoreboardScore);
        final TextView name = (TextView) v.findViewById(R.id.scorboardName);
        ref.child(data.get(position)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue();
                if( value != null)
                {
                    name.setText((String)((Map)value).get("name"));
                    score.setText((String)((Map)value).get("score"));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        // ...

        return v;
    }
}
