package com.example.database.table;

public interface AgendaItemTable {
    String TABLE_NAME = "agendaitem";

    String _ID = "_id";

    String TITLE = "title";
    String COMMENT = "comment";
    String AGENDA_ITEM_GROUP_ID = "agenda_item_group_id";

    String[] ALL_COLUMNS = new String[] {_ID, TITLE, COMMENT, AGENDA_ITEM_GROUP_ID};

    String SQL_CREATE = "CREATE TABLE agendaitem ( _id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, comment TEXT, agenda_item_group_id INTEGER )";

    String SQL_INSERT = "INSERT INTO agendaitem ( title, comment, agenda_item_group_id ) VALUES ( ?, ?, ? )";

    String SQL_DROP = "DROP TABLE IF EXISTS agendaitem";

    String WHERE_ID_EQUALS = _ID + "=?";

    String WHERE_AGENDA_ITEM_GROUP_ID_EQUALS = AGENDA_ITEM_GROUP_ID + "=?";
}