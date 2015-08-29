package com.example.project.database.provider.contact;

import android.net.Uri;
import android.provider.BaseColumns;

import com.example.project.database.provider.ExampleProvider;
import com.example.project.database.provider.address.AddressColumns;
import com.example.project.database.provider.contact.ContactColumns;

/**
 * Columns for the {@code contact} table.
 */
public class ContactColumns implements BaseColumns {
    public static final String TABLE_NAME = "contact";
    public static final Uri CONTENT_URI = Uri.parse(ExampleProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String FIRST_NAME = "first_name";

    public static final String LAST_NAME = "last_name";

    public static final String BIRTHDATE = "birthdate";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            FIRST_NAME,
            LAST_NAME,
            BIRTHDATE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(FIRST_NAME) || c.contains("." + FIRST_NAME)) return true;
            if (c.equals(LAST_NAME) || c.contains("." + LAST_NAME)) return true;
            if (c.equals(BIRTHDATE) || c.contains("." + BIRTHDATE)) return true;
        }
        return false;
    }

}
