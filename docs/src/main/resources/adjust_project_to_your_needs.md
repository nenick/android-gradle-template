[Back to Index](index.md)

# Adjust the project to your needs

### Remove database support

* clean up the app/build.gradle file from stuff which is added for [database example](database.md)
* delete the app/src/main/java/com/example/project/database package
* now delete or adjust other code with compile errors

### Remove network support

* clean up the app/build.gradle file from stuff which is added for [network example](network.md)
* delete the app/src/main/java/com/example/project/database package
* now delete or adjust other code with compile errors

### Replace AndroidAnnotation with Dagger and ButterKnife

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

[Back to Index](index.md)