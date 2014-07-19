package com.example.test.support.assertions;

import org.fest.assertions.api.ANDROID;
import org.robolectric.shadows.ShadowActivity;

public class AndroidShadowAssertions extends ANDROID {

    public static ShadowActivityAssert assertThat(ShadowActivity actual) {
        return new ShadowActivityAssert(actual);
    }
}
