package com.example.project.robolectric;

import com.example.project.BuildConfig;

import org.androidannotations.api.BackgroundExecutor;
import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.internal.SdkEnvironment;
import org.robolectric.internal.bytecode.InstrumentationConfiguration;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Adjust some path declarations to the module under test.
 * <p/>
 * Original robolectric would search at wrong locations when used inside separated test module.
 */
public class CostomRobolectricTestRunner extends RobolectricTestRunner {

    public CostomRobolectricTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
        String buildVariant = (BuildConfig.FLAVOR.isEmpty() ? "" : BuildConfig.FLAVOR + "/") + BuildConfig.BUILD_TYPE;
        String intermediatesPath = BuildConfig.MODULE_PATH + "/build//intermediates";

        System.setProperty("android.package", BuildConfig.APPLICATION_ID);
        System.setProperty("android.manifest", intermediatesPath + "/manifests/full/" + buildVariant + "/AndroidManifest.xml");
        System.setProperty("android.resources", intermediatesPath + "/res/merged/" + buildVariant);
        System.setProperty("android.assets", intermediatesPath + "/assets/" + buildVariant);
        System.setProperty("java.io.tmpdir", intermediatesPath + "/test-data");
        mkdir(intermediatesPath + "/test-data");
    }

    private void mkdir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            assertThat(file.mkdir()).isTrue();
        }
    }

    /**
     * Declare custom classes to be shadowed when shadow exist.
     */
    public InstrumentationConfiguration createClassLoaderConfig() {
        InstrumentationConfiguration.Builder builder = InstrumentationConfiguration.newBuilder();
        builder.addInstrumentedClass(BackgroundExecutor.class.getName());
        return builder.build();
    }
}
