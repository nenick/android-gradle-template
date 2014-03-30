package com.example.component.tests;

import com.example.activity.MainActivity;
import com.example.component.tests.pages.StartPage;
import com.example.test.support.ComponentSpec;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class MainActivityCTest extends ComponentSpec<MainActivity> {

    StartPage startPage = new StartPage(this);

    public MainActivityCTest() {
        super(MainActivity.class);
    }

    @Before
    public void setUp() {
        startActivity();
    }

    @Test
    public void testSomething() throws Exception {
        startPage.checkTextViewHasText("Hello Espresso!");
    }

}
