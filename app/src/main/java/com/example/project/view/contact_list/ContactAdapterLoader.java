package com.example.project.view.contact_list;

import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;

import com.example.project.business.contact.QueryContactListFunction;
import com.example.project.view.common.cursorloader.AdapterCursorLoader;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

@EBean
public class ContactAdapterLoader extends AdapterCursorLoader {

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
