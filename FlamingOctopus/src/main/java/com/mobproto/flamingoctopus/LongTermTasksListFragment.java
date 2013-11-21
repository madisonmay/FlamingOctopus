package com.mobproto.flamingoctopus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class LongTermTasksListFragment extends Fragment {
    MainActivity activity;
    ListView listView;
    LongTermTasksListCursorAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        activity = (MainActivity) getActivity();

        adapter = new LongTermTasksListCursorAdapter(activity, activity.dbAdapter.getAllLongTermTasks(), activity.dbAdapter);
        listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);

        return rootView;
    }


    public void updateListView() {
        adapter.changeCursor(activity.dbAdapter.getAllLongTermTasks());
        adapter.notifyDataSetChanged();
    }
}
