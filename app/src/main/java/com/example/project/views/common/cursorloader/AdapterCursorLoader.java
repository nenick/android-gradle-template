package com.example.project.views.common.cursorloader;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;

import com.example.project.database.contact.ContactCursor;
import com.example.project.views.common.mvp.BaseActivityPresenter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean
public abstract class AdapterCursorLoader {

    @RootContext
    protected BaseActivityPresenter context;

    public abstract CursorAdapter getCursorAdapter();

    public abstract int getLoaderId();

    public abstract Cursor loadCursor();

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
                    @Override
                    public Cursor loadInBackground() {
                        return loadCursor();
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
