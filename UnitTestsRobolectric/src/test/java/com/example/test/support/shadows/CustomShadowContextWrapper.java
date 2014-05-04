package com.example.test.support.shadows;

import android.content.Context;
import android.content.ContextWrapper;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.shadows.ShadowContext;
import org.robolectric.shadows.ShadowContextWrapper;

import java.io.File;

/** Just relocate the database file. */
@Implements(ContextWrapper.class)
public class CustomShadowContextWrapper extends ShadowContextWrapper {

    private static final String alternativeDatabasePath = "build/resources/unit-test.db";

    @Override
    @Implementation
    public File getDatabasePath(String name) {
        new RuntimeException(name).printStackTrace(System.err);
        return new File(alternativeDatabasePath);
    }
}
