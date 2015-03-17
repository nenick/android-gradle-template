package com.example.databaseexample;

import android.app.LoaderManager;
import android.os.Bundle;

import com.example.model.Agenda;
import com.example.test.support.UnitTestSpecification;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DatabasePresenterTest extends UnitTestSpecification {

    public static final String TEST_AGENDA = "MyAgenda";
    public static final long TEST_ITEM_ID = 42;

    @Mock
    DatabaseActivity view;

    @Mock
    LoaderManager loaderManager;

    @Mock
    AgendaAdapter agendaAdapter;

    @Mock
    AgendaData agendaData;

    @InjectMocks
    DatabasePresenter presenter;

    ArgumentCaptor<Agenda> agendaCaptor = ArgumentCaptor.forClass(Agenda.class);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(view.getLoaderManager()).thenReturn(loaderManager);
    }

    @Test
    public void test_viewOnCreate() throws Exception {
        presenter.viewOnCreate(null);
        verify(view).setListAdapter(any(AgendaAdapter.class));
        verify(loaderManager).initLoader(eq(0), eq((Bundle) null), any(AgendaLoader.class));
    }

    @Test
    public void test_onAddAgenda() throws Exception {
        when(view.getInputAgenda()).thenReturn(TEST_AGENDA);
        presenter.onAddAgenda();
        verify(view).resetInputFields();
        verify(agendaData).insert(agendaCaptor.capture());
        Agenda agenda = agendaCaptor.getValue();
        assertThat(agenda.getName()).isEqualTo(TEST_AGENDA);
        assertThat(agenda.getDate()).matches("Added on .+");
    }

    @Test
    public void test_onDeleteAgenda() throws Exception {
        when(agendaAdapter.getItemId(0)).thenReturn(TEST_ITEM_ID);
        presenter.onDeleteAgenda();
        verify(agendaData).deleteById(TEST_ITEM_ID);
    }
}
