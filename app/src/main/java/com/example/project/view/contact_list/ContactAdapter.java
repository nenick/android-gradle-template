package com.example.project.view.contact_list;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project.database.contact.ContactCursor;

import org.androidannotations.annotations.EBean;

@EBean
public class ContactAdapter extends CursorAdapter {

    public ContactAdapter(Context context) {
        super(context, null, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ContactCursor contacts = (ContactCursor) cursor;
        ((TextView) view.findViewById(android.R.id.text1)).setText(contacts.getFirstName() + " " + contacts.getLastName());
        if(contacts.getBirthdate() != null) {
            ((TextView) view.findViewById(android.R.id.text2)).setText(contacts.getBirthdate().toString());
        }
    }
}
