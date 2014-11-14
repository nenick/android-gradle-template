package com.example.test.support;

import android.app.Activity;

import com.example.CustomRobolectricTestRunner;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;

import static org.robolectric.Robolectric.shadowOf;

@RunWith(CustomRobolectricTestRunner.class)
public abstract class ComponentTestSpecification<A extends Activity> {

    public A activity;
    public ShadowActivity activityShadow;
    public ActivityController<A> activityController;
    private Class<A> clazz;

    public ComponentTestSpecification(Class<A> clazz) {
        this.clazz = clazz;
    }

    protected void startActivity() {
        activityController.create().visible().start().resume();
    }

    @Before
    public void setUpComponentTest() {
        activityController = Robolectric.buildActivity(clazz);
        activity = activityController.get();
        activityShadow = shadowOf(activity);
    }

}
