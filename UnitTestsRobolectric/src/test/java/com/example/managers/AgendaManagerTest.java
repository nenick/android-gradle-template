package com.example.managers;

import android.database.Cursor;

import com.example.model.Agenda;
import com.example.provider.AgendaProvider;
import com.example.test.support.UnitTestSpecification;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;

import static org.fest.assertions.api.Assertions.assertThat;

public class AgendaManagerTest extends UnitTestSpecification {

    AgendaManager agendaManager = new AgendaManager();

    @Before
    public void setUp() {
        agendaManager.context = Robolectric.application;
    }

    @Test
    public void test_insert() {
        Agenda agenda = new Agenda();
        agenda.setName("MyAgenda");
        agenda.setDate("current date");
        long rowId = agendaManager.insert(agenda);

        Cursor query = Robolectric.application.getContentResolver().query(AgendaProvider.AGENDA_CONTENT_URI, null, null, null, null);
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
        long rowId = agendaManager.insert(agenda);

        agendaManager.deleteById(rowId);

        Cursor query = Robolectric.application.getContentResolver().query(AgendaProvider.AGENDA_CONTENT_URI, null, null, null, null);
        assertThat(query.getCount()).isZero();
    }
}
