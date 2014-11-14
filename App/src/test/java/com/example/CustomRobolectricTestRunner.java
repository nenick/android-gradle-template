package com.example;

import com.example.restexample.BookmarkAdapter;

import org.junit.runners.model.InitializationError;
import org.robolectric.AndroidManifest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.SdkConfig;
import org.robolectric.SdkEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.bytecode.ClassInfo;
import org.robolectric.bytecode.Setup;

import java.util.Properties;

/** Enable shadows for our own application classes. */
public class CustomRobolectricTestRunner extends RobolectricTestRunner {

    public CustomRobolectricTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected ClassLoader createRobolectricClassLoader(Setup setup, SdkConfig sdkConfig) {
        return super.createRobolectricClassLoader(new ExtraShadows(setup), sdkConfig);
    }

    protected SdkConfig pickSdkVersion(AndroidManifest appManifest, Config config) {
        Properties properties = new Properties();
        properties.setProperty("emulateSdk", "18");
        Config.Implementation implementation = new Config.Implementation(config, Config.Implementation.fromProperties(properties));
        return super.pickSdkVersion(appManifest, implementation);
    }

    protected void configureShadows(SdkEnvironment sdkEnvironment, Config config) {
        Properties properties = new Properties();
        properties.setProperty("shadows", CustomShadowApplication.class.getName());
        Config.Implementation implementation = new Config.Implementation(config, Config.Implementation.fromProperties(properties));
        super.configureShadows(sdkEnvironment, implementation);
    }

    class ExtraShadows extends Setup {
        private Setup setup;

        public ExtraShadows(Setup setup) {
            this.setup = setup;
        }

        public boolean shouldInstrument(ClassInfo classInfo) {
            boolean shoudInstrument = setup.shouldInstrument(classInfo);
            return shoudInstrument
                    || classInfo.getName().equals(BookmarkAdapter.class.getName());
        }
    }
}
