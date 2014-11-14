package com.example.component.tests;

import com.example.R;
import com.example.main.MainActivity_;
import com.example.component.tests.pages.StartPage;
import com.example.test.support.ComponentTestSpecification;

import org.junit.Before;
import org.junit.Test;

public class MainActivityTest extends ComponentTestSpecification<MainActivity_> {

    StartPage startPage = new StartPage(this);

    public MainActivityTest() {
        super(MainActivity_.class);
    }

    @Before
    public void setUp() {
        startActivity();
    }

    @Test
    public void testShouldUseCorrectLayout() throws Exception {
        startPage.checkLayoutIs(R.id.activity_main);
    }

    @Test
    public void testShouldShowText() throws Exception {
        startPage.checkTextViewHasText("Hello Espresso!");
    }

}
