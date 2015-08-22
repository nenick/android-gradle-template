package com.example.project.espresso;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class EspTextEdit {

    int resourceId;

    public EspTextEdit(int resourceId) {
        this.resourceId = resourceId;
    }

    public void insert(String text) {
        onView(withId(resourceId)).perform(typeText(text));
    }
}
