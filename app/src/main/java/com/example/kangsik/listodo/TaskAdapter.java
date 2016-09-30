package com.example.kangsik.listodo;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by Kangsik on 9/29/16.
 */

public class TaskAdapter extends CursorAdapter {



    public static class ViewHolder {
        public final TextView textViewTitle;
        public final TextView textViewDescription;
        public final TextView textViewDate;
        public final TextView textViewPriority;

        public ViewHolder(View view){
            textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
            textViewDescription = (TextView) view.findViewById(R.id.textViewDescription);
            textViewDate = (TextView) view.findViewById(R.id.textViewDate);
            textViewPriority = (TextView) view.findViewById(R.id.textViewPriority);
        }

    }

    public TaskAdapter(Context context, Cursor c, int flags){
        super(context, c, flags);
    }



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int layoutId = R.layout.task_list_item;
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        String title = cursor.getString(MainActivity.COL_TITLE);
        String description = cursor.getString(MainActivity.COL_DESCRIPTION);
        String date = cursor.getString(MainActivity.COL_DATE);
        String priority = cursor.getString(MainActivity.COL_PRIORITY);
        viewHolder.textViewTitle.setText(title);
        viewHolder.textViewDescription.setText(description);
        viewHolder.textViewDate.setText(date);
        viewHolder.textViewPriority.setText(priority);
    }
}
