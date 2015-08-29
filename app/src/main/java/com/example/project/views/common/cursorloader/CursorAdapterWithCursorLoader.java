package com.example.project.views.common.cursorloader;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;

import com.example.project.database.provider.contact.ContactCursor;
import com.example.project.views.common.mvp.BaseActivityPresenter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Simplify handling for basic cursor loader + cursor adapter combination.
 * <p/>
 * Always listen for changes on the data source and reload new content.
 * <p/>
 * Implement this class, add the Adapter to a AdapterView and call {@link #start()}.
 */
@EBean
public abstract class CursorAdapterWithCursorLoader {

    /**
     * Expecting the adapter where the swap cursor method will be called.
     */
    public abstract CursorAdapter getCursorAdapter();

    /**
     * Provide an unique identifier for the cursor loader.
     */
    public abstract int getLoaderId();

    /**
     * Will be called on background when the cursor should loaded
     */
    public abstract Cursor loadCursor();

    @RootContext
    protected BaseActivityPresenter context;

    public void start() {
        LoaderManager loaderManager = context.getSupportLoaderManager();

        Loader<ContactCursor> loader = loaderManager.getLoader(getLoaderId());
        if (loader != null && !loader.isReset()) {
            loaderManager.restartLoader(getLoaderId(), null, createLoaderCallback());
        } else {
            loaderManager.initLoader(getLoaderId(), null, createLoaderCallback());
        }
    }

    private LoaderManager.LoaderCallbacks<Cursor> createLoaderCallback() {
        return new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                return new CursorLoader(context) {

                    private ForceLoadContentObserver observer = new ForceLoadContentObserver();

                    @Override
                    public Cursor loadInBackground() {
                        Cursor cursor = loadCursor();
                        cursor.registerContentObserver(observer);
                        return cursor;
                    }
                };
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, final Cursor data) {
                getCursorAdapter().swapCursor(data);
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                getCursorAdapter().swapCursor(null);
            }
        };
    }
}
