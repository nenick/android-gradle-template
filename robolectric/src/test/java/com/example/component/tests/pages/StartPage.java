package com.example.component.tests.pages;

import android.widget.TextView;

import com.example.R;
import com.example.test.support.ComponentTestSpecification;
import com.example.test.support.assertions.AndroidShadowAssertions;

import static org.fest.assertions.api.ANDROID.assertThat;

public class StartPage {

    private ComponentTestSpecification componentTestSpecification;

    public StartPage(ComponentTestSpecification componentTestSpecification) {
        this.componentTestSpecification = componentTestSpecification;
    }

    public void checkTextViewHasText(String expected) {
        TextView textView = (TextView) componentTestSpecification.activity.findViewById(R.id.text);
        assertThat(textView).containsText(expected);
    }

    public void checkLayoutIs(int expected) {
        AndroidShadowAssertions.assertThat(componentTestSpecification.activityShadow).hasContentView(expected);
    }
}
