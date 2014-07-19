package com.example.provider;

import com.example.database.AgendaDatabase;

import com.example.database.table.*;

import android.provider.BaseColumns;
import android.text.TextUtils;
import android.content.ContentUris;
import android.database.sqlite.SQLiteQueryBuilder;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class AgendaProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.provider";

    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    public static final Uri AGENDA_CONTENT_URI = Uri.withAppendedPath(AgendaProvider.AUTHORITY_URI, AgendaContent.CONTENT_PATH);

    public static final Uri CALENDAR_ITEM_CONTENT_URI = Uri.withAppendedPath(AgendaProvider.AUTHORITY_URI, CalendarItemContent.CONTENT_PATH);

    public static final Uri AGENDA_ITEM_GROUP_CONTENT_URI = Uri.withAppendedPath(AgendaProvider.AUTHORITY_URI, AgendaItemGroupContent.CONTENT_PATH);

    public static final Uri AGENDA_ITEM_CONTENT_URI = Uri.withAppendedPath(AgendaProvider.AUTHORITY_URI, AgendaItemContent.CONTENT_PATH);

    public static final Uri AGENDA_JOIN_CALENDAR_ITEM_CONTENT_URI = Uri.withAppendedPath(AgendaProvider.AUTHORITY_URI, AgendaJoinCalendarItemContent.CONTENT_PATH);
    public static final Uri AGENDA_ITEM_GROUP_JOIN_AGENDA_ITEM_CONTENT_URI = Uri.withAppendedPath(AgendaProvider.AUTHORITY_URI, AgendaItemGroupJoinAgendaItemContent.CONTENT_PATH);

    private static final UriMatcher URI_MATCHER;
    private AgendaDatabase mDatabase;

    private static final int AGENDA_DIR = 0;
    private static final int AGENDA_ID = 1;

    private static final int CALENDAR_ITEM_DIR = 2;
    private static final int CALENDAR_ITEM_ID = 3;

    private static final int AGENDA_ITEM_GROUP_DIR = 4;
    private static final int AGENDA_ITEM_GROUP_ID = 5;

    private static final int AGENDA_ITEM_DIR = 6;
    private static final int AGENDA_ITEM_ID = 7;

    private static final int AGENDA_JOIN_CALENDAR_ITEM_DIR = 8;

        private static final int AGENDA_ITEM_GROUP_JOIN_AGENDA_ITEM_DIR = 9;

    
    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(AUTHORITY, AgendaContent.CONTENT_PATH, AGENDA_DIR);
        URI_MATCHER.addURI(AUTHORITY, AgendaContent.CONTENT_PATH + "/#",    AGENDA_ID);

        URI_MATCHER.addURI(AUTHORITY, CalendarItemContent.CONTENT_PATH, CALENDAR_ITEM_DIR);
        URI_MATCHER.addURI(AUTHORITY, CalendarItemContent.CONTENT_PATH + "/#",    CALENDAR_ITEM_ID);

        URI_MATCHER.addURI(AUTHORITY, AgendaItemGroupContent.CONTENT_PATH, AGENDA_ITEM_GROUP_DIR);
        URI_MATCHER.addURI(AUTHORITY, AgendaItemGroupContent.CONTENT_PATH + "/#",    AGENDA_ITEM_GROUP_ID);

        URI_MATCHER.addURI(AUTHORITY, AgendaItemContent.CONTENT_PATH, AGENDA_ITEM_DIR);
        URI_MATCHER.addURI(AUTHORITY, AgendaItemContent.CONTENT_PATH + "/#",    AGENDA_ITEM_ID);

        URI_MATCHER.addURI(AUTHORITY, AgendaJoinCalendarItemContent.CONTENT_PATH, AGENDA_JOIN_CALENDAR_ITEM_DIR);
        URI_MATCHER.addURI(AUTHORITY, AgendaItemGroupJoinAgendaItemContent.CONTENT_PATH, AGENDA_ITEM_GROUP_JOIN_AGENDA_ITEM_DIR);
     }

    public static final class AgendaContent implements BaseColumns {
        public static final String CONTENT_PATH = "agenda";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.agenda_database.agenda";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.agenda_database.agenda";
    }

    public static final class CalendarItemContent implements BaseColumns {
        public static final String CONTENT_PATH = "calendaritem";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.agenda_database.calendaritem";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.agenda_database.calendaritem";
    }

    public static final class AgendaItemGroupContent implements BaseColumns {
        public static final String CONTENT_PATH = "agendaitemgroup";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.agenda_database.agendaitemgroup";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.agenda_database.agendaitemgroup";
    }

    public static final class AgendaItemContent implements BaseColumns {
        public static final String CONTENT_PATH = "agendaitem";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.agenda_database.agendaitem";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.agenda_database.agendaitem";
    }

    public static final class AgendaJoinCalendarItemContent implements BaseColumns {
        public static final String CONTENT_PATH = "agenda_join_calendar_item";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.agenda_database.agenda_join_calendar_item";
    }
    public static final class AgendaItemGroupJoinAgendaItemContent implements BaseColumns {
        public static final String CONTENT_PATH = "agenda_item_group_join_agenda_item";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.agenda_database.agenda_item_group_join_agenda_item";
    }

    @Override
    public final boolean onCreate() {
        mDatabase = new AgendaDatabase(getContext());
        return true;
    }

    @Override
    public final String getType(final Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case AGENDA_DIR:
                return AgendaContent.CONTENT_TYPE;
            case AGENDA_ID:
                return AgendaContent.CONTENT_ITEM_TYPE;

            case CALENDAR_ITEM_DIR:
                return CalendarItemContent.CONTENT_TYPE;
            case CALENDAR_ITEM_ID:
                return CalendarItemContent.CONTENT_ITEM_TYPE;

            case AGENDA_ITEM_GROUP_DIR:
                return AgendaItemGroupContent.CONTENT_TYPE;
            case AGENDA_ITEM_GROUP_ID:
                return AgendaItemGroupContent.CONTENT_ITEM_TYPE;

            case AGENDA_ITEM_DIR:
                return AgendaItemContent.CONTENT_TYPE;
            case AGENDA_ITEM_ID:
                return AgendaItemContent.CONTENT_ITEM_TYPE;

            case AGENDA_JOIN_CALENDAR_ITEM_DIR:
                return AgendaJoinCalendarItemContent.CONTENT_TYPE;

            case AGENDA_ITEM_GROUP_JOIN_AGENDA_ITEM_DIR:
                return AgendaItemGroupJoinAgendaItemContent.CONTENT_TYPE;

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public final Cursor query(final Uri uri, String[] projection, final String selection, final String[] selectionArgs, final String sortOrder) {
        final SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        final SQLiteDatabase dbConnection = mDatabase.getReadableDatabase();

        switch (URI_MATCHER.match(uri)) {
            case AGENDA_ID:
                queryBuilder.appendWhere(AgendaTable._ID + "=" + uri.getLastPathSegment());
            case AGENDA_DIR:
                queryBuilder.setTables(AgendaTable.TABLE_NAME);
                break;

            case CALENDAR_ITEM_ID:
                queryBuilder.appendWhere(CalendarItemTable._ID + "=" + uri.getLastPathSegment());
            case CALENDAR_ITEM_DIR:
                queryBuilder.setTables(CalendarItemTable.TABLE_NAME);
                break;

            case AGENDA_ITEM_GROUP_ID:
                queryBuilder.appendWhere(AgendaItemGroupTable._ID + "=" + uri.getLastPathSegment());
            case AGENDA_ITEM_GROUP_DIR:
                queryBuilder.setTables(AgendaItemGroupTable.TABLE_NAME);
                break;

            case AGENDA_ITEM_ID:
                queryBuilder.appendWhere(AgendaItemTable._ID + "=" + uri.getLastPathSegment());
            case AGENDA_ITEM_DIR:
                queryBuilder.setTables(AgendaItemTable.TABLE_NAME);
                break;

            case AGENDA_JOIN_CALENDAR_ITEM_DIR:
                queryBuilder.setTables(AgendaTable.TABLE_NAME + " JOIN " + CalendarItemTable.TABLE_NAME + " ON (" + AgendaTable.TABLE_NAME + "." + AgendaTable._ID + "=" + CalendarItemTable.TABLE_NAME + "." + CalendarItemTable.AGENDA_ID + ")");

                projection = new String[] {
                    //add left table columns
                    AgendaTable.TABLE_NAME + "._id AS " + AgendaTable.TABLE_NAME + "__id",
                    AgendaTable.TABLE_NAME + "." + AgendaTable.NAME + " AS " + AgendaTable.TABLE_NAME + "_" + AgendaTable.NAME,
                    AgendaTable.TABLE_NAME + "." + AgendaTable.DATE + " AS " + AgendaTable.TABLE_NAME + "_" + AgendaTable.DATE,
                    AgendaTable.TABLE_NAME + "." + AgendaTable.PERSON_CONDUCTING + " AS " + AgendaTable.TABLE_NAME + "_" + AgendaTable.PERSON_CONDUCTING,
                    CalendarItemTable.TABLE_NAME + "._id AS " + CalendarItemTable.TABLE_NAME + "__id",
                    CalendarItemTable.TABLE_NAME + "." + CalendarItemTable.TITLE + " AS " + CalendarItemTable.TABLE_NAME + "_" + CalendarItemTable.TITLE,
                    CalendarItemTable.TABLE_NAME + "." + CalendarItemTable.DATE + " AS " + CalendarItemTable.TABLE_NAME + "_" + CalendarItemTable.DATE,
                    CalendarItemTable.TABLE_NAME + "." + CalendarItemTable.TIME + " AS " + CalendarItemTable.TABLE_NAME + "_" + CalendarItemTable.TIME,
                    CalendarItemTable.TABLE_NAME + "." + CalendarItemTable.LOCATION + " AS " + CalendarItemTable.TABLE_NAME + "_" + CalendarItemTable.LOCATION,
                    CalendarItemTable.TABLE_NAME + "." + CalendarItemTable.NOTES + " AS " + CalendarItemTable.TABLE_NAME + "_" + CalendarItemTable.NOTES,
                };
                break;
            case AGENDA_ITEM_GROUP_JOIN_AGENDA_ITEM_DIR:
                queryBuilder.setTables(AgendaItemGroupTable.TABLE_NAME + " JOIN " + AgendaItemTable.TABLE_NAME + " ON (" + AgendaItemGroupTable.TABLE_NAME + "." + AgendaItemGroupTable._ID + "=" + AgendaItemTable.TABLE_NAME + "." + AgendaItemTable.AGENDA_ITEM_GROUP_ID + ")");

                projection = new String[] {
                    //add left table columns
                    AgendaItemGroupTable.TABLE_NAME + "._id AS " + AgendaItemGroupTable.TABLE_NAME + "__id",
                    AgendaItemGroupTable.TABLE_NAME + "." + AgendaItemGroupTable.TITLE + " AS " + AgendaItemGroupTable.TABLE_NAME + "_" + AgendaItemGroupTable.TITLE,
                    AgendaItemGroupTable.TABLE_NAME + "." + AgendaItemGroupTable.DESCRIPTION + " AS " + AgendaItemGroupTable.TABLE_NAME + "_" + AgendaItemGroupTable.DESCRIPTION,
                    AgendaItemTable.TABLE_NAME + "._id AS " + AgendaItemTable.TABLE_NAME + "__id",
                    AgendaItemTable.TABLE_NAME + "." + AgendaItemTable.TITLE + " AS " + AgendaItemTable.TABLE_NAME + "_" + AgendaItemTable.TITLE,
                    AgendaItemTable.TABLE_NAME + "." + AgendaItemTable.COMMENT + " AS " + AgendaItemTable.TABLE_NAME + "_" + AgendaItemTable.COMMENT,
                };
                break;
            default :
                throw new IllegalArgumentException("Unsupported URI:" + uri);
        }

        Cursor cursor = queryBuilder.query(dbConnection, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;

    }

    @Override
    public final Uri insert(final Uri uri, final ContentValues values) {
        final SQLiteDatabase dbConnection = mDatabase.getWritableDatabase();

        try {
            dbConnection.beginTransaction();

            switch (URI_MATCHER.match(uri)) {
                case AGENDA_DIR:
                case AGENDA_ID:
                    final long agendaId = dbConnection.insertOrThrow(AgendaTable.TABLE_NAME, null, values);
                    final Uri newAgendaUri = ContentUris.withAppendedId(AGENDA_CONTENT_URI, agendaId);
                    getContext().getContentResolver().notifyChange(newAgendaUri, null);
                    getContext().getContentResolver().notifyChange(AGENDA_JOIN_CALENDAR_ITEM_CONTENT_URI, null);

                    dbConnection.setTransactionSuccessful();
                    return newAgendaUri;
                case CALENDAR_ITEM_DIR:
                case CALENDAR_ITEM_ID:
                    final long calendar_itemId = dbConnection.insertOrThrow(CalendarItemTable.TABLE_NAME, null, values);
                    final Uri newCalendarItemUri = ContentUris.withAppendedId(CALENDAR_ITEM_CONTENT_URI, calendar_itemId);
                    getContext().getContentResolver().notifyChange(newCalendarItemUri, null);
                    getContext().getContentResolver().notifyChange(AGENDA_JOIN_CALENDAR_ITEM_CONTENT_URI, null);

                    dbConnection.setTransactionSuccessful();
                    return newCalendarItemUri;
                case AGENDA_ITEM_GROUP_DIR:
                case AGENDA_ITEM_GROUP_ID:
                    final long agenda_item_groupId = dbConnection.insertOrThrow(AgendaItemGroupTable.TABLE_NAME, null, values);
                    final Uri newAgendaItemGroupUri = ContentUris.withAppendedId(AGENDA_ITEM_GROUP_CONTENT_URI, agenda_item_groupId);
                    getContext().getContentResolver().notifyChange(newAgendaItemGroupUri, null);
                    getContext().getContentResolver().notifyChange(AGENDA_ITEM_GROUP_JOIN_AGENDA_ITEM_CONTENT_URI, null);

                    dbConnection.setTransactionSuccessful();
                    return newAgendaItemGroupUri;
                case AGENDA_ITEM_DIR:
                case AGENDA_ITEM_ID:
                    final long agenda_itemId = dbConnection.insertOrThrow(AgendaItemTable.TABLE_NAME, null, values);
                    final Uri newAgendaItemUri = ContentUris.withAppendedId(AGENDA_ITEM_CONTENT_URI, agenda_itemId);
                    getContext().getContentResolver().notifyChange(newAgendaItemUri, null);
                    getContext().getContentResolver().notifyChange(AGENDA_ITEM_GROUP_JOIN_AGENDA_ITEM_CONTENT_URI, null);

                    dbConnection.setTransactionSuccessful();
                    return newAgendaItemUri;
                default :
                    throw new IllegalArgumentException("Unsupported URI:" + uri);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConnection.endTransaction();
        }

        return null;
    }

    @Override
    public final int update(final Uri uri, final ContentValues values, final String selection, final String[] selectionArgs) {
        final SQLiteDatabase dbConnection = mDatabase.getWritableDatabase();
        int updateCount = 0;
        List<Uri> joinUris = new ArrayList<Uri>();

        try {
            dbConnection.beginTransaction();

            switch (URI_MATCHER.match(uri)) {
                case AGENDA_DIR:
                    updateCount = dbConnection.update(AgendaTable.TABLE_NAME, values, selection, selectionArgs);

                    joinUris.add(AGENDA_JOIN_CALENDAR_ITEM_CONTENT_URI);

                    dbConnection.setTransactionSuccessful();
                    break;
                case AGENDA_ID:
                   final long agendaId = ContentUris.parseId(uri);
                   updateCount = dbConnection.update(AgendaTable.TABLE_NAME, values,
                       AgendaTable._ID + "=" + agendaId + (TextUtils.isEmpty(selection) ? "" : " AND (" + selection + ")"), selectionArgs);

                   joinUris.add(AGENDA_JOIN_CALENDAR_ITEM_CONTENT_URI);

                   dbConnection.setTransactionSuccessful();
                   break;

                case CALENDAR_ITEM_DIR:
                    updateCount = dbConnection.update(CalendarItemTable.TABLE_NAME, values, selection, selectionArgs);

                    joinUris.add(AGENDA_JOIN_CALENDAR_ITEM_CONTENT_URI);

                    dbConnection.setTransactionSuccessful();
                    break;
                case CALENDAR_ITEM_ID:
                   final long calendar_itemId = ContentUris.parseId(uri);
                   updateCount = dbConnection.update(CalendarItemTable.TABLE_NAME, values,
                       CalendarItemTable._ID + "=" + calendar_itemId + (TextUtils.isEmpty(selection) ? "" : " AND (" + selection + ")"), selectionArgs);

                   joinUris.add(AGENDA_JOIN_CALENDAR_ITEM_CONTENT_URI);

                   dbConnection.setTransactionSuccessful();
                   break;

                case AGENDA_ITEM_GROUP_DIR:
                    updateCount = dbConnection.update(AgendaItemGroupTable.TABLE_NAME, values, selection, selectionArgs);

                    joinUris.add(AGENDA_ITEM_GROUP_JOIN_AGENDA_ITEM_CONTENT_URI);

                    dbConnection.setTransactionSuccessful();
                    break;
                case AGENDA_ITEM_GROUP_ID:
                   final long agenda_item_groupId = ContentUris.parseId(uri);
                   updateCount = dbConnection.update(AgendaItemGroupTable.TABLE_NAME, values,
                       AgendaItemGroupTable._ID + "=" + agenda_item_groupId + (TextUtils.isEmpty(selection) ? "" : " AND (" + selection + ")"), selectionArgs);

                   joinUris.add(AGENDA_ITEM_GROUP_JOIN_AGENDA_ITEM_CONTENT_URI);

                   dbConnection.setTransactionSuccessful();
                   break;

                case AGENDA_ITEM_DIR:
                    updateCount = dbConnection.update(AgendaItemTable.TABLE_NAME, values, selection, selectionArgs);

                    joinUris.add(AGENDA_ITEM_GROUP_JOIN_AGENDA_ITEM_CONTENT_URI);

                    dbConnection.setTransactionSuccessful();
                    break;
                case AGENDA_ITEM_ID:
                   final long agenda_itemId = ContentUris.parseId(uri);
                   updateCount = dbConnection.update(AgendaItemTable.TABLE_NAME, values,
                       AgendaItemTable._ID + "=" + agenda_itemId + (TextUtils.isEmpty(selection) ? "" : " AND (" + selection + ")"), selectionArgs);

                   joinUris.add(AGENDA_ITEM_GROUP_JOIN_AGENDA_ITEM_CONTENT_URI);

                   dbConnection.setTransactionSuccessful();
                   break;

                default :
                    throw new IllegalArgumentException("Unsupported URI:" + uri);
            }
        } finally {
            dbConnection.endTransaction();
        }

        if (updateCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);

            for (Uri joinUri : joinUris) {
                getContext().getContentResolver().notifyChange(joinUri, null);
            }
        }

        return updateCount;

    }

    @Override
    public final int delete(final Uri uri, final String selection, final String[] selectionArgs) {
        final SQLiteDatabase dbConnection = mDatabase.getWritableDatabase();
        int deleteCount = 0;
        List<Uri> joinUris = new ArrayList<Uri>();

        try {
            dbConnection.beginTransaction();

            switch (URI_MATCHER.match(uri)) {
                case AGENDA_DIR:
                    deleteCount = dbConnection.delete(AgendaTable.TABLE_NAME, selection, selectionArgs);

                    joinUris.add(AGENDA_JOIN_CALENDAR_ITEM_CONTENT_URI);

                    dbConnection.setTransactionSuccessful();
                    break;
                case AGENDA_ID:
                    deleteCount = dbConnection.delete(AgendaTable.TABLE_NAME, AgendaTable.WHERE_ID_EQUALS, new String[] { uri.getLastPathSegment() });

                    joinUris.add(AGENDA_JOIN_CALENDAR_ITEM_CONTENT_URI);

                    dbConnection.setTransactionSuccessful();
                    break;

                case CALENDAR_ITEM_DIR:
                    deleteCount = dbConnection.delete(CalendarItemTable.TABLE_NAME, selection, selectionArgs);

                    joinUris.add(AGENDA_JOIN_CALENDAR_ITEM_CONTENT_URI);

                    dbConnection.setTransactionSuccessful();
                    break;
                case CALENDAR_ITEM_ID:
                    deleteCount = dbConnection.delete(CalendarItemTable.TABLE_NAME, CalendarItemTable.WHERE_ID_EQUALS, new String[] { uri.getLastPathSegment() });

                    joinUris.add(AGENDA_JOIN_CALENDAR_ITEM_CONTENT_URI);

                    dbConnection.setTransactionSuccessful();
                    break;

                case AGENDA_ITEM_GROUP_DIR:
                    deleteCount = dbConnection.delete(AgendaItemGroupTable.TABLE_NAME, selection, selectionArgs);

                    joinUris.add(AGENDA_ITEM_GROUP_JOIN_AGENDA_ITEM_CONTENT_URI);

                    dbConnection.setTransactionSuccessful();
                    break;
                case AGENDA_ITEM_GROUP_ID:
                    deleteCount = dbConnection.delete(AgendaItemGroupTable.TABLE_NAME, AgendaItemGroupTable.WHERE_ID_EQUALS, new String[] { uri.getLastPathSegment() });

                    joinUris.add(AGENDA_ITEM_GROUP_JOIN_AGENDA_ITEM_CONTENT_URI);

                    dbConnection.setTransactionSuccessful();
                    break;

                case AGENDA_ITEM_DIR:
                    deleteCount = dbConnection.delete(AgendaItemTable.TABLE_NAME, selection, selectionArgs);

                    joinUris.add(AGENDA_ITEM_GROUP_JOIN_AGENDA_ITEM_CONTENT_URI);

                    dbConnection.setTransactionSuccessful();
                    break;
                case AGENDA_ITEM_ID:
                    deleteCount = dbConnection.delete(AgendaItemTable.TABLE_NAME, AgendaItemTable.WHERE_ID_EQUALS, new String[] { uri.getLastPathSegment() });

                    joinUris.add(AGENDA_ITEM_GROUP_JOIN_AGENDA_ITEM_CONTENT_URI);

                    dbConnection.setTransactionSuccessful();
                    break;

                default :
                    throw new IllegalArgumentException("Unsupported URI:" + uri);
            }
        } finally {
            dbConnection.endTransaction();
        }

        if (deleteCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);

            for (Uri joinUri : joinUris) {
                getContext().getContentResolver().notifyChange(joinUri, null);
            }
        }

        return deleteCount;
    }
}