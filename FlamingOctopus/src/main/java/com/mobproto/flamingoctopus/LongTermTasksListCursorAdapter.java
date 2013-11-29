package com.mobproto.flamingoctopus;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;


public class LongTermTasksListCursorAdapter extends CursorAdapter {
    private final LayoutInflater inflater;
    private final TasksDbAdapter dbAdapter;
    private final MainActivity activity;

    public LongTermTasksListCursorAdapter(Context context, Cursor c, TasksDbAdapter dbAdapter) {
        super(context, c, true);
        this.activity = (MainActivity) context;
        inflater = LayoutInflater.from(context);
        this.dbAdapter = dbAdapter;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.long_term_task_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final ViewHolder holder;
        holder = new ViewHolder(TasksDbAdapter.taskFromCursor(cursor), (TextView) view.findViewById(R.id.contents), (Button) view.findViewById(R.id.done_button), (Button) view.findViewById(R.id.make_short));
        view.setTag(holder);

        holder.contentsTextView.setText(holder.task.getContents());
        holder.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbAdapter.deleteTask(holder.task);

                //increment user's score in firebase
                MainActivity.manager.increment();
                changeCursor(dbAdapter.getAllLongTermTasks());
                notifyDataSetChanged();
            }
        });
        holder.shortTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //move task to other list
                holder.task.longTerm = false;
                dbAdapter.editTask(holder.task);
                changeCursor(dbAdapter.getAllLongTermTasks());
                notifyDataSetChanged();
                activity.updateListViews();
            }
        });

    }

    public class ViewHolder{
        Button shortTermButton;
        Button doneButton;
        Task task;
        TextView contentsTextView;

        private ViewHolder(Task task, TextView contentsTextView, Button doneButton, Button shortTermButton) {
            this.task = task;
            this.contentsTextView = contentsTextView;
            this.doneButton = doneButton;
            this.shortTermButton = shortTermButton;
        }
    }
}