package com.mobproto.flamingoctopus;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lego6245 on 11/20/13.
 */
public class ScorboardListFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        List<String> data = MainActivity.activeUsers;
        final ScorboardListAdapter adapter = new ScorboardListAdapter(getActivity(), R.layout.scoreboard_list_item, data);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);


        return rootView;
    }
}
