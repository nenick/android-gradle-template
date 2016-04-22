package com.example.app.core;

import com.example.app.database.provider.contact.ContactCursor;
import com.example.app.database.ContactDb;

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
