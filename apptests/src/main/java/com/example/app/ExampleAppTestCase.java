package com.example.app;

import android.app.Activity;
import android.support.test.espresso.Espresso;

import org.junit.Before;

import de.nenick.espressomacchiato.testbase.EspressoTestCase;

public abstract class ExampleAppTestCase<A extends Activity> extends EspressoTestCase<A> {

    @Before
    public void setupBase() {
        Espresso.registerIdlingResources(ExampleAppIdlingCounter.instance());
    }
}
