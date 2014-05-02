package com.example.test.support;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;

public abstract class EspressoSpec <T extends Activity> extends ActivityInstrumentationTestCase2<T> {

    public EspressoSpec(Class<T> activityClass) {
        super(activityClass);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        // sometimes tests failed on emulator, following approach should avoid it
        // http://stackoverflow.com/questions/22737476/false-positives-junit-framework-assertionfailederror-edittext-is-not-found
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
            }
        });

        // Espresso will not launch our activity for us, we must launch it via getActivity().
    }
}
