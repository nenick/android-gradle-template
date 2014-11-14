package com.example.main;

import android.widget.Button;

import com.example.R;
import com.example.test.support.UnitTestSpecification;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;

import static org.mockito.Mockito.verify;

public class MainActivityTest extends UnitTestSpecification {

    @Mock
    MainPresenter presenter;

    MainActivity_ view = Robolectric.buildActivity(MainActivity_.class).create().get();

    Button database;
    Button rest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        view.presenter = presenter;
        database = (Button) view.findViewById(R.id.database);
        rest = (Button) view.findViewById(R.id.rest);
    }

    @Test
    public void shouldDelegateDatabaseButtonClick() throws Exception {
        database.performClick();
        verify(presenter).onOpenDatabaseExample();
    }

    @Test
    public void shouldDelegateRestButtonClick() throws Exception {
        rest.performClick();
        verify(presenter).onOpenRestExample();
    }
}
