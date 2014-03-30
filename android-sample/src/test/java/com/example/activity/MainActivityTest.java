package com.example.activity;

import com.example.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;

import static com.example.test.support.assertions.AndroidShadowAssertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Robolectric.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    private ActivityController<MainActivity> activityController;
    private MainActivity activity;
    private ShadowActivity activityShadow;

    @Before
    public void setUp() {
        activityController = Robolectric.buildActivity(MainActivity.class);
        activity = activityController.get();
        activityShadow = shadowOf(activity);
    }
    @Test
    public void testShouldUseCorrectLayout() throws Exception {
        activityController.create();
        assertThat(activityShadow).hasContentView(R.id.activity_main);
    }
}
