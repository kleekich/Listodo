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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import static com.example.kangsik.listodo.TaskContract.TaskEntry;

public class MainActivity extends AppCompatActivity implements com.example.kangsik.listodo.EditNameDialogFragment.EditNameDialogListener, LoaderManager.LoaderCallbacks<Cursor> {


    Context context;

    private TaskAdapter mTaskAdapter;
    private ListView listView;
    private int mPosition = ListView.INVALID_POSITION;
    private static final String SELECTED_KEY = "selected_position";


    static final int COL_ID = 0;
    static final int COL_TITLE = 1;
    static final int COL_DESCRIPTION = 2;
    static final int COL_DATE = 3;
    static final int COL_PRIORITY = 4;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mPosition != ListView.INVALID_POSITION){
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

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

        mTaskAdapter = new TaskAdapter(this, null, 0);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(mTaskAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                if (cursor != null){
                }
                mPosition = position;
            }
        });

        if(savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)){
          mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

        Button btnAddItem = (Button) findViewById(R.id.btnAddItem);

        btnAddItem.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent addIntent = new Intent(getApplicationContext(), AddActivity.class);
                        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
                        String itemText = etNewItem.getText().toString();
                        addIntent.putExtra("TASK_TITLE", itemText);
                        startActivity(addIntent);
                    }
                }

        );

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




    @Override
    public void onFinishEditDialog(String inputText) {
        Toast.makeText(this, "Hi, " + inputText, Toast.LENGTH_SHORT).show();
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
        mTaskAdapter.swapCursor(data);
        if (mPosition != ListView.INVALID_POSITION){
            listView.smoothScrollToPosition(mPosition);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mTaskAdapter.swapCursor(null);
    }
}
