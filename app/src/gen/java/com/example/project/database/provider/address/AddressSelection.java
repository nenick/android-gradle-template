package com.example.project.database.provider.address;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.example.project.database.provider.base.AbstractSelection;
import com.example.project.database.provider.contact.*;

/**
 * Selection for the {@code address} table.
 */
public class AddressSelection extends AbstractSelection<AddressSelection> {
    @Override
    protected Uri baseUri() {
        return AddressColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code AddressCursor} object, which is positioned before the first entry, or null.
     */
    public AddressCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new AddressCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public AddressCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public AddressCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public AddressSelection id(long... value) {
        addEquals("address." + AddressColumns._ID, toObjectArray(value));
        return this;
    }

    public AddressSelection contactId(long... value) {
        addEquals(AddressColumns.CONTACT_ID, toObjectArray(value));
        return this;
    }

    public AddressSelection contactIdNot(long... value) {
        addNotEquals(AddressColumns.CONTACT_ID, toObjectArray(value));
        return this;
    }

    public AddressSelection contactIdGt(long value) {
        addGreaterThan(AddressColumns.CONTACT_ID, value);
        return this;
    }

    public AddressSelection contactIdGtEq(long value) {
        addGreaterThanOrEquals(AddressColumns.CONTACT_ID, value);
        return this;
    }

    public AddressSelection contactIdLt(long value) {
        addLessThan(AddressColumns.CONTACT_ID, value);
        return this;
    }

    public AddressSelection contactIdLtEq(long value) {
        addLessThanOrEquals(AddressColumns.CONTACT_ID, value);
        return this;
    }

    public AddressSelection contactFirstName(String... value) {
        addEquals(ContactColumns.FIRST_NAME, value);
        return this;
    }

    public AddressSelection contactFirstNameNot(String... value) {
        addNotEquals(ContactColumns.FIRST_NAME, value);
        return this;
    }

    public AddressSelection contactFirstNameLike(String... value) {
        addLike(ContactColumns.FIRST_NAME, value);
        return this;
    }

    public AddressSelection contactFirstNameContains(String... value) {
        addContains(ContactColumns.FIRST_NAME, value);
        return this;
    }

    public AddressSelection contactFirstNameStartsWith(String... value) {
        addStartsWith(ContactColumns.FIRST_NAME, value);
        return this;
    }

    public AddressSelection contactFirstNameEndsWith(String... value) {
        addEndsWith(ContactColumns.FIRST_NAME, value);
        return this;
    }

    public AddressSelection contactLastName(String... value) {
        addEquals(ContactColumns.LAST_NAME, value);
        return this;
    }

    public AddressSelection contactLastNameNot(String... value) {
        addNotEquals(ContactColumns.LAST_NAME, value);
        return this;
    }

    public AddressSelection contactLastNameLike(String... value) {
        addLike(ContactColumns.LAST_NAME, value);
        return this;
    }

    public AddressSelection contactLastNameContains(String... value) {
        addContains(ContactColumns.LAST_NAME, value);
        return this;
    }

    public AddressSelection contactLastNameStartsWith(String... value) {
        addStartsWith(ContactColumns.LAST_NAME, value);
        return this;
    }

    public AddressSelection contactLastNameEndsWith(String... value) {
        addEndsWith(ContactColumns.LAST_NAME, value);
        return this;
    }

    public AddressSelection contactBirthdate(Date... value) {
        addEquals(ContactColumns.BIRTHDATE, value);
        return this;
    }

    public AddressSelection contactBirthdateNot(Date... value) {
        addNotEquals(ContactColumns.BIRTHDATE, value);
        return this;
    }

    public AddressSelection contactBirthdate(Long... value) {
        addEquals(ContactColumns.BIRTHDATE, value);
        return this;
    }

    public AddressSelection contactBirthdateAfter(Date value) {
        addGreaterThan(ContactColumns.BIRTHDATE, value);
        return this;
    }

    public AddressSelection contactBirthdateAfterEq(Date value) {
        addGreaterThanOrEquals(ContactColumns.BIRTHDATE, value);
        return this;
    }

    public AddressSelection contactBirthdateBefore(Date value) {
        addLessThan(ContactColumns.BIRTHDATE, value);
        return this;
    }

    public AddressSelection contactBirthdateBeforeEq(Date value) {
        addLessThanOrEquals(ContactColumns.BIRTHDATE, value);
        return this;
    }

    public AddressSelection street(String... value) {
        addEquals(AddressColumns.STREET, value);
        return this;
    }

    public AddressSelection streetNot(String... value) {
        addNotEquals(AddressColumns.STREET, value);
        return this;
    }

    public AddressSelection streetLike(String... value) {
        addLike(AddressColumns.STREET, value);
        return this;
    }

    public AddressSelection streetContains(String... value) {
        addContains(AddressColumns.STREET, value);
        return this;
    }

    public AddressSelection streetStartsWith(String... value) {
        addStartsWith(AddressColumns.STREET, value);
        return this;
    }

    public AddressSelection streetEndsWith(String... value) {
        addEndsWith(AddressColumns.STREET, value);
        return this;
    }

    public AddressSelection number(String... value) {
        addEquals(AddressColumns.NUMBER, value);
        return this;
    }

    public AddressSelection numberNot(String... value) {
        addNotEquals(AddressColumns.NUMBER, value);
        return this;
    }

    public AddressSelection numberLike(String... value) {
        addLike(AddressColumns.NUMBER, value);
        return this;
    }

    public AddressSelection numberContains(String... value) {
        addContains(AddressColumns.NUMBER, value);
        return this;
    }

    public AddressSelection numberStartsWith(String... value) {
        addStartsWith(AddressColumns.NUMBER, value);
        return this;
    }

    public AddressSelection numberEndsWith(String... value) {
        addEndsWith(AddressColumns.NUMBER, value);
        return this;
    }

    public AddressSelection city(String... value) {
        addEquals(AddressColumns.CITY, value);
        return this;
    }

    public AddressSelection cityNot(String... value) {
        addNotEquals(AddressColumns.CITY, value);
        return this;
    }

    public AddressSelection cityLike(String... value) {
        addLike(AddressColumns.CITY, value);
        return this;
    }

    public AddressSelection cityContains(String... value) {
        addContains(AddressColumns.CITY, value);
        return this;
    }

    public AddressSelection cityStartsWith(String... value) {
        addStartsWith(AddressColumns.CITY, value);
        return this;
    }

    public AddressSelection cityEndsWith(String... value) {
        addEndsWith(AddressColumns.CITY, value);
        return this;
    }

    public AddressSelection country(String... value) {
        addEquals(AddressColumns.COUNTRY, value);
        return this;
    }

    public AddressSelection countryNot(String... value) {
        addNotEquals(AddressColumns.COUNTRY, value);
        return this;
    }

    public AddressSelection countryLike(String... value) {
        addLike(AddressColumns.COUNTRY, value);
        return this;
    }

    public AddressSelection countryContains(String... value) {
        addContains(AddressColumns.COUNTRY, value);
        return this;
    }

    public AddressSelection countryStartsWith(String... value) {
        addStartsWith(AddressColumns.COUNTRY, value);
        return this;
    }

    public AddressSelection countryEndsWith(String... value) {
        addEndsWith(AddressColumns.COUNTRY, value);
        return this;
    }

    public AddressSelection state(String... value) {
        addEquals(AddressColumns.STATE, value);
        return this;
    }

    public AddressSelection stateNot(String... value) {
        addNotEquals(AddressColumns.STATE, value);
        return this;
    }

    public AddressSelection stateLike(String... value) {
        addLike(AddressColumns.STATE, value);
        return this;
    }

    public AddressSelection stateContains(String... value) {
        addContains(AddressColumns.STATE, value);
        return this;
    }

    public AddressSelection stateStartsWith(String... value) {
        addStartsWith(AddressColumns.STATE, value);
        return this;
    }

    public AddressSelection stateEndsWith(String... value) {
        addEndsWith(AddressColumns.STATE, value);
        return this;
    }

    public AddressSelection postalcode(String... value) {
        addEquals(AddressColumns.POSTALCODE, value);
        return this;
    }

    public AddressSelection postalcodeNot(String... value) {
        addNotEquals(AddressColumns.POSTALCODE, value);
        return this;
    }

    public AddressSelection postalcodeLike(String... value) {
        addLike(AddressColumns.POSTALCODE, value);
        return this;
    }

    public AddressSelection postalcodeContains(String... value) {
        addContains(AddressColumns.POSTALCODE, value);
        return this;
    }

    public AddressSelection postalcodeStartsWith(String... value) {
        addStartsWith(AddressColumns.POSTALCODE, value);
        return this;
    }

    public AddressSelection postalcodeEndsWith(String... value) {
        addEndsWith(AddressColumns.POSTALCODE, value);
        return this;
    }
}
