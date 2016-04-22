package com.example.app.testdata;

import com.example.project.database.provider.contact.ContactContentValues;
import com.example.project.database.contact.ContactDb;
import com.example.project.database.contact.ContactDb_;

import org.robolectric.RuntimeEnvironment;

import java.util.Date;

public class TestContactData {

    public static void createRandomeContacts(int count) {
        for (int i = 0; i < count; i++) {
            ContactContentValues contactContentValues = new ContactContentValues();
            contactContentValues.putFirstName("Max " + i);
            contactContentValues.putLastName("Muster");
            contactContentValues.putBirthdate(new Date());

            ContactDb contactDb = ContactDb_.getInstance_(RuntimeEnvironment.application);
            contactDb.insert(contactContentValues);
        }
    }
}
