package com.example.project.database.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.example.project.BuildConfig;
import com.example.project.database.provider.address.AddressColumns;
import com.example.project.database.provider.contact.ContactColumns;

public class ExampleSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = ExampleSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "example.db";
    private static final int DATABASE_VERSION = 1;
    private static ExampleSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final ExampleSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_ADDRESS = "CREATE TABLE IF NOT EXISTS "
            + AddressColumns.TABLE_NAME + " ( "
            + AddressColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AddressColumns.CONTACT_ID + " INTEGER NOT NULL, "
            + AddressColumns.STREET + " TEXT, "
            + AddressColumns.NUMBER + " TEXT, "
            + AddressColumns.CITY + " TEXT, "
            + AddressColumns.COUNTRY + " TEXT, "
            + AddressColumns.STATE + " TEXT, "
            + AddressColumns.POSTALCODE + " TEXT "
            + ", CONSTRAINT fk_contact_id FOREIGN KEY (" + AddressColumns.CONTACT_ID + ") REFERENCES contact (_id) ON DELETE CASCADE"
            + " );";

    public static final String SQL_CREATE_TABLE_CONTACT = "CREATE TABLE IF NOT EXISTS "
            + ContactColumns.TABLE_NAME + " ( "
            + ContactColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ContactColumns.FIRST_NAME + " TEXT, "
            + ContactColumns.LAST_NAME + " TEXT, "
            + ContactColumns.BIRTHDATE + " INTEGER "
            + ", CONSTRAINT first_or_last_name_must_be_given CHECK((first_name <> '' AND first_name IS NOT NULL) OR (last_name <> '' AND last_name IS NOT NULL)) ON CONFLICT FAIL"
            + " );";

    // @formatter:on

    public static ExampleSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static ExampleSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static ExampleSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new ExampleSQLiteOpenHelper(context);
    }

    private ExampleSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new ExampleSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static ExampleSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new ExampleSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private ExampleSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new ExampleSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_ADDRESS);
        db.execSQL(SQL_CREATE_TABLE_CONTACT);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
