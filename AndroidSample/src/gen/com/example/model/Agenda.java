package com.example.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.database.table.AgendaTable;

import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private long mRowId;
    private String mName;
    private String mDate;
    private String mPersonConducting;


    private ContentValues mValues = new ContentValues();

    public Agenda() {}

    public Agenda(final Cursor cursor) {
        this(cursor, false);
    }

    public Agenda(final Cursor cursor, boolean prependTableName) {
        String prefix = prependTableName ? AgendaTable.TABLE_NAME + "_" : "";
        setRowId(cursor.getLong(cursor.getColumnIndex(prefix + AgendaTable._ID)));
        setName(cursor.getString(cursor.getColumnIndex(prefix + AgendaTable.NAME)));
        setDate(cursor.getString(cursor.getColumnIndex(prefix + AgendaTable.DATE)));
        setPersonConducting(cursor.getString(cursor.getColumnIndex(prefix + AgendaTable.PERSON_CONDUCTING)));

    }

    public ContentValues getContentValues() {
        return mValues;
    }

    public Long getRowId() {
        return mRowId;
    }

    public void setRowId(long _id) {
        mRowId = _id;
        mValues.put(AgendaTable._ID, _id);
    }
    public void setName(String name) {
        mName = name;
        mValues.put(AgendaTable.NAME, name);
    }

    public String getName() {
            return mName;
    }


    public void setDate(String date) {
        mDate = date;
        mValues.put(AgendaTable.DATE, date);
    }

    public String getDate() {
            return mDate;
    }


    public void setPersonConducting(String person_conducting) {
        mPersonConducting = person_conducting;
        mValues.put(AgendaTable.PERSON_CONDUCTING, person_conducting);
    }

    public String getPersonConducting() {
            return mPersonConducting;
    }




    public static List<Agenda> listFromCursor(Cursor cursor) {
        List<Agenda> list = new ArrayList<Agenda>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                list.add(new Agenda(cursor));
            } while (cursor.moveToNext());
        }

        return list;
    }
}