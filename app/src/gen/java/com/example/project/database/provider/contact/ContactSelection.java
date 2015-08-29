package com.example.project.database.provider.contact;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.example.project.database.provider.base.AbstractSelection;

/**
 * Selection for the {@code contact} table.
 */
public class ContactSelection extends AbstractSelection<ContactSelection> {
    @Override
    protected Uri baseUri() {
        return ContactColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code ContactCursor} object, which is positioned before the first entry, or null.
     */
    public ContactCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new ContactCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public ContactCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public ContactCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public ContactSelection id(long... value) {
        addEquals("contact." + ContactColumns._ID, toObjectArray(value));
        return this;
    }

    public ContactSelection firstName(String... value) {
        addEquals(ContactColumns.FIRST_NAME, value);
        return this;
    }

    public ContactSelection firstNameNot(String... value) {
        addNotEquals(ContactColumns.FIRST_NAME, value);
        return this;
    }

    public ContactSelection firstNameLike(String... value) {
        addLike(ContactColumns.FIRST_NAME, value);
        return this;
    }

    public ContactSelection firstNameContains(String... value) {
        addContains(ContactColumns.FIRST_NAME, value);
        return this;
    }

    public ContactSelection firstNameStartsWith(String... value) {
        addStartsWith(ContactColumns.FIRST_NAME, value);
        return this;
    }

    public ContactSelection firstNameEndsWith(String... value) {
        addEndsWith(ContactColumns.FIRST_NAME, value);
        return this;
    }

    public ContactSelection lastName(String... value) {
        addEquals(ContactColumns.LAST_NAME, value);
        return this;
    }

    public ContactSelection lastNameNot(String... value) {
        addNotEquals(ContactColumns.LAST_NAME, value);
        return this;
    }

    public ContactSelection lastNameLike(String... value) {
        addLike(ContactColumns.LAST_NAME, value);
        return this;
    }

    public ContactSelection lastNameContains(String... value) {
        addContains(ContactColumns.LAST_NAME, value);
        return this;
    }

    public ContactSelection lastNameStartsWith(String... value) {
        addStartsWith(ContactColumns.LAST_NAME, value);
        return this;
    }

    public ContactSelection lastNameEndsWith(String... value) {
        addEndsWith(ContactColumns.LAST_NAME, value);
        return this;
    }

    public ContactSelection birthdate(Date... value) {
        addEquals(ContactColumns.BIRTHDATE, value);
        return this;
    }

    public ContactSelection birthdateNot(Date... value) {
        addNotEquals(ContactColumns.BIRTHDATE, value);
        return this;
    }

    public ContactSelection birthdate(Long... value) {
        addEquals(ContactColumns.BIRTHDATE, value);
        return this;
    }

    public ContactSelection birthdateAfter(Date value) {
        addGreaterThan(ContactColumns.BIRTHDATE, value);
        return this;
    }

    public ContactSelection birthdateAfterEq(Date value) {
        addGreaterThanOrEquals(ContactColumns.BIRTHDATE, value);
        return this;
    }

    public ContactSelection birthdateBefore(Date value) {
        addLessThan(ContactColumns.BIRTHDATE, value);
        return this;
    }

    public ContactSelection birthdateBeforeEq(Date value) {
        addLessThanOrEquals(ContactColumns.BIRTHDATE, value);
        return this;
    }
}
