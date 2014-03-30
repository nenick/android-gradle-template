package com.example.test.support.pages;

import com.example.R;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;

public class StartPage {

    public void checkTextViewHasText(String expected) {
       onView(withId(R.id.text)).check(matches(withText(expected)));
    }
}
