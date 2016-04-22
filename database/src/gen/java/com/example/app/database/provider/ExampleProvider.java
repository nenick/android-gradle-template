package com.example.app.database.provider;

import java.util.Arrays;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.example.app.database.provider.address.AddressColumns;
import com.example.app.database.provider.base.BaseContentProvider;
import com.example.app.database.BuildConfig;
import com.example.app.database.provider.contact.ContactColumns;

public class ExampleProvider extends BaseContentProvider {
    private static final String TAG = ExampleProvider.class.getSimpleName();

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "com.example.project";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    private static final int URI_TYPE_ADDRESS = 0;
    private static final int URI_TYPE_ADDRESS_ID = 1;

    private static final int URI_TYPE_CONTACT = 2;
    private static final int URI_TYPE_CONTACT_ID = 3;



    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, AddressColumns.TABLE_NAME, URI_TYPE_ADDRESS);
        URI_MATCHER.addURI(AUTHORITY, AddressColumns.TABLE_NAME + "/#", URI_TYPE_ADDRESS_ID);
        URI_MATCHER.addURI(AUTHORITY, ContactColumns.TABLE_NAME, URI_TYPE_CONTACT);
        URI_MATCHER.addURI(AUTHORITY, ContactColumns.TABLE_NAME + "/#", URI_TYPE_CONTACT_ID);
    }

    @Override
    protected SQLiteOpenHelper createSqLiteOpenHelper() {
        return ExampleSQLiteOpenHelper.getInstance(getContext());
    }

    @Override
    protected boolean hasDebug() {
        return DEBUG;
    }

    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_ADDRESS:
                return TYPE_CURSOR_DIR + AddressColumns.TABLE_NAME;
            case URI_TYPE_ADDRESS_ID:
                return TYPE_CURSOR_ITEM + AddressColumns.TABLE_NAME;

            case URI_TYPE_CONTACT:
                return TYPE_CURSOR_DIR + ContactColumns.TABLE_NAME;
            case URI_TYPE_CONTACT_ID:
                return TYPE_CURSOR_ITEM + ContactColumns.TABLE_NAME;

        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        return super.insert(uri, values);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        return super.bulkInsert(uri, values);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.update(uri, values, selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.delete(uri, selection, selectionArgs);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (DEBUG)
            Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
                    + " groupBy=" + uri.getQueryParameter(QUERY_GROUP_BY) + " having=" + uri.getQueryParameter(QUERY_HAVING) + " limit=" + uri.getQueryParameter(QUERY_LIMIT));
        return super.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    protected QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_ADDRESS:
            case URI_TYPE_ADDRESS_ID:
                res.table = AddressColumns.TABLE_NAME;
                res.idColumn = AddressColumns._ID;
                res.tablesWithJoins = AddressColumns.TABLE_NAME;
                if (ContactColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + ContactColumns.TABLE_NAME + " AS " + AddressColumns.PREFIX_CONTACT + " ON " + AddressColumns.TABLE_NAME + "." + AddressColumns.CONTACT_ID + "=" + AddressColumns.PREFIX_CONTACT + "." + ContactColumns._ID;
                }
                res.orderBy = AddressColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_CONTACT:
            case URI_TYPE_CONTACT_ID:
                res.table = ContactColumns.TABLE_NAME;
                res.idColumn = ContactColumns._ID;
                res.tablesWithJoins = ContactColumns.TABLE_NAME;
                res.orderBy = ContactColumns.DEFAULT_ORDER;
                break;

            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }

        switch (matchedId) {
            case URI_TYPE_ADDRESS_ID:
            case URI_TYPE_CONTACT_ID:
                id = uri.getLastPathSegment();
        }
        if (id != null) {
            if (selection != null) {
                res.selection = res.table + "." + res.idColumn + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = res.table + "." + res.idColumn + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }
}
