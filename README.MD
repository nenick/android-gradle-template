# Android - Rapid Test Driven Development

Start fast without the time consuming project setup effort!

* (TODO) no complex dependency injection (simple to replace when the need of more complex exist)
* (TODO) ready for MVVM
* (TODO) ready for architecture components (ViewModel, LiveData)
* (TODO) ready for local database storing
* (TODO) ready for REST communication
* (TODO) prepared testing with plane unit tests on a wide range
* (TODO) prepared testing with robolectric for fast complex tests
* (TODO) prepared testing with espresso for most realistic
* (TODO OPT) prepared REST mocking (for testing and independent developing!)

## Notes

### Minimum version

Current we use **Android Version 21** which is enough for 85% of the users and gives some nice defaults.
Current nothing should really block you to select a lower minimum version.

--todo-- advantages of minimum versions

topic / api | ?? | 21 | 23
---|---|---|---
material design ||default style|default style
backups ||| automatically

### Change package and application name

--todo--

### Dependency Injection

Most times its enough to use **AndroidAnnotation** instead of more advantage tools like Dagger.
This library points in simplicity and developing speed.

### Unhandled topics

* Compile Warning: App is not indexable by Google Search; ...
* Compile Warning: On SDK version 23 and up, ... adding \`android:fullBackupContent\`


### Used libraries

* Kotlin Android Extensions https://kotlinlang.org/docs/tutorials/android-plugin.html
* AndroidAnnotations https://github.com/androidannotations/androidannotations/wiki