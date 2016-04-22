package com.example.app.core;

import com.example.app.database.provider.contact.ContactContentValues;
import com.example.app.database.ContactDb;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.Date;

@EBean
public class CreateContactFunction {

    @Bean
    ContactDb contactDb;

    public void apply(String firstName, String lastName, Date birthDate) {
        apply("", firstName, lastName, birthDate);
    }

    public void apply(String uid, String firstName, String lastName, Date birthDate) {
        ContactContentValues contentValues = contactDb.insertDataContainer();
        contentValues.putUid(uid);
        contentValues.putFirstName(firstName);
        contentValues.putLastName(lastName);
        contentValues.putBirthdate(birthDate);
        contactDb.insert(contentValues);
    }
}
