package com.mobproto.flamingoctopus;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


public class TasksListCursorAdapter extends CursorAdapter {
    private final LayoutInflater inflater;
    private final TasksDbAdapter dbAdapter;

    public TasksListCursorAdapter(Context context, Cursor c, TasksDbAdapter dbAdapter) {
        super(context, c, true);
        inflater = LayoutInflater.from(context);
        this.dbAdapter = dbAdapter;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.tasks_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final ViewHolder holder;
        holder = new ViewHolder(TasksDbAdapter.taskFromCursor(cursor), (TextView) view.findViewById(R.id.contents));
        view.setTag(holder);

        holder.contentsTextView.setText(holder.task.getContents());
    }

    public class ViewHolder{
        Task task;
        TextView contentsTextView;

        private ViewHolder(Task task, TextView contentsTextView) {
            this.task = task;
            this.contentsTextView = contentsTextView;
        }
    }
}
