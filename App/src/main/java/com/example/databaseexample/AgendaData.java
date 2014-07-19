package com.example.databaseexample;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;

import com.example.database.table.AgendaTable;
import com.example.model.Agenda;
import com.example.provider.AgendaProvider;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean
public class AgendaData {

    @RootContext
    Context context;

    public void deleteById(long id) {
        String where = AgendaTable._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        context.getContentResolver().delete(AgendaProvider.AGENDA_CONTENT_URI, where, selectionArgs);
    }

    public long insert(Agenda agenda) {
        Uri insert = context.getContentResolver().insert(AgendaProvider.AGENDA_CONTENT_URI, agenda.getContentValues());
        return ContentUris.parseId(insert);
    }
}
