package com.example.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.database.table.CalendarItemTable;

import java.util.ArrayList;
import java.util.List;

public class CalendarItem {
    private long mRowId;
    private String mTitle;
    private String mDate;
    private String mTime;
    private String mLocation;
    private String mNotes;

    private Long mAgendaId;

    private ContentValues mValues = new ContentValues();

    public CalendarItem() {}

    public CalendarItem(final Cursor cursor) {
        this(cursor, false);
    }

    public CalendarItem(final Cursor cursor, boolean prependTableName) {
        String prefix = prependTableName ? CalendarItemTable.TABLE_NAME + "_" : "";
        setRowId(cursor.getLong(cursor.getColumnIndex(prefix + CalendarItemTable._ID)));
        setTitle(cursor.getString(cursor.getColumnIndex(prefix + CalendarItemTable.TITLE)));
        setDate(cursor.getString(cursor.getColumnIndex(prefix + CalendarItemTable.DATE)));
        setTime(cursor.getString(cursor.getColumnIndex(prefix + CalendarItemTable.TIME)));
        setLocation(cursor.getString(cursor.getColumnIndex(prefix + CalendarItemTable.LOCATION)));
        setNotes(cursor.getString(cursor.getColumnIndex(prefix + CalendarItemTable.NOTES)));

        setAgendaId(cursor.getLong(cursor.getColumnIndex(prefix + CalendarItemTable.AGENDA_ID)));
    }

    public ContentValues getContentValues() {
        return mValues;
    }

    public Long getRowId() {
        return mRowId;
    }

    public void setRowId(long _id) {
        mRowId = _id;
        mValues.put(CalendarItemTable._ID, _id);
    }
    public void setTitle(String title) {
        mTitle = title;
        mValues.put(CalendarItemTable.TITLE, title);
    }

    public String getTitle() {
            return mTitle;
    }


    public void setDate(String date) {
        mDate = date;
        mValues.put(CalendarItemTable.DATE, date);
    }

    public String getDate() {
            return mDate;
    }


    public void setTime(String time) {
        mTime = time;
        mValues.put(CalendarItemTable.TIME, time);
    }

    public String getTime() {
            return mTime;
    }


    public void setLocation(String location) {
        mLocation = location;
        mValues.put(CalendarItemTable.LOCATION, location);
    }

    public String getLocation() {
            return mLocation;
    }


    public void setNotes(String notes) {
        mNotes = notes;
        mValues.put(CalendarItemTable.NOTES, notes);
    }

    public String getNotes() {
            return mNotes;
    }



    public void setAgendaId(Long agendaId) {
        mAgendaId = agendaId;
        mValues.put(CalendarItemTable.AGENDA_ID, agendaId);
    }

    public Long getAgendaId() {
            return mAgendaId;
    }


    public static List<CalendarItem> listFromCursor(Cursor cursor) {
        List<CalendarItem> list = new ArrayList<CalendarItem>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                list.add(new CalendarItem(cursor));
            } while (cursor.moveToNext());
        }

        return list;
    }
}