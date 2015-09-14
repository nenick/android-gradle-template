[Back to Index](../index.md)

# Android ContentProvider Generator

Create tool for generating all necessary database files.

* Table classes with constants
* ContentProvider implementation
* CursorWrapper
* Hook for database updates
* and many more ...

## Info

* [Project](https://github.com/BoD/android-contentprovider-generator)

Current there is an important bug with joins and ids <https://github.com/BoD/android-contentprovider-generator/issues/86>.
To avoid this issue you can give a specific projection.

    private final String[] ALL_JOINED_COLUMNS = (String[]) ArrayUtils.addAll(
                            AddressColumns.ALL_COLUMNS,
                            ContactColumns.ALL_COLUMNS);

    // multi join example
    private final String[] ALL_JOINED_COLUMNS = (String[]) ArrayUtils.addAll(
                            ArrayUtils.addAll(
                                AddressColumns.ALL_COLUMNS,
                                ContactColumns.ALL_COLUMNS),
                            MoreColumns.ALL_COLUMNS);

The ArrayUtils comes with *compile 'commons-lang:commons-lang:2.6'*

## Add Android ContentProvider Generator to your project

Add the following snippet to your build.gradle file:

    // this block must be above the first plugin line
    plugins {
        id "de.undercouch.download" version "1.2"
    }

    // this block can be at the end of the build.gradle or in a separate file
    def dbSchemaPath = "src/main/json/database/schema"
    def dbClassesPath = "src/gen/java"
    android.sourceSets.main.java.srcDirs += dbClassesPath
    android.sourceSets.main.java.srcDirs += "src/main/json"

    task generateDatabaseFiles << {
        def generatorVersion = "1.9.2"

        # download android contentprovider generator
        if (!file("$buildDir/android_contentprovider_generator-${generatorVersion}-bundle.jar").exists()) {
            download {
                src "https://github.com/BoD/android-contentprovider-generator/releases/download/v${generatorVersion}/android_contentprovider_generator-${generatorVersion}-bundle.jar"
                dest buildDir
            }
        }

        # delete last generated files
        file(dbClassesPath).deleteDir()

        # execute the generator
        exec {
            executable "java"
            args = ["-jar", "$buildDir/android_contentprovider_generator-${generatorVersion}-bundle.jar", "-i", dbSchemaPath, "-o", dbClassesPath]
        }
    }

This snippet add the new task **generateDatabaseFiles** to you gradle tasks.
This new Task reads the schema files from $dbSchemaPath and write them to $dbClassesPath.

After the generation you will get a hint how to register your new database provider at AndroidManifest.xml

    <application>
        <provider
            android:name="com.example.project.database.provider.ExampleProvider"
            android:authorities="com.example.project.database.provider"
            android:exported="false" />
     </application>

### subclass for dependency injection

    @EBean
    public class ExampleDbProvider extends ExampleProvider {
    }

### Move SQLiteOpenHelper Callbacks

By default this class would not be overwritten but with some custom changes to the generation process it does.
Best is to move it to src/main/java instead of src/gen/java within the same package to keep custom callback implementations.
After each you must delete

---

[Back to Index](../index.md)
