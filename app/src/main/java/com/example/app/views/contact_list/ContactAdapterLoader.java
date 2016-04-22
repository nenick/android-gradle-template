package com.example.app.views.contact_list;

import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;

import com.example.app.views.common.cursorloader.CursorAdapterWithCursorLoader;
import com.example.app.core.QueryContactListFunction;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

@EBean
public class ContactAdapterLoader extends CursorAdapterWithCursorLoader {

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
        Log.e("sync", "sync load");
        return queryContactListFunction.apply();
    }
}
