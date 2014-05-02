package com.example.activities;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Bundle;

import com.example.adapters.AgendaAdapter;
import com.example.adapters.AgendaLoader;
import com.example.database.table.AgendaTable;
import com.example.provider.AgendaProvider;
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

public class MainPresenterTest extends UnitTestSpecification {

    public static final String TEST_AGENDA = "MyAgenda";
    public static final long TEST_ITEM_ID = 42;

    @Mock
    MainActivity view;

    @Mock
    LoaderManager loaderManager;

    @Mock
    AgendaAdapter agendaAdapter;

    @Mock
    ContentResolver contentResolver;

    @InjectMocks
    MainPresenter presenter;

    ArgumentCaptor<ContentValues> contentValueCaptor = ArgumentCaptor.forClass(ContentValues.class);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(view.getLoaderManager()).thenReturn(loaderManager);
        when(view.getContentResolver()).thenReturn(contentResolver);
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
        verify(contentResolver).insert(eq(AgendaProvider.AGENDA_CONTENT_URI), contentValueCaptor.capture());
        ContentValues contentValues = contentValueCaptor.getValue();
        assertThat(contentValues.getAsString(AgendaTable.NAME)).isEqualTo(TEST_AGENDA);
        assertThat(contentValues.getAsString(AgendaTable.DATE)).matches("Added on .+");
    }

    @Test
    public void test_onDeleteAgenda() throws Exception {
        when(agendaAdapter.getItemId(0)).thenReturn(TEST_ITEM_ID);
        presenter.onDeleteAenda();
        verify(contentResolver).delete(AgendaProvider.AGENDA_CONTENT_URI, AgendaTable._ID + " = ?", new String[]{String.valueOf(TEST_ITEM_ID)});
    }
}
