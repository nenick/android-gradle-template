# Android - Rapid Test Driven Development

Start fast without the time consuming project setup effort!

* Model-View-ViewModel (MVVM) architecture template
 * ViewModel (architecture components) to avoid configuration change issues
 * LiveData (architecture components) to avoid view state issues
* Single activity architecture template
 * Navigation (architecture components) for proper navigation handling
* AndroidAnnotations reduce boilerplate
 * Generate view event listener
 * Simplified dependency injection
* (TODO) Local database storing
* (TODO) REST communication
* (TODO) template testing with plane unit tests on a wide range
* (TODO) template testing with robolectric for fast complex tests
* (TODO) template testing with espresso for most realistic
* (TODO OPT) REST mocking (for testing and independent developing!)

## Notes

### Setup

#### Activate experimental navigation

```
File > Settings (Android Studio > Preferences on Mac),
select the Experimental category in the left pane,
check Enable Navigation Editor, and then restart Android Studio.
```

### Single activity architecture

The official recommended way to implement apps with navigation component.

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

When necessary, this should be easy replaceable by a more advantage tool.

### Unhandled topics

* Compile Warning: App is not indexable by Google Search; ...
* Compile Warning: On SDK version 23 and up, ... adding \`android:fullBackupContent\`


### Added libraries

* Kotlin Android Extensions https://kotlinlang.org/docs/tutorials/android-plugin.html
* AndroidAnnotations https://github.com/androidannotations/androidannotations/wiki