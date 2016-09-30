package com.example.kangsik.listodo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import static com.example.kangsik.listodo.R.id.buttonCreate;


/**
 * Created by Kangsik on 9/28/16.
 */
public class EditNameDialogFragment extends DialogFragment implements TextView.OnEditorActionListener{
    private EditText mEditText;

    private static EditText editTextTitle;
    private static EditText editTextDescription;
    private static TextView textViewDate;
    private static Button buttonEdit;
    private static Spinner spinner;


    public interface EditNameDialogListener {
        void onFinishEditDialog(String inputText);
    }

    public EditNameDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditNameDialogFragment newInstance(int _id, String title, String desc, String date, String prio) {
        EditNameDialogFragment frag = new EditNameDialogFragment();
        Bundle args = new Bundle();
        args.putInt("id", _id);
        args.putString("title", title);
        args.putString("desc", desc);
        args.putString("date", date);
        args.putString("prio", prio);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        editTextTitle = (EditText) view.findViewById(R.id.editTextTitle);
        editTextDescription = (EditText) view.findViewById(R.id.editTextDescription);
        textViewDate = (TextView) view.findViewById(R.id.textViewDate);
        buttonEdit = (Button) view.findViewById(buttonCreate);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle("Edit Task");
        editTextTitle.setText(title);

        String desc = getArguments().getString("desc", "");
        editTextDescription.setText(desc);

        String date = getArguments().getString("date", "");
        textViewDate.setText(date);

        String prio = getArguments().getString("prio", "");



        // Show soft keyboard automatically and request focus to field
        editTextTitle.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        editTextTitle.setOnEditorActionListener(this);

        buttonEdit.setText("Done");
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editItem();
                dismiss();
            }
        });
    }

    public final void editItem(){
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_TITLE, editTextTitle.getText().toString());
        values.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, editTextDescription.getText().toString());
        values.put(TaskContract.TaskEntry.COLUMN_DATE, textViewDate.getText().toString());
        values.put(TaskContract.TaskEntry.COLUMN_PRIORITY, spinner.getSelectedItem().toString());
        ContentResolver contentResolver = getActivity().getContentResolver();
        Uri uri = TaskContract.TaskEntry.CONTENT_URI;
        String id = String.valueOf(getArguments().getInt("id", 0));
        String selection = TaskContract.TaskEntry._ID + " = " + id;
        contentResolver.update(uri, values, selection, null);
    }

    // Fires whenever the textfield has an action performed
    // In this case, when the "Done" button is pressed
    // REQUIRES a 'soft keyboard' (virtual keyboard)
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text back to activity through the implemented listener
            EditNameDialogListener listener = (EditNameDialogListener) getActivity();
            listener.onFinishEditDialog(editTextTitle.getText().toString());
            // Close the dialog and return back to the parent activity
            dismiss();
            return true;
        }
        return false;
    }

    // Call this method to send the data back to the parent fragment
    public void sendBackResult() {
        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        EditNameDialogListener listener = (EditNameDialogListener) getTargetFragment();
        listener.onFinishEditDialog(editTextTitle.getText().toString());
        dismiss();
    }






}
