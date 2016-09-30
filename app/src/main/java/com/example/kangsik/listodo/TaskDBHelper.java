package com.example.kangsik.listodo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.kangsik.listodo.TaskContract.*;

/**
 * Created by Kangsik on 9/29/16.
 */

public class TaskDBHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "task.db";

    public TaskDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TASK_TABLE = "CREATE_TABLE " + TaskEntry.TABLE_NAME + " (" + TaskEntry._ID + " INTEGER PRIMARY KEY," +
                TaskEntry.COLUMN_TITLE+ " TEXT NOT NULL, " +
                TaskEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                TaskEntry.COLUMN_DATE + " TEXT NOT NULL, " +
                TaskEntry.COLUMN_PRIORITY + " TEXT NOT NULL " +
                " );";

        db.execSQL(SQL_CREATE_TASK_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskEntry.TABLE_NAME);
        onCreate(db);
    }
}
