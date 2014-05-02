package com.example.activities;

import android.os.Bundle;

import com.example.adapters.AgendaAdapter;
import com.example.adapters.AgendaLoader;
import com.example.database.table.AgendaTable;
import com.example.model.Agenda;
import com.example.provider.AgendaProvider;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.text.DateFormat;
import java.util.Date;

@EBean
public class MainPresenter {

    @RootContext
    MainActivity view;

    AgendaAdapter mAdapter;

    public void viewOnCreate(Bundle savedInstanceState) {
        mAdapter = new AgendaAdapter(view, null, 0);
        view.setListAdapter(mAdapter);
        view.getLoaderManager().initLoader(0, null, new AgendaLoader(view, mAdapter));
    }

    public void onAddAgenda() {
        String inputAgenda = view.getInputAgenda();
        view.resetInputFields();

        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String comment = "Added on " + df.format(new Date());

        Agenda agenda = new Agenda();
        agenda.setName(inputAgenda);
        agenda.setDate(comment);

        view.getContentResolver().insert(AgendaProvider.AGENDA_CONTENT_URI, agenda.getContentValues());
    }

    public void onDeleteAenda() {
        long itemId = mAdapter.getItemId(0);
        view.getContentResolver().delete(AgendaProvider.AGENDA_CONTENT_URI, AgendaTable._ID + " = ?", new String[]{String.valueOf(itemId)});
    }
}
