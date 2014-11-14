package com.example.test.support;

import android.content.Context;

import org.junit.Before;
import org.robolectric.Robolectric;

import java.io.File;

public abstract class DatabaseSpecification extends UnitTestSpecification {

    public Context context;

    @Before
    public void initContext() {
        context = Robolectric.application;
        resetDatabase();
    }

    public void resetDatabase() {
        File dbFile = context.getDatabasePath(null);
        if (dbFile.exists()) {
            context.deleteDatabase(dbFile.getAbsolutePath());
        }
    }
}
