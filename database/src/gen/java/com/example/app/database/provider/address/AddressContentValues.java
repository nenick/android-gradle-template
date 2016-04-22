package com.example.app.database.provider.address;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.app.database.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code address} table.
 */
public class AddressContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return AddressColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable AddressSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public AddressContentValues putContactId(long value) {
        mContentValues.put(AddressColumns.CONTACT_ID, value);
        return this;
    }


    public AddressContentValues putStreet(@Nullable String value) {
        mContentValues.put(AddressColumns.STREET, value);
        return this;
    }

    public AddressContentValues putStreetNull() {
        mContentValues.putNull(AddressColumns.STREET);
        return this;
    }

    public AddressContentValues putNumber(@Nullable String value) {
        mContentValues.put(AddressColumns.NUMBER, value);
        return this;
    }

    public AddressContentValues putNumberNull() {
        mContentValues.putNull(AddressColumns.NUMBER);
        return this;
    }

    public AddressContentValues putCity(@Nullable String value) {
        mContentValues.put(AddressColumns.CITY, value);
        return this;
    }

    public AddressContentValues putCityNull() {
        mContentValues.putNull(AddressColumns.CITY);
        return this;
    }

    public AddressContentValues putCountry(@Nullable String value) {
        mContentValues.put(AddressColumns.COUNTRY, value);
        return this;
    }

    public AddressContentValues putCountryNull() {
        mContentValues.putNull(AddressColumns.COUNTRY);
        return this;
    }

    public AddressContentValues putState(@Nullable String value) {
        mContentValues.put(AddressColumns.STATE, value);
        return this;
    }

    public AddressContentValues putStateNull() {
        mContentValues.putNull(AddressColumns.STATE);
        return this;
    }

    public AddressContentValues putPostalcode(@Nullable String value) {
        mContentValues.put(AddressColumns.POSTALCODE, value);
        return this;
    }

    public AddressContentValues putPostalcodeNull() {
        mContentValues.putNull(AddressColumns.POSTALCODE);
        return this;
    }
}
