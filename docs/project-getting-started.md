# Getting Started

Just clone and import the project into Android Studio and press run (the app or some test suites).

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


### Location of reports

(TODO)
unit test
*   execution build/reports/tests/testDebugUnitTest/index.html
*   coverage build/reports/jacoco/jacocoUnitTestReport/index.html

instrumentation tests reports
*   execution build/reports/androidTests/connected/index.html
*   coverage build/reports/jacoco/jacocoInstrumentationTestReport/index.html

### Project structure overview

(TODO)
*   app
    *   androidTest - Mainly for isolated unit tests when device/emulator is necessary
    *   test - Unit tests when possible
    *   main - For views, business logic and connecting the data options
*   app-tests
    *   main - Full app integration tests with mocked REST API

### Run application and tests

(TODO)
#### Command Line

#### Android Studio

##### app-tests

You must select a package and run the instrumentation tests, otherwise AndroidStudio think they are pure unit tests.
Current it's not possible to just select the module or java folder an start run tests.

## Work with this template

### Configure your needs

(TODO)
*   remove data-network module when not necessary
*   remove data-local module when not necessary
*   change package and application name
    *   TODO: write bash script for it

### Add new view

(TODO)
*   create new Fragment class enhanced with AndroidAnnotation
*   create related ViewModel class
*   tip: now you can start designing with static mock data before implementing a Repository or any UseCases

### Switch start view

(TODO)
*   open src/main/res/navigation/nav_graph_main.xml and switch to design view
*   click "Add destination" and choose the generated enhanced class (*Fragment_)
*   hit "Set Start Destination"

### Espresso tests

(TODO)
Basically you can write them in every module with each kind of abstraction.
For database and network operations its rare that you will need a real device for testing.
Most of the issues will already show up when testing on pure JVM. More independent and faster execution.

For full application integration tests I recommend to use a separated test module.
And unit tests done with Espresso should stay inside the related modules.
It's kind of separation by concern and lets you run the tests separately without extra configurations.

