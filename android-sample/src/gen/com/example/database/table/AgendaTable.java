package com.example.database.table;

public interface AgendaTable {
    String TABLE_NAME = "agenda";

    String _ID = "_id";

    String NAME = "name";
    String DATE = "date";
    String PERSON_CONDUCTING = "person_conducting";

    String[] ALL_COLUMNS = new String[] {_ID, NAME, DATE, PERSON_CONDUCTING};

    String SQL_CREATE = "CREATE TABLE agenda ( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, date TEXT, person_conducting TEXT )";

    String SQL_INSERT = "INSERT INTO agenda ( name, date, person_conducting ) VALUES ( ?, ?, ? )";

    String SQL_DROP = "DROP TABLE IF EXISTS agenda";

    String WHERE_ID_EQUALS = _ID + "=?";

}