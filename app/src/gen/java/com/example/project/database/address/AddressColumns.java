package com.example.project.database.address;

import android.net.Uri;
import android.provider.BaseColumns;

import com.example.project.database.ExampleProvider;
import com.example.project.database.address.AddressColumns;
import com.example.project.database.contact.ContactColumns;

/**
 * Columns for the {@code address} table.
 */
public class AddressColumns implements BaseColumns {
    public static final String TABLE_NAME = "address";
    public static final Uri CONTENT_URI = Uri.parse(ExampleProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String CONTACT_ID = "contact_id";

    public static final String STREET = "street";

    public static final String NUMBER = "number";

    public static final String CITY = "city";

    public static final String COUNTRY = "country";

    public static final String STATE = "state";

    public static final String POSTALCODE = "postalcode";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            CONTACT_ID,
            STREET,
            NUMBER,
            CITY,
            COUNTRY,
            STATE,
            POSTALCODE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(CONTACT_ID) || c.contains("." + CONTACT_ID)) return true;
            if (c.equals(STREET) || c.contains("." + STREET)) return true;
            if (c.equals(NUMBER) || c.contains("." + NUMBER)) return true;
            if (c.equals(CITY) || c.contains("." + CITY)) return true;
            if (c.equals(COUNTRY) || c.contains("." + COUNTRY)) return true;
            if (c.equals(STATE) || c.contains("." + STATE)) return true;
            if (c.equals(POSTALCODE) || c.contains("." + POSTALCODE)) return true;
        }
        return false;
    }

    public static final String PREFIX_CONTACT = TABLE_NAME + "__" + ContactColumns.TABLE_NAME;
}
