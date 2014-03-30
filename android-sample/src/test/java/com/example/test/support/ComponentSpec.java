package com.example.test.support;

import android.app.Activity;

import com.example.activity.MainActivity;

import org.junit.Before;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

public abstract class ComponentSpec<A extends Activity> {

    public A activity;
    public ActivityController<A> activityController;
    private Class<A> clazz;

    public ComponentSpec(Class<A> clazz) {
        this.clazz = clazz;
    }

    protected void startActivity() {
        activityController.create().visible().start().resume();
    }

    @Before
    public void setUpComponentTest() {
        activityController = Robolectric.buildActivity(clazz);
        activity = activityController.get();
    }

}
