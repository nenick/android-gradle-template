package com.example.test.support;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

public abstract class EspressoSpec <T extends Activity> extends ActivityInstrumentationTestCase2<T> {

    public EspressoSpec(Class<T> activityClass) {
        super(activityClass);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity(); // Espresso will not launch our activity for us, we must launch it via getActivity().
    }
}
