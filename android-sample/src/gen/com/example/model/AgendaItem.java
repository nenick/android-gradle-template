package com.example.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.database.table.AgendaItemTable;

import java.util.ArrayList;
import java.util.List;

public class AgendaItem {
    private long mRowId;
    private String mTitle;
    private String mComment;

    private Long mAgendaItemGroupId;

    private ContentValues mValues = new ContentValues();

    public AgendaItem() {}

    public AgendaItem(final Cursor cursor) {
        this(cursor, false);
    }

    public AgendaItem(final Cursor cursor, boolean prependTableName) {
        String prefix = prependTableName ? AgendaItemTable.TABLE_NAME + "_" : "";
        setRowId(cursor.getLong(cursor.getColumnIndex(prefix + AgendaItemTable._ID)));
        setTitle(cursor.getString(cursor.getColumnIndex(prefix + AgendaItemTable.TITLE)));
        setComment(cursor.getString(cursor.getColumnIndex(prefix + AgendaItemTable.COMMENT)));

        setAgendaItemGroupId(cursor.getLong(cursor.getColumnIndex(prefix + AgendaItemTable.AGENDA_ITEM_GROUP_ID)));
    }

    public ContentValues getContentValues() {
        return mValues;
    }

    public Long getRowId() {
        return mRowId;
    }

    public void setRowId(long _id) {
        mRowId = _id;
        mValues.put(AgendaItemTable._ID, _id);
    }
    public void setTitle(String title) {
        mTitle = title;
        mValues.put(AgendaItemTable.TITLE, title);
    }

    public String getTitle() {
            return mTitle;
    }


    public void setComment(String comment) {
        mComment = comment;
        mValues.put(AgendaItemTable.COMMENT, comment);
    }

    public String getComment() {
            return mComment;
    }



    public void setAgendaItemGroupId(Long agendaItemGroupId) {
        mAgendaItemGroupId = agendaItemGroupId;
        mValues.put(AgendaItemTable.AGENDA_ITEM_GROUP_ID, agendaItemGroupId);
    }

    public Long getAgendaItemGroupId() {
            return mAgendaItemGroupId;
    }


    public static List<AgendaItem> listFromCursor(Cursor cursor) {
        List<AgendaItem> list = new ArrayList<AgendaItem>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                list.add(new AgendaItem(cursor));
            } while (cursor.moveToNext());
        }

        return list;
    }
}