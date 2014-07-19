package com.example.database;

import com.example.database.table.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AgendaDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "agenda_database.db";
    private static final int DATABASE_VERSION = 1;

    public AgendaDatabase(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public final void onCreate(final SQLiteDatabase db) {
        db.execSQL(AgendaTable.SQL_CREATE);

        db.execSQL(CalendarItemTable.SQL_CREATE);

        db.execSQL(AgendaItemGroupTable.SQL_CREATE);

        db.execSQL(AgendaItemTable.SQL_CREATE);

    }

    @Override
    public final void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        upgrade(db, oldVersion, newVersion);
    }

    private final void dropTablesAndCreate(final SQLiteDatabase db) {
        db.execSQL(AgendaTable.SQL_DROP);

        db.execSQL(CalendarItemTable.SQL_DROP);

        db.execSQL(AgendaItemGroupTable.SQL_DROP);

        db.execSQL(AgendaItemTable.SQL_DROP);


        onCreate(db);
    }

    private void upgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        dropTablesAndCreate(db);
    }
}