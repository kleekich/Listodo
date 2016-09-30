package com.example.kangsik.listodo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Kangsik on 9/28/16.
 */

public class AddActivity extends AppCompatActivity  {

    private static EditText editTextTitle;
    private static EditText editTextDescription;
    private static TextView textViewDate;
    private static Button buttonCreate;
    private static Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
        buttonCreate = (Button) findViewById(R.id.buttonCreate);
        spinner = (Spinner) findViewById(R.id.spinner);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String title = extras.getString("TASK_TITLE");
        editTextTitle.setText(title);





        buttonCreate.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveItem();
                        startActivity(new Intent(AddActivity.this, MainActivity.class));
                        finish();
                    }
                }

        );


    }

    private final void saveItem() {
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_TITLE, editTextTitle.getText().toString());
        values.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, editTextDescription.getText().toString());
        values.put(TaskContract.TaskEntry.COLUMN_DATE, textViewDate.getText().toString());
        values.put(TaskContract.TaskEntry.COLUMN_PRIORITY, spinner.getSelectedItem().toString());


        Log.d("AddActivity", values.toString());
        ContentResolver contentResolver = getContentResolver();
        Uri uri = TaskContract.TaskEntry.CONTENT_URI;
        contentResolver.insert(uri, values);
    }

    /*
    public void onCreateItem(View v) {

        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();

        Intent addIntent = new Intent(this, AddActivity.class);
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        addIntent.putExtra("TASK_TITLE", itemText);
        startActivity(addIntent);
    }

    */
    public void onCreateItem(View v) {

    }




    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            textViewDate.setText(month + "/" + day + "/" + year);
        }


    }

}
