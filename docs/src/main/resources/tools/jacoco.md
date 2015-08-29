# Jacoco unit tests

simple unit tests in app module and robolectric supported

apply plugin: "jacoco"

add custom jacocoTestReport task (see app/build-jacoco-test-report.gradle)

result at: app/build/reports/jacoco/index.html

# jacoco component tests

apply plugin: "jacoco"

to get a report adjust some properties (see appCt/build-jacoco-test-report.gradle)

result at: appCt/build/reports/jacoco/test/html/com.example.project/index.html

# jacoco integration tests

enable them and use connectedCheck instead of connectedAndroidTest

    buildTypes {
        debug {
            testCoverageEnabled = true
        }
    }

results at: result at: appCt/build/reports/jacoco/test/html/com.example.project/index.html

# combined coverage report

see

# Jacoco Version

looks like some version does no work with android. The version "0.7.0.201403182114" looks good.