package com.example.project.business.contact;

import android.support.annotation.Nullable;

import com.example.project.database.provider.contact.ContactCursor;
import com.example.project.database.contact.ContactDb;
import com.example.project.database.provider.contact.ContactModel;

import org.androidannotations.annotations.EBean;

import java.util.Date;

@EBean
public class QueryContactFunction {

    ContactDb contactDb;

    public ContactModel apply(long contactId) {
        ContactCursor contactCursor = contactDb.queryById(contactId);
        contactCursor.moveToFirst();
        final String firstName = contactCursor.getFirstName();
        final String lastName = contactCursor.getLastName();
        final Date birthDate = contactCursor.getBirthdate();
        contactCursor.close();

        return new ContactModel() {
            @Nullable
            @Override
            public String getFirstName() {
                return firstName;
            }

            @Nullable
            @Override
            public String getLastName() {
                return lastName;
            }

            @Nullable
            @Override
            public Date getBirthdate() {
                return birthDate;
            }
        };
    }
}
