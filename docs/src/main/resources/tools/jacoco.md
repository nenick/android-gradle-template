[Back to Index](../index.md)

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

see projectRoot/build-jacoco-test-report.gradle

# Jacoco Version

looks like some version does no work with android. The version "0.7.0.201403182114" looks good.

# Replace AndroidAnnotation with Dagger and ButterKnife

* clean up the app/build.gradle file from stuff which is added for [AndroidAnnotations](tools/androidannotations.md)
* add dagger and butterknife to the app/build.gradle
* replace all annotations
* for jacoco code coverage you will need the following workaround to avoid issues with the generated $$ classes


    task jacocoReport(type: JacocoReport) {
        doFirst {
            applyDaggerWorkaround('app/build/intermediates/classes/')
        }
        // avoid some side effects through revert renaming
        doLast {
            revertDaggerWorkaround('app/build/intermediates/classes/')
        }
    }

    def applyDaggerWorkaround(String pathWithDaggerClasses) {
        def filePath = new File(pathWithDaggerClasses)
        if (filePath.exists()) {
            filePath.eachFileRecurse { file ->
                if (file.name.contains('$$')) {
                    file.renameTo(file.path.replace('$$', '$'))
                }
            }
        }
    }

    def revertDaggerWorkaround(String pathWithDaggerClasses) {
        def filePath = new File(pathWithDaggerClasses)
        if (filePath.exists()) {
            filePath.eachFileRecurse { file ->
                if (file.name.contains('$ModuleAdapter')) {
                    file.renameTo(file.path.replace('$ModuleAdapter', '$$ModuleAdapter'))
                }
            }
        }
    }

---

[Back to Index](../index.md)
