package com.example.specs;

import android.test.suitebuilder.annotation.LargeTest;

import com.example.main.MainActivity_;
import com.example.test.support.EspressoSpec;
import com.example.test.support.pages.StartPage;

@LargeTest
public class ExampleAppSpec extends EspressoSpec<MainActivity_> {

    StartPage startPage = new StartPage();

    public ExampleAppSpec() {
        super(MainActivity_.class);
    }

    public void testShouldShowText() {
        startPage.checkTextViewHasText("Hello Espresso!");
    }
}
