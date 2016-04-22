package com.example.app.database.contact;


import com.example.app.RoboTestCase;
import com.example.app.database.ContactDb;
import com.example.app.database.ContactDb_;
import com.example.app.database.provider.contact.ContactContentValues;
import com.example.app.database.provider.contact.ContactCursor;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.assertj.android.api.Assertions.assertThat;

public class ContactDbTest extends RoboTestCase {

    ContactDb contactDb;
    long contactId;

    ContactContentValues contactContentValues = new ContactContentValues()
            .putFirstName("Max")
            .putLastName("Max")
            .putBirthdate(new Date());

    @Before
    public void setup() {
        contactDb = ContactDb_.getInstance_(context);
    }

    @Test
    public void queryAll() {
        givenContactAtDatabase(contactContentValues);
        givenContactAtDatabase(contactContentValues);
        ContactCursor contactCursor = contactDb.queryAll();
        assertThat(contactCursor).hasCount(2);
    }

    private void givenContactAtDatabase(ContactContentValues contentValues) {
        contactId = contactDb.insert(contentValues);
    }
}