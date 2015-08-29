package com.example.project.business.contact;

import com.example.project.database.provider.contact.ContactContentValues;
import com.example.project.database.contact.ContactDb;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.Date;

@EBean
public class CreateContactFunction {

    @Bean
    ContactDb contactDb;

    public void apply(String firstName, String lastName, Date birhtDate) {
        ContactContentValues contentValues = contactDb.insertDataContainer();
        contentValues.putFirstName(firstName);
        contentValues.putLastName(lastName);
        contentValues.putBirthdate(birhtDate);
        contactDb.insert(contentValues);
    }
}
