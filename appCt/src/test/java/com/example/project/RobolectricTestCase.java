package com.example.project;

import android.content.Context;
import android.database.ShadowContentObservable;

import com.example.project.BuildConfig;
import com.example.project.database.provider.ExampleSQLiteOpenHelper;
import com.example.project.robolectric.CostomRobolectricTestRunner;
import com.example.project.robolectric.ShadowBackgroundExecutor;
import com.github.tomakehurst.wiremock.client.WireMock;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;

@RunWith(CostomRobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, shadows = {ShadowContentObservable.class, ShadowBackgroundExecutor.class})
public abstract class RobolectricTestCase {

    protected Context context;

    @Before
    public void roboSetup() {
        context = RuntimeEnvironment.application;
        WireMock.configureFor(context.getString(R.string.const_wiremock_ip), 1337);
        WireMock.resetToDefault();
    }

    @After
    public void finishRobolectricTest() {
        resetSingleton(ExampleSQLiteOpenHelper.class, "sInstance");
    }

    private void resetSingleton(Class clazz, String fieldName) {
        Field instance;
        try {
            instance = clazz.getDeclaredField(fieldName);
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}