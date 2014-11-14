package com.example.test.support;

import com.example.CustomRobolectricTestRunner;

import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.logging.LogManager;

@RunWith(CustomRobolectricTestRunner.class)
public abstract class UnitTestSpecification {

    public UnitTestSpecification() {
        // found no other way to set LogManager configuration by property file
        try {
            LogManager.getLogManager().readConfiguration(getClass().getResourceAsStream("/logging.properties"));
            ShadowLog.stream = System.out; // current ShadowLog ignore log levels
        } catch (Throwable e) {
            //throw new RuntimeException(e);
        }
    }
}
