package com.example.app;

import android.support.test.espresso.idling.CountingIdlingResource;

public class ExampleAppIdlingCounter {

    private static CountingIdlingResource counter;

    public static CountingIdlingResource instance() {
        if(counter == null) {
            counter = new CountingIdlingResource(ExampleAppIdlingCounter.class.getName());
        }
        return counter;
    }

    public static void increment() {
        if(BuildConfig.DEBUG) {
            instance().increment();
        }
    }

    public static void decrement() {
        if(BuildConfig.DEBUG) {
            instance().decrement();
        }
    }
}
