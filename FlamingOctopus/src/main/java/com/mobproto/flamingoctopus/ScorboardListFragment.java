package com.mobproto.flamingoctopus;

import android.app.Fragment;
import android.os.Bundle;
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

        List<String> data = new ArrayList<String>();
        data.add("9737659298");
        final ScorboardListAdapter adapter = new ScorboardListAdapter(getActivity(), R.layout.scoreboard_list_item, data);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);

        return rootView;
    }
}
