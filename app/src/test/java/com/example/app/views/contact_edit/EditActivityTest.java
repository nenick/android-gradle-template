package com.example.app.views.contact_edit;

import com.example.app.views.common.testwrapper.ViewFinisher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EditActivityTest {

    @InjectMocks
    EditActivity editActivity;

    @Mock
    EditFragment editFragment;

    @Mock
    ViewFinisher viewFinisher;

    long CONTACT_ID = 42;

    @Test
    public void testOnViewCreated_shouldInitDetailFragment() throws Exception {
        editActivity.contactId = CONTACT_ID;
        editActivity.onViewCreated();
        verify(editFragment).setEditConfirmedListener(editActivity);
        verify(editFragment).onShowContact(CONTACT_ID);
    }

    @Test
    public void testOnEditConfirmed_shouldFinishTheActivity() throws Exception {
        editActivity.onEditConfirmed();
        verify(viewFinisher).finish();
    }
}