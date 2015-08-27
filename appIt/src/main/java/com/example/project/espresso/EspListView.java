package com.example.project.espresso;

import android.view.View;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class EspListView {
    private int resourceId;

    public EspListView(int resourceId) {
        this.resourceId = resourceId;
    }

    public int count() {
        final int[] counts = new int[1];
        onData(withId(resourceId)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;
                counts[0] = listView.getCount();
                return true;
            }

            @Override
            public void describeTo(Description description) {
            }
        }));
        return counts[0];
    }
}
