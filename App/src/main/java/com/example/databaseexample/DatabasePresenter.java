package com.example.databaseexample;

import android.os.Bundle;

import com.example.model.Agenda;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.text.DateFormat;
import java.util.Date;

@EBean
public class DatabasePresenter {

    @RootContext
    DatabaseActivity view;

    @Bean
    AgendaData agendaData;

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

        agendaData.insert(agenda);
    }

    public void onDeleteAgenda() {
        long itemId = mAdapter.getItemId(0);
        agendaData.deleteById(itemId);
    }
}
