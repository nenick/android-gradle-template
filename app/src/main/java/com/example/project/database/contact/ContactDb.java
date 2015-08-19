package com.example.project.database.contact;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean
public class ContactDb {

    @RootContext
    Context context;

    public long insert(ContactContentValues contentValues) {
        Uri contentUri = contentValues.insert(context.getContentResolver());
        return ContentUris.parseId(contentUri);
    }

    public ContactCursor queryAll() {
        return new ContactSelection().query(context.getContentResolver());
    }
}
