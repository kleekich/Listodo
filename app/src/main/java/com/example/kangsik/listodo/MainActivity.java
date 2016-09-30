package com.example.kangsik.listodo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.kangsik.listodo.TaskContract.TaskEntry;

public class MainActivity extends AppCompatActivity implements com.example.kangsik.listodo.EditNameDialogFragment.EditNameDialogListener, LoaderManager.LoaderCallbacks<Cursor> {


    Context context;
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    private Uri mUri;

    private static final String[] DETAIL_COLUMS = {
            TaskEntry.TABLE_NAME + "." + TaskEntry._ID,
            TaskEntry.COLUMN_TITLE,
            TaskEntry.COLUMN_DESCRIPTION,
            TaskEntry.COLUMN_DATE,
            TaskEntry.COLUMN_PRIORITY
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        Button btnAddItem = (Button) findViewById(R.id.btnAddItem);

        context = this;
        btnAddItem.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent addIntent = new Intent(context, AddActivity.class);
                        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
                        String itemText = etNewItem.getText().toString();
                        addIntent.putExtra("TASK_TITLE", itemText);
                        startActivity(addIntent);
                    }
                }

        );
        setupListViewListener();

    }



    private void showEditDialog(String title) {
        FragmentManager fm = getSupportFragmentManager();
        EditNameDialogFragment editNameDialogFragment = EditNameDialogFragment.newInstance(title);
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

    public void onAddItem(View v) {
        /*
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
        */
        Intent addIntent = new Intent(this, AddActivity.class);
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        addIntent.putExtra("TASK_TITLE", itemText);
        startActivity(addIntent);
    }


    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }

                });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                /*
                Intent disscussIntent = new Intent(context, MessageActivity.class);
                Bundle extras = new Bundle();



                extras.putString("MENTOR_NAME", mentorName);

                disscussIntent.putExtras(extras);
                startActivity(disscussIntent);
                */
                String todoItem = (String) adapterView.getItemAtPosition(position);
                //EditNameDialogFragment frag = new EditNameDialogFragment.newInstance(todoItem);
                showEditDialog(todoItem);
            }
        });
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        Toast.makeText(this, "Hi, " + inputText, Toast.LENGTH_SHORT).show();
    }


    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));

        }catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortOrder = TaskEntry._ID + "DESC";

        Uri taskUri = TaskEntry.CONTENT_URI;
        return new CursorLoader(this,
                taskUri,
                DETAIL_COLUMS,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            int taskId = data.getInt(COL_TASK_CONDITION_ID);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
