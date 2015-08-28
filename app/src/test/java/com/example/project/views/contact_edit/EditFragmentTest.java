package com.example.project.views.contact_edit;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
@Ignore
public class EditFragmentTest {

    @InjectMocks
    EditFragment editFragment;

    @Mock
    EditView editView;

    @Mock
    EditConfirmedListener editConfirmedListener;

    @Test
    public void testOnShowContact_shouldLoadContactDetails() throws Exception {
        editFragment.onShowContact(42);
    }

    @Test
    public void testOnClickConfirm_shouldSaveContact() throws Exception {

    }
}