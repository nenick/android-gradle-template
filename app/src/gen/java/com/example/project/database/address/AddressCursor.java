package com.example.project.database.address;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.project.database.base.AbstractCursor;
import com.example.project.database.contact.*;

/**
 * Cursor wrapper for the {@code address} table.
 */
public class AddressCursor extends AbstractCursor implements AddressModel {
    public AddressCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(AddressColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code contact_id} value.
     */
    public long getContactId() {
        Long res = getLongOrNull(AddressColumns.CONTACT_ID);
        if (res == null)
            throw new NullPointerException("The value of 'contact_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code first_name} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getContactFirstName() {
        String res = getStringOrNull(ContactColumns.FIRST_NAME);
        return res;
    }

    /**
     * Get the {@code last_name} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getContactLastName() {
        String res = getStringOrNull(ContactColumns.LAST_NAME);
        return res;
    }

    /**
     * Get the {@code birthdate} value.
     * Can be {@code null}.
     */
    @Nullable
    public Date getContactBirthdate() {
        Date res = getDateOrNull(ContactColumns.BIRTHDATE);
        return res;
    }

    /**
     * Get the {@code street} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getStreet() {
        String res = getStringOrNull(AddressColumns.STREET);
        return res;
    }

    /**
     * Get the {@code number} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getNumber() {
        String res = getStringOrNull(AddressColumns.NUMBER);
        return res;
    }

    /**
     * Get the {@code city} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getCity() {
        String res = getStringOrNull(AddressColumns.CITY);
        return res;
    }

    /**
     * Get the {@code country} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getCountry() {
        String res = getStringOrNull(AddressColumns.COUNTRY);
        return res;
    }

    /**
     * Get the {@code state} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getState() {
        String res = getStringOrNull(AddressColumns.STATE);
        return res;
    }

    /**
     * Get the {@code postalcode} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getPostalcode() {
        String res = getStringOrNull(AddressColumns.POSTALCODE);
        return res;
    }
}
