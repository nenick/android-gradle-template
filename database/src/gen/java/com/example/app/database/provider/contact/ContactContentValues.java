package com.example.app.database.provider.contact;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.app.database.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code contact} table.
 */
public class ContactContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return ContactColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable ContactSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public ContactContentValues putUid(@Nullable String value) {
        mContentValues.put(ContactColumns.UID, value);
        return this;
    }

    public ContactContentValues putUidNull() {
        mContentValues.putNull(ContactColumns.UID);
        return this;
    }

    public ContactContentValues putFirstName(@Nullable String value) {
        mContentValues.put(ContactColumns.FIRST_NAME, value);
        return this;
    }

    public ContactContentValues putFirstNameNull() {
        mContentValues.putNull(ContactColumns.FIRST_NAME);
        return this;
    }

    public ContactContentValues putLastName(@Nullable String value) {
        mContentValues.put(ContactColumns.LAST_NAME, value);
        return this;
    }

    public ContactContentValues putLastNameNull() {
        mContentValues.putNull(ContactColumns.LAST_NAME);
        return this;
    }

    public ContactContentValues putBirthdate(@Nullable Date value) {
        mContentValues.put(ContactColumns.BIRTHDATE, value == null ? null : value.getTime());
        return this;
    }

    public ContactContentValues putBirthdateNull() {
        mContentValues.putNull(ContactColumns.BIRTHDATE);
        return this;
    }

    public ContactContentValues putBirthdate(@Nullable Long value) {
        mContentValues.put(ContactColumns.BIRTHDATE, value);
        return this;
    }
}
