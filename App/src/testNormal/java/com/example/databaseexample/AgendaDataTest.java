package com.example.databaseexample;

import android.database.Cursor;

import com.example.model.Agenda;
import com.example.provider.AgendaProvider;
import com.example.test.support.DatabaseSpecification;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class AgendaDataTest extends DatabaseSpecification {

    AgendaData agendaData = new AgendaData();

    @Before
    public void setUp() {
        agendaData.context = context;
    }

    @Test
    public void test_insert() {
        Agenda agenda = new Agenda();
        agenda.setName("MyAgenda");
        agenda.setDate("current date");
        long rowId = agendaData.insert(agenda);

        Cursor query = context.getContentResolver().query(AgendaProvider.AGENDA_CONTENT_URI, null, null, null, null);
        assertThat(query.getCount()).isEqualTo(1);
        query.moveToNext();
        Agenda dbAgenda = new Agenda(query);
        assertThat(dbAgenda.getRowId()).isPositive();
        assertThat(dbAgenda.getRowId()).isEqualTo(rowId);
        assertThat(dbAgenda.getName()).isEqualTo(agenda.getName());
        assertThat(dbAgenda.getDate()).isEqualTo(agenda.getDate());
    }

    @Test
    public void test_deleteById() {
        Agenda agenda = new Agenda();
        agenda.setName("MyAgenda");
        agenda.setDate("current date");
        long rowId = agendaData.insert(agenda);

        agendaData.deleteById(rowId);

        Cursor query = context.getContentResolver().query(AgendaProvider.AGENDA_CONTENT_URI, null, null, null, null);
        assertThat(query.getCount()).isZero();
    }
}
