package com.example.project.database.contact;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;

import com.example.project.database.provider.contact.ContactContentValues;
import com.example.project.database.provider.contact.ContactCursor;
import com.example.project.database.provider.contact.ContactSelection;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean
public class ContactDb {

    @RootContext
    Context context;

    public ContactContentValues insertDataContainer() {
        return new ContactContentValues();
    }

    public long insert(ContactContentValues contentValues) {
        Uri contentUri = contentValues.insert(context.getContentResolver());
        return ContentUris.parseId(contentUri);
    }

    public ContactCursor queryAll() {
        return new ContactSelection().query(context.getContentResolver());
    }

    public ContactCursor queryById(long contactId) {
        return new ContactSelection().id(contactId).query(context.getContentResolver());
    }
}
