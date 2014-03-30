package com.example.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import com.example.R;
import com.example.test.support.pages.StartPage;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;

@LargeTest
public class ExampleAppSpec extends ActivityInstrumentationTestCase2<MainActivity> {

    StartPage startPage = new StartPage();

    @SuppressWarnings("deprecation")
    public ExampleAppSpec() {
        // This constructor was deprecated - but we want to support lower API levels.
        super("com.example.activity", MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        // Espresso will not launch our activity for us, we must launch it via getActivity().
        getActivity();
    }

    public void testShouldShowText() {
        startPage.checkTextViewHasText("Hello Espresso!");
    }
}
