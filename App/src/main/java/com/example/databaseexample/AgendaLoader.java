package com.example.databaseexample;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

import com.example.database.table.AgendaTable;
import com.example.provider.AgendaProvider;

public class AgendaLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context context;
    private AgendaAdapter adapter;

    public AgendaLoader(Context context, AgendaAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        String[] columns = {AgendaTable._ID, AgendaTable.NAME, AgendaTable.DATE};
        String sortOrder = AgendaTable.DATE + " DESC";
        return new CursorLoader(context, AgendaProvider.AGENDA_CONTENT_URI, columns, null, null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }


}
