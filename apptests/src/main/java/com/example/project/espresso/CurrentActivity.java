package com.example.project.espresso;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.collect.Iterables;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;

public class CurrentActivity {

    @SuppressWarnings("unchecked")
    public static <A extends Activity> A get() {
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        final Activity[] activity = new Activity[1];
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                java.util.Collection<Activity> activities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                activity[0] = Iterables.getOnlyElement(activities);
            }
        });
        return (A) activity[0];
    }
}
