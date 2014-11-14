package com.example;

import com.example.test.support.UnitTestSpecification;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;

public class BuildConfigTest extends UnitTestSpecification {

    @Test
    public void shouldHaveCorrectConfiguration() {
        if ("debug".equals(BuildConfig.BUILD_TYPE)) {
            assertThat(BuildConfig.DEBUG).isTrue();
        } else if ("release".equals(BuildConfig.BUILD_TYPE)) {
            assertThat(BuildConfig.DEBUG).isFalse();
        } else {
            fail("build type configuration not tested or supported?");
        }
        new BuildConfig(); // dummy coverage, should be an interface or something else
    }
}
