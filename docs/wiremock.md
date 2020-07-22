# WireMock for independent testing

## Use Cases

#### Start feature development without waiting for the backend implementation.
Simply create a draft for the behaviour of requests and responses and implement against it. 
This is most time enough to start with the feature development. 
The final backend api may differ from the draft but that adjustments shouldn't be difficult with clean code.  

#### Develop and tryout features fast.
Receive an instant api response while manuel testing your implementation. 

#### Configure the backend response behaviour.
Every situation you need, needs just to be configured instead of cumbersome steps to reach a specific situation.
This is very helpful for testing.

#### Presentation when the backend isn't ready or not stable enough.
Simply configure the required behaviour and impress your audience without shame because something does not work as expected.

## Features

* Run app against real backend server.
* Run app against device installed mock server.
* Run app against pc installed mock server.
* Run tests (jvm & instrumented) against mock server with configurable behaviour.

Also
* Easy to delete these mock server features.
* Automatic and manuel test feature is independent from usage in the project.

## Links

* [First approach to run WireMock on Android](https://handstandsam.com/2016/01/30/running-wiremock-on-android/) ([GitHub Repo](https://github.com/handstandsam/AndroidHttpMockingExamples))
* [Solution for the JKS/BKS issue on Android](https://medium.com/walmartlabs/android-testing-with-mocking-fcec5b9f71c2) ([GitHub Repo](https://github.com/abhagupta/AndroidHttpMockingExamples))
* [WireMock](http://wiremock.org/) ([GitHub Repo](https://github.com/tomakehurst/wiremock))
    * [Stubbing](http://wiremock.org/docs/stubbing/)
    * [Verifying](http://wiremock.org/docs/verifying/)
    * [Request Matching](http://wiremock.org/docs/request-matching/)
    * [Simulating Faults](http://wiremock.org/docs/simulating-faults/)