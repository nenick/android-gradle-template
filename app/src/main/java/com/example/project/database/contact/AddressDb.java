package com.example.project.database.contact;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;

import com.example.project.database.address.AddressColumns;
import com.example.project.database.address.AddressContentValues;
import com.example.project.database.address.AddressCursor;
import com.example.project.database.address.AddressSelection;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.lang3.ArrayUtils;

@EBean
public class AddressDb {

    @RootContext
    Context context;

    // one join example
    private final String[] ALL_JOINED_COLUMNS = (String[]) ArrayUtils.addAll(AddressColumns.ALL_COLUMNS, ContactColumns.ALL_COLUMNS);

    public long insert(AddressContentValues contentValues) {
        Uri contentUri = contentValues.insert(context.getContentResolver());
        return ContentUris.parseId(contentUri);
    }

    public AddressCursor queryById(long addressId) {
        return new AddressSelection().id(addressId).query(context.getContentResolver(), ALL_JOINED_COLUMNS);
    }
}
