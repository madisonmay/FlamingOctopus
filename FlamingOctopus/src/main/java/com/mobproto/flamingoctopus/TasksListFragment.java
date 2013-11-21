package com.mobproto.flamingoctopus;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class TasksListFragment extends Fragment {
    MainActivity activity;
    ListView listView;
    TasksListCursorAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        activity = (MainActivity) getActivity();

        adapter = new TasksListCursorAdapter(activity, activity.dbAdapter.getAllTasks(), activity.dbAdapter);
        listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);

        return rootView;
    }


    public void updateListView() {
        adapter.changeCursor(activity.dbAdapter.getAllTasks());
        adapter.notifyDataSetChanged();
    }
}
