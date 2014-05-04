package com.example.activities;

import android.os.Bundle;

import com.example.adapters.AgendaAdapter;
import com.example.adapters.AgendaLoader;
import com.example.managers.AgendaManager;
import com.example.model.Agenda;
import com.example.rest.EventClient;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.rest.RestService;

import java.text.DateFormat;
import java.util.Date;

@EBean
public class MainPresenter {

    @RootContext
    MainActivity view;

    @RestService
    EventClient eventClient;

    @Bean
    AgendaManager agendaManager;

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

        agendaManager.insert(agenda);
    }

    public void onDeleteAgenda() {
        long itemId = mAdapter.getItemId(0);
        agendaManager.deleteById(itemId);
    }
}
