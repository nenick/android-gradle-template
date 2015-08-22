package com.example.project.espresso;

import android.support.test.espresso.action.ViewActions;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class EspButton<P> {

    private int resourceId;

    public EspButton(int resourceId) {
        this.resourceId = resourceId;
    }

    public P click() {
        onView(withId(resourceId)).perform(ViewActions.click());
        return null;
    }
}
