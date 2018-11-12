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
* Clean architecture structure
 * View -> ViewModel -> UseCase -> Repository -> local/network Data
* Local storing with Room
 * Fast and device independent tests on JVM with Robolectric
* REST communication
 * Fast and device independent tests on JVM
 * (TODO) Pure java library
* (TODO) Template testing with plane unit tests on a wide range
* (TODO) Template testing with espresso for most realistic
* (TODO) Server Mocking for testing and independent developing! (runs on android environment)
* (TODO) Full sample: load data through network, cache it at local database, populate list view with stored data

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

topic / api | ?? | 18 | 21 | 23
---|:-:|:-:|:-:|:-:
UiAutomator (tests) || available | available | available
material design ||| default style | default style
backups |||| automatically

### Change package and application name

*-- TODO: write bash script for it --*

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

*-- TODO: list all libraries --*