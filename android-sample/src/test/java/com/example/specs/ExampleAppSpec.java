package com.example.specs;

import android.test.suitebuilder.annotation.LargeTest;

import com.example.activity.MainActivity;
import com.example.test.support.EspressoSpec;
import com.example.test.support.pages.StartPage;

@LargeTest
public class ExampleAppSpec extends EspressoSpec<MainActivity> {

    StartPage startPage = new StartPage();

    public ExampleAppSpec() {
        super(MainActivity.class);
    }

    public void testShouldShowText() {
        startPage.checkTextViewHasText("Hello Espresso!");
    }
}
