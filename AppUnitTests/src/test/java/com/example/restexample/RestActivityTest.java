package com.example.restexample;

import com.example.test.support.UnitTestSpecification;

import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

@Config(shadows = {BookmarkAdapterShadow.class})
public class RestActivityTest extends UnitTestSpecification {

    private RestActivity_ view = Robolectric.buildActivity(RestActivity_.class).create().get();

    @Test
    public void dummy() {

    }
}
