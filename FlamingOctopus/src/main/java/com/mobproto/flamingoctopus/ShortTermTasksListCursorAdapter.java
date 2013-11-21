package com.mobproto.flamingoctopus;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;


public class ShortTermTasksListCursorAdapter extends CursorAdapter {
    private final LayoutInflater inflater;
    private final TasksDbAdapter dbAdapter;

    public ShortTermTasksListCursorAdapter(Context context, Cursor c, TasksDbAdapter dbAdapter) {
        super(context, c, true);
        inflater = LayoutInflater.from(context);
        this.dbAdapter = dbAdapter;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.short_term_tasks_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final ViewHolder holder;
        holder = new ViewHolder(TasksDbAdapter.taskFromCursor(cursor), (TextView) view.findViewById(R.id.contents), (Button) view.findViewById(R.id.done_button));
        view.setTag(holder);

        holder.contentsTextView.setText(holder.task.getContents());
        holder.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbAdapter.deleteTask(holder.task);
                changeCursor(dbAdapter.getAllShortTermTasks());
                notifyDataSetChanged();
            }
        });

    }

    public class ViewHolder{
        Button doneButton;
        Task task;
        TextView contentsTextView;

        private ViewHolder(Task task, TextView contentsTextView, Button doneButton) {
            this.task = task;
            this.contentsTextView = contentsTextView;
            this.doneButton = doneButton;
        }
    }
}
