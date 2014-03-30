package com.example.test.support;

import android.app.Activity;

import org.junit.Before;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

public abstract class ComponentSpec<A extends Activity> {

    public A activity;
    public ActivityController<A> activityController;

    public abstract Class<A> getBaseActivityClass();

    @Before
    public void setUpComponentTest() {
        Class<A> clazz = getBaseActivityClass();
        activityController = Robolectric.buildActivity(clazz);
        activity = activityController.get();
    }

    protected void startActivity() {
        activityController.create().visible().start().resume();
    }
}
