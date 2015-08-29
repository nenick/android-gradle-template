package com.example.project.views.contact_list;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ContactListFragmentTest {

    @Mock
    ContactListView view;

    @Mock
    ContactCursorAdapterWithLoader contactAdapterLoader;

    @Mock
    ContactAdapter contactAdapter;

    @InjectMocks
    ContactListFragment contactListFragment;

    @Before
    public void setup() {
        given(contactAdapterLoader.getCursorAdapter()).willReturn(contactAdapter);
    }

    @Test
    public void testOnViewCreated_shouldStartLoadingContacts() throws Exception {
        contactListFragment.onViewCreated();
        verify(view).showContacts(contactAdapter);
        verify(contactAdapterLoader).start();
    }
}