package com.example.database.table;

public interface AgendaItemGroupTable {
    String TABLE_NAME = "agendaitemgroup";

    String _ID = "_id";

    String TITLE = "title";
    String DESCRIPTION = "description";

    String[] ALL_COLUMNS = new String[] {_ID, TITLE, DESCRIPTION};

    String SQL_CREATE = "CREATE TABLE agendaitemgroup ( _id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT )";

    String SQL_INSERT = "INSERT INTO agendaitemgroup ( title, description ) VALUES ( ?, ? )";

    String SQL_DROP = "DROP TABLE IF EXISTS agendaitemgroup";

    String WHERE_ID_EQUALS = _ID + "=?";

}