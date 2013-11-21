package com.mobproto.flamingoctopus;

import android.support.v4.app.Fragment;
import android.os.Bundle;
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
        adapter.add("9737659298");
        adapter.add("2012807565");
        adapter.add("5402095219");
        adapter.add("0000000000");
        adapter.add("9999999999");
        adapter.add("1111111111");

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);

        return rootView;
    }
}
