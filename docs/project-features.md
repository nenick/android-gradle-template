# Project Features

* Model-View-ViewModel (MVVM) architecture template
  * ViewModel (from architecture components) to avoid configuration change issues
  * LiveData (from architecture components) to avoid view state issues
  * (TODO) HowTo: create new view
* Single activity architecture template
  * Navigation (from architecture components) for proper navigation handling
  * Combine Fragments for on large screens
  * (TODO) HowTo: replace activity result
  * (TODO) HowTo: different navigation graphs
* Coroutines instead of RX or LiveData from ViewModel to data logic.
* AndroidAnnotations reduce some ugly boilerplate
* Dependency injection with Koin
* Clean architecture structure
  * View -> ViewModel -> UseCase -> Repository -> local/network Data
* Local storing with Room
  * Fast and device independent tests on JVM with Robolectric
  * (TODO) Prepared scheme migration process
  * (TODO ??) Pure java library
* REST communication with Retrofit
  * Fast and device independent tests on JVM
  * (TODO ??) Pure java library
  * (TODO) HowTo: enable build in caching
* Separated app instrumentation tests
* Testing with Espresso
  * Waits automatically until all coroutine tasks are finished to stabilize execution
  * (TODO) Reset application state between test runs
  * (TODO) Less test complexity with Espresso Macchiato
  * (TODO) Permissions interceptor to emulate different grants
  * (TODO) Reduce common issues with headless emulator testing
* (TODO) Server Mocking for testing and independent developing! (runs on android environment)
* (TODO) Full sample: load data through network, cache it at local database, populate list view with stored data
* Coverage report
  * Separated reports for each target at each module
  * Merge reports over all targets and modules
* (TODO) Logging concept
  * (TODO) Configurable for interesting parts (e.g. Network calls, Database operations, Application execution, ...)
  * (TODO) HowTo: use configure the logging
* Preconfigured for CircleCi and Codacy
  * Workflow for parallelizing
  * Run instrumentation tests with Firebase TestLab
  * Merged Coverage Report
  * Static code analysis
  * (TODO) use project files for quality tools settings
* Dynamic property loading