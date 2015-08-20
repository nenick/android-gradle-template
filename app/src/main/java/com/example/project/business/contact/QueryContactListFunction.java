package com.example.project.business.contact;

import com.example.project.database.contact.ContactCursor;
import com.example.project.database.contact.ContactDb;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

@EBean
public class QueryContactListFunction {

    @Bean
    ContactDb contactDb;

    public ContactCursor apply() {
        return contactDb.queryAll();
    }
}
