package com.example.kangsik.listodo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Kangsik on 9/29/16.
 */

public class TaskProvider extends ContentProvider {

    public static final UriMatcher sUriMatcher = buildUriMathcer();
    private TaskDBHelper mOpenHelper;

    static final int TASK = 100;

    static UriMatcher buildUriMathcer() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = TaskContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, TaskContract.PATH_TASK, TASK);
        return matcher;
    }


    @Override
    public boolean onCreate() {
        mOpenHelper = new TaskDBHelper (getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)){
            case TASK: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        TaskContract.TaskEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " +uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TASK:
                return TaskContract.TaskEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " +uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch(match) {
            case TASK:
                long _id = db.insert(TaskContract.TaskEntry.TABLE_NAME, null, values);
                if ( _id >0)
                    returnUri = TaskContract.TaskEntry.buildTaskUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into "+ uri);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: "+ uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        if( null == selection ) selection =  "1";
        switch (match){
            case TASK:
                rowsDeleted= db.delete(
                        TaskContract.TaskEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: "+ uri);

        }

        if (rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match){
            case TASK:
                rowsUpdated= db.update(
                        TaskContract.TaskEntry.TABLE_NAME, values , selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: "+ uri);

        }

        if (rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;



    }
}
