package com.example.test.support.assertions;

import org.fest.assertions.api.AbstractAssert;
import org.robolectric.shadows.ShadowActivity;

import static org.fest.assertions.api.Assertions.assertThat;

public class ShadowActivityAssert extends AbstractAssert<ShadowActivityAssert, ShadowActivity> {

    public ShadowActivityAssert(ShadowActivity actual) {
        super(actual, ShadowActivityAssert.class);
    }

    public ShadowActivityAssert hasContentView(int resourceId) {
        assertThat(actual.getContentView().getId()).isEqualTo(resourceId);
        return this;
    }
}
