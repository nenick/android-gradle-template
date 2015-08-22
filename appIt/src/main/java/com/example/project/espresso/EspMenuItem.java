package com.example.project.espresso;

import android.support.test.espresso.action.ViewActions;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class EspMenuItem<P> {

    int resourceId;

    public EspMenuItem(int resourceId) {
        this.resourceId = resourceId;
    }

    public P click() {
        onView(withId(resourceId)).perform(ViewActions.click());
        return null;
    }
}
