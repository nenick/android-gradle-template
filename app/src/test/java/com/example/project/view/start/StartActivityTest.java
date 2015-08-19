package com.example.project.view.start;

import com.example.project.view.contact_list.ContactListActivityIntent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StartActivityTest {

    @Mock
    ContactListActivityIntent contactListActivityIntent;

    @InjectMocks
    StartActivity startActivity;

    @Test
    public void viewIsStarted() throws Exception {
        startActivity.onViewResume();
        thenNextActivityShouldBeStarted();
    }

    private void thenNextActivityShouldBeStarted() {
        verify(contactListActivityIntent).start();
    }
}