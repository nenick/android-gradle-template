package com.example.test.support;

import org.junit.runner.RunWith;
import org.robolectric.shadows.ShadowLog;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;

@RunWith(CustomRobolectricTestRunner.class)
public abstract class UnitTestSpecification {

    public UnitTestSpecification() {
        // found no other way to set LogManager configuration by property file
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("logging.properties"));
            ShadowLog.stream = System.out; // current ShadowLog ignore log levels
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
