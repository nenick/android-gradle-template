# Android - Rapid Test Driven Development

Start fast without the time consuming project setup effort!

* Model-View-ViewModel (MVVM) architecture template
 * ViewModel (from architecture components) to avoid configuration change issues
 * LiveData (from architecture components) to avoid view state issues
 * (TODO) HowTo: create new view
* Single activity architecture template
 * Navigation (from architecture components) for proper navigation handling
 * (TODO) Combine Fragments for on large screens
 * (TODO) HowTo: replace activity result
 * (TODO) HowTo: different navigation graphs
* AndroidAnnotations reduce some ugly boilerplate
* Dependency injection with Koin
* Clean architecture structure
 * View -> ViewModel -> UseCase -> Repository -> local/network Data
* Local storing with Room
 * Fast and device independent tests on JVM with Robolectric
 * (TODO ??) Pure java library
* REST communication with Retrofit
 * Fast and device independent tests on JVM
 * (TODO ??) Pure java library
 * (TODO) HowTo: enable build in caching
* Separated app instrumentation tests
* Testing with Espresso
 * Waits automatically until all coroutine tasks are finished to stabilize execution
 * (TODO) Reset application state between test runs
 * (TODO) Write less how, more what you want to do
 * (TODO) Permissions interceptor to emulate different grants
* (TODO) Server Mocking for testing and independent developing! (runs on android environment)
* (TODO) Full sample: load data through network, cache it at local database, populate list view with stored data
* (TODO) Coverage report
 * (TODO) Separated reports for each target inside single modules
 * (TODO) Merged reports over all targets and modules
* (TODO) Logging concept
 * (TODO) Configurable for interesting parts (e.g. Network calls, Database operations, Application execution, ...)
 * (TODO) HowTo: use configure the logging

## Notes

### Setup

#### Activate experimental navigation

(TODO)
```
File > Settings (Android Studio > Preferences on Mac),
select the Experimental category in the left pane,
check Enable Navigation Editor, and then restart Android Studio.
```

### Single activity architecture

(TODO)
The official recommended way to implement apps with navigation component.

### Minimum version

(TODO)
Current we use **Android Version 21** which is enough for 85% of the users and gives some nice defaults.
Current nothing should really block you to select a lower minimum version.

--todo-- advantages of minimum versions

topic / api | ?? | 18 | 21 | 23
---|:-:|:-:|:-:|:-:
UiAutomator (tests) || available | available | available
material design ||| default style | default style
backups |||| automatically

### Dependency Injection

(TODO)
Most times its enough to use **AndroidAnnotation** instead of more advantage tools like Dagger.
This library points in simplicity and developing speed.

When necessary, this should be easy replaceable by a more advantage tool.

### Unhandled topics

(TODO)
* Compile Warning: App is not indexable by Google Search; ...
* Compile Warning: On SDK version 23 and up, ... adding \`android:fullBackupContent\`


### Added libraries


(TODO)
* Kotlin Android Extensions https://kotlinlang.org/docs/tutorials/android-plugin.html
* AndroidAnnotations https://github.com/androidannotations/androidannotations/wiki

*-- TODO: list all libraries --*

## Work with this template

### Configure your needs

(TODO)
* remove data-network module when not necessary
* remove data-local module when not necessary
* change package and application name
 * TODO: write bash script for it

### Add new view

(TODO)
* create new Fragment class enhanced with AndroidAnnotation
* create related ViewModel class
* tip: now you can start designing with static mock data before implementing a Repository or any UseCases

### Switch start view

(TODO)
* open src/main/res/navigation/nav_graph_main.xml and switch to design view
* click "Add destination" and choose the generated enhanced class (*Fragment_)
* hit "Set Start Destination"

### Espresso tests

(TODO)
Basically you can write them in every module with each kind of abstraction.
For database and network operations its rare that you will need a real device for testing.
Most of the issues will already show up when testing on pure JVM. More independent and faster execution.

For full application integration tests I recommend to use a separated test module.
And unit tests done with Espresso should stay inside the related modules.
It's kind of separation by concern and lets you run the tests separately without extra configurations.

### Project structure overview

(TODO)
* app
 * androidTest - Mainly for isolated unit tests when device/emulator is necessary
 * test - Unit tests when possible
 * main - For views, business logic and connecting the data options
* app-tests
 * main - Full app integration tests with mocked REST API

### Run application and tests

(TODO)
#### Command Line

#### Android Studio

##### app-tests

You must select a package and run the instrumentation tests, otherwise AndroidStudio think they are pure unit tests.
Current it's not possible to just select the module or java folder an start run tests.

## For further details

(TODO)
* Navigation Component https://developer.android.com/topic/libraries/architecture/navigation/navigation-implementing
* Good to know about coroutines https://medium.com/capital-one-tech/kotlin-coroutines-on-android-things-i-wish-i-knew-at-the-beginning-c2f0b1f16cff
* JaCoCo release notes https://www.jacoco.org/jacoco/trunk/doc/changes.html