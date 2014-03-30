package com.example.test.support;

import android.app.Activity;

import com.example.activity.MainActivity;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

@Config(reportSdk = 18)
@RunWith(RobolectricTestRunner.class)
public abstract class ComponentTestSpecification<A extends Activity> {

    public A activity;
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
    }

}
