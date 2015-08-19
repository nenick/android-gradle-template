package com.example.project.database.contact;


import android.database.sqlite.SQLiteException;

import com.example.project.RoboTestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class ContactDbInsertTest extends RoboTestCase {

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
    public void insert() {
        whenContactIsInserted(contactContentValues);
        thenInsertWasSuccessful();
    }

    @Test
    public void insert_firstname_isNullable() {
        contactContentValues.putFirstName(null);
        whenContactIsInserted(contactContentValues);
        thenInsertWasSuccessful();
    }

    @Test
    public void insert_lastname_isNullable() {
        contactContentValues.putLastName(null);
        whenContactIsInserted(contactContentValues);
        thenInsertWasSuccessful();
    }

    @Test(expected = SQLiteException.class)
    public void insert_firstOrLastname_isMandatory() {
        contactContentValues.putFirstName(null);
        contactContentValues.putLastName(null);
        whenContactIsInserted(contactContentValues);
    }

    @Test
    public void insert_birthdate_isNullable() {
        contactContentValues.putBirthdate(0l);
        whenContactIsInserted(contactContentValues);
        thenInsertWasSuccessful();
    }

    private void thenInsertWasSuccessful() {
        assertThat(contactId).isPositive();
    }

    private void whenContactIsInserted(ContactContentValues contentValues) {
        contactId = contactDb.insert(contentValues);
    }
}