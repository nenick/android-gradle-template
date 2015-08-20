package com.example.project.business.contact;

import com.example.project.database.contact.ContactCursor;
import com.example.project.database.contact.ContactDb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class QueryContactListFunctionTest {

    @InjectMocks
    QueryContactListFunction queryContactListFunction;

    @Mock
    ContactDb contactDb;

    @Mock
    ContactCursor allContactCursor;

    @Test
    public void shouldLoadAllContacts() {
        given(contactDb.queryAll()).willReturn(allContactCursor);
        ContactCursor result = queryContactListFunction.apply();
        assertThat(result).isSameAs(allContactCursor);
    }
}