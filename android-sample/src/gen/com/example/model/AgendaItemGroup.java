package com.example.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.database.table.AgendaItemGroupTable;

import java.util.ArrayList;
import java.util.List;

public class AgendaItemGroup {
    private long mRowId;
    private String mTitle;
    private String mDescription;


    private ContentValues mValues = new ContentValues();

    public AgendaItemGroup() {}

    public AgendaItemGroup(final Cursor cursor) {
        this(cursor, false);
    }

    public AgendaItemGroup(final Cursor cursor, boolean prependTableName) {
        String prefix = prependTableName ? AgendaItemGroupTable.TABLE_NAME + "_" : "";
        setRowId(cursor.getLong(cursor.getColumnIndex(prefix + AgendaItemGroupTable._ID)));
        setTitle(cursor.getString(cursor.getColumnIndex(prefix + AgendaItemGroupTable.TITLE)));
        setDescription(cursor.getString(cursor.getColumnIndex(prefix + AgendaItemGroupTable.DESCRIPTION)));

    }

    public ContentValues getContentValues() {
        return mValues;
    }

    public Long getRowId() {
        return mRowId;
    }

    public void setRowId(long _id) {
        mRowId = _id;
        mValues.put(AgendaItemGroupTable._ID, _id);
    }
    public void setTitle(String title) {
        mTitle = title;
        mValues.put(AgendaItemGroupTable.TITLE, title);
    }

    public String getTitle() {
            return mTitle;
    }


    public void setDescription(String description) {
        mDescription = description;
        mValues.put(AgendaItemGroupTable.DESCRIPTION, description);
    }

    public String getDescription() {
            return mDescription;
    }




    public static List<AgendaItemGroup> listFromCursor(Cursor cursor) {
        List<AgendaItemGroup> list = new ArrayList<AgendaItemGroup>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                list.add(new AgendaItemGroup(cursor));
            } while (cursor.moveToNext());
        }

        return list;
    }
}