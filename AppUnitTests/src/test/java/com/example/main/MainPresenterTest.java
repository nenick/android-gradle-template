package com.example.main;

import android.content.Intent;

import com.example.databaseexample.DatabaseActivity_;
import com.example.restexample.RestActivity_;
import com.example.test.support.UnitTestSpecification;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class MainPresenterTest extends UnitTestSpecification {

    @Mock
    MainActivity view;

    @InjectMocks
    MainPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    ArgumentCaptor<Intent> intentCaptor = ArgumentCaptor.forClass(Intent.class);

    @Test
    public void shouldOpenDatabaseActivity() throws Exception {
        presenter.onOpenDatabaseExample();
        verify(view).startActivity(intentCaptor.capture());
        Intent startedIntent = intentCaptor.getValue();
        assertThat(startedIntent.getComponent().getClassName()).isEqualTo(DatabaseActivity_.class.getName());
    }

    @Test
    public void shouldOpenRestActivity() throws Exception {
        presenter.onOpenRestExample();
        verify(view).startActivity(intentCaptor.capture());
        Intent startedIntent = intentCaptor.getValue();
        assertThat(startedIntent.getComponent().getClassName()).isEqualTo(RestActivity_.class.getName());
    }
}
