package com.example.database.table;

public interface CalendarItemTable {
    String TABLE_NAME = "calendaritem";

    String _ID = "_id";

    String TITLE = "title";
    String DATE = "date";
    String TIME = "time";
    String LOCATION = "location";
    String NOTES = "notes";
    String AGENDA_ID = "agenda_id";

    String[] ALL_COLUMNS = new String[] {_ID, TITLE, DATE, TIME, LOCATION, NOTES, AGENDA_ID};

    String SQL_CREATE = "CREATE TABLE calendaritem ( _id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, date TEXT, time TEXT, location TEXT, notes TEXT, agenda_id INTEGER )";

    String SQL_INSERT = "INSERT INTO calendaritem ( title, date, time, location, notes, agenda_id ) VALUES ( ?, ?, ?, ?, ?, ? )";

    String SQL_DROP = "DROP TABLE IF EXISTS calendaritem";

    String WHERE_ID_EQUALS = _ID + "=?";

    String WHERE_AGENDA_ID_EQUALS = AGENDA_ID + "=?";
}