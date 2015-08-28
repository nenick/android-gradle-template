

module under test need to publish variants: publishNonDefault true

Initial need dummy AndroidManifest.xml at src/main

base build.gradle

    apply plugin: 'com.android.test'

    android {
        compileSdkVersion 22
        buildToolsVersion "22.0.1"

        defaultConfig {
            minSdkVersion 8
        }

        targetProjectPath ':app'
        targetVariant 'debug'
    }

### APK not build issue

workaround is to call https://code.google.com/p/android/issues/detail?id=180689
