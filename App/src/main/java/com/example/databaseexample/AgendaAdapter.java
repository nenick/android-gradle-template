package com.example.databaseexample;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.database.table.AgendaTable;

public class AgendaAdapter extends CursorAdapter {

    private static class Viewholder {
        TextView titleText;
        TextView dateText;
    }

    public AgendaAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View listItem = View.inflate(context, android.R.layout.simple_list_item_2, null);
        TextView titleText = (TextView) listItem.findViewById(android.R.id.text1);
        TextView dateText = (TextView) listItem.findViewById(android.R.id.text2);

        Viewholder holder = new Viewholder();
        holder.titleText = titleText;
        holder.dateText = dateText;

        listItem.setTag(holder);
        return listItem;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Viewholder holder = (Viewholder) view.getTag();

        String agendaName = cursor.getString(cursor.getColumnIndex(AgendaTable.NAME));
        String date = cursor.getString(cursor.getColumnIndex(AgendaTable.DATE));
        holder.titleText.setText(agendaName);
        holder.dateText.setText(date);
    }
}
