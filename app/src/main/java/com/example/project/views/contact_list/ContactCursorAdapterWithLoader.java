package com.example.project.views.contact_list;

import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;

import com.example.project.business.contact.QueryContactListFunction;
import com.example.project.views.common.cursorloader.CursorAdapterWithCursorLoader;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

@EBean
public class ContactCursorAdapterWithLoader extends CursorAdapterWithCursorLoader {

    @Bean
    ContactAdapter contactAdapter;

    @Bean
    QueryContactListFunction queryContactListFunction;

    @Override
    public CursorAdapter getCursorAdapter() {
        return contactAdapter;
    }

    @Override
    public int getLoaderId() {
        return 1;
    }

    @Override
    public Cursor loadCursor() {
        return queryContactListFunction.apply();
    }
}
