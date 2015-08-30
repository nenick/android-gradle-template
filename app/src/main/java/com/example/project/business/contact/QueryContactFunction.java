package com.example.project.business.contact;

import android.support.annotation.Nullable;

import com.example.project.database.contact.ContactDb;
import com.example.project.database.provider.contact.ContactCursor;
import com.example.project.database.provider.contact.ContactModel;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.Date;

@EBean
public class QueryContactFunction {

    @Bean
    ContactDb contactDb;

    public ContactModel apply(long contactId) {
        ContactCursor contactCursor = contactDb.queryById(contactId);
        return createContactModel(contactCursor);
    }

    public ContactModel applyByUid(String contactUid) {
        ContactCursor contactCursor = contactDb.queryByUid(contactUid);
        return createContactModel(contactCursor);
    }

    private ContactModel createContactModel(ContactCursor contactCursor) {

        if(!contactCursor.moveToFirst()) {
            return null;
        }

        final String contactUid = contactCursor.getUid();
        final String firstName = contactCursor.getFirstName();
        final String lastName = contactCursor.getLastName();
        final Date birthDate = contactCursor.getBirthdate();
        contactCursor.close();

        return new ContactModel() {
            @Nullable
            @Override
            public String getUid() {
                return contactUid;
            }

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
