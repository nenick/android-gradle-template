package com.example.test.support.shadows;

import android.app.Application;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.shadows.ShadowApplication;

import java.io.File;

/**
 * Just relocate the database file to a hard defined position.
 */
@Implements(Application.class)
public class CustomShadowApplication extends ShadowApplication {

}
