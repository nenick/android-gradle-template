package com.example.component.tests;

import com.example.activity.MainActivity;
import com.example.component.tests.pages.StartPage;
import com.example.test.support.ComponentTestSpecification;

import org.junit.Before;
import org.junit.Test;

public class MainActivitySpec extends ComponentTestSpecification<MainActivity> {

    StartPage startPage = new StartPage(this);

    public MainActivitySpec() {
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
