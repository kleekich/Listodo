package com.example.kangsik.listodo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Kangsik on 9/28/16.
 */

public class AddActivity extends AppCompatActivity  {

    private static EditText editTextTitle;
    private static TextView textViewDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editTextTitle = (EditText) findViewById(R.id.editTextTitle);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String title = extras.getString("TASK_TITLE");
        editTextTitle.setText(title);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
/*
        Button btnCreate = (Button) findViewById(btnCreate);


        btnCreate.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent mainIntent = new Intent(this, MainActivity.class);
                        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
                        String itemText = etNewItem.getText().toString();
                        addIntent.putExtra("TASK_TITLE", itemText);
                        startActivity(addIntent);
                    }
                }

        );
        */

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
            // Do something with the date chosen by the user
        /*
        monthOfYear++;
        if(year == mYear) {
            if(monthOfYear == mMonth + 1) {
                if(dayOfMonth == mDay) {
                    sDateTextView.setText(AppConstant.TODAY);
                } else {
                    sDateTextView.setText(monthOfYear + "/" + dayOfMonth + "/" + year);
                }
            } else {
                sDateTextView.setText(monthOfYear + "/" + dayOfMonth + "/" + year);
            }
        } else {
            sDateTextView.setText(monthOfYear + "/" + dayOfMonth + "/" + year);
        }

        sYear = year;
        sMonth = monthOfYear;
        sDay = dayOfMonth;
        sDateTextView.setVisibility(View.VISIBLE);
        dateTimeDeleteImageView.setVisibility(View.VISIBLE);
        */

            textViewDate.setText(month + "/" + day + "/" + year);
        }


    }

}
