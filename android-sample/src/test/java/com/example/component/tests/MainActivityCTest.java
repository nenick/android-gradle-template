package com.example.component.tests;

import android.widget.TextView;

import com.example.R;
import com.example.activity.MainActivity;
import com.example.test.support.ComponentSpec;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.fest.assertions.api.ANDROID.assertThat;

@RunWith(RobolectricTestRunner.class)
public class MainActivityCTest extends ComponentSpec<MainActivity> {

    @Before
    public void setUp() {
        startActivity();
    }

    @Test
    public void testSomething() throws Exception {
        TextView textView = (TextView) activity.findViewById(R.id.text);
        assertThat(textView).containsText("Hello Espresso!");
    }

    @Override
    public Class<MainActivity> getBaseActivityClass() {
        return MainActivity.class;
    }
}
