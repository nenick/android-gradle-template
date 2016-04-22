package com.example.app.database.provider.contact;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.Nullable;

import com.example.app.database.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code contact} table.
 */
public class ContactCursor extends AbstractCursor implements ContactModel {
    public ContactCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(ContactColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code uid} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getUid() {
        String res = getStringOrNull(ContactColumns.UID);
        return res;
    }

    /**
     * Get the {@code first_name} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getFirstName() {
        String res = getStringOrNull(ContactColumns.FIRST_NAME);
        return res;
    }

    /**
     * Get the {@code last_name} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getLastName() {
        String res = getStringOrNull(ContactColumns.LAST_NAME);
        return res;
    }

    /**
     * Get the {@code birthdate} value.
     * Can be {@code null}.
     */
    @Nullable
    public Date getBirthdate() {
        Date res = getDateOrNull(ContactColumns.BIRTHDATE);
        return res;
    }
}
