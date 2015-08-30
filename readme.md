# Android - Rapid Test Driven Development

Mainly this template shows which tools can be used to generate most of the boilerplate code and
provides examples how to test different aspects of an android application.
Also show shows a clean architecture example with MVP and package by feature approach.

* Generate view access and listener by [Android Annotations](docs/src/main/resources/tools/androidannotations.md)
* Generate REST Client by [Android Annotations](docs/src/main/resources/tools/androidannotations.md)
* Generate Json Mapping by [JsonSchema2Pojo](docs/src/main/resources/tools/jsonschema2pojo.md)
* Generate Database handling by [ContentProvider Generator](docs/src/main/resources/tools/android_contentprovider_generator.md)
* Simplify Background and UiThread actions with [Android Annotations](docs/src/main/resources/tools/androidannotations.md)
* Testing your app on Device/Emulator with [Espresso](docs/src/main/resources/tools/espresso.md)
* Testing your app at JVM with [Robolectric](docs/src/main/resources/tools/robolectric.md) or simple Unit Tests

**Wishes, improvements and discussions about the stuff here are welcome.**

For more documentation see [Project Documentations](docs/src/main/resources/index.md)

## Latest tested basic tool versions

* Android Studio 1.3.2
* Gradle Build Tools 1.3.0
* Gradle 2.6
* Android v22

## Getting Started

Start an emulator or connect with device.
Then on commandline navigate into the project root and execute from there `tools/src/main/resources/test-all-with-coverage.sh`
and check that all works. (Script works on Mac and Linux but Windows user must use `tools\src\main\resources\test-all-with-coverage.bat`)

With Android Studio import the project and start developing. For more details see [Getting Started](docs/src/main/resources/getting_started.md)

