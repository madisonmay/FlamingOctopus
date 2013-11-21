package com.mobproto.flamingoctopus;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.EditText;


public class AddTaskDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(inflater.inflate(R.layout.add_task_dialog, null))
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText editText = (EditText) AddTaskDialogFragment.this.getDialog().findViewById(R.id.new_task_edit_text);
                        CheckBox checkBox = (CheckBox) AddTaskDialogFragment.this.getDialog().findViewById(R.id.long_term_check_box);

                        String contents = editText.getText().toString();
                        Boolean longTerm = checkBox.isChecked();

                        ((MainActivity) getActivity()).dbAdapter.createTask(contents, longTerm);
                        ((MainActivity) getActivity()).updateListViews();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
