package com.mobproto.flamingoctopus;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class TasksListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final TasksDbAdapter dbAdapter = new TasksDbAdapter(getActivity());
        dbAdapter.open();

        final TasksListCursorAdapter adapter = new TasksListCursorAdapter(getActivity(), dbAdapter.getAllTasks(), dbAdapter);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);

        return rootView;
    }

}
