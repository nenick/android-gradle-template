[Back to Index](../index.md)

# Testing Strategies

## Unit Tests

Check that your code work technically in isolation like you expected it.
Your have the choice between different unit tests variants.

**Android Unit Tests** (Historical this was the first existing possibility for unit tests)

This is the most realistic testing by deploy and run your code on device/emulator.
Is also the slowest approach because of the device/emulator deployment time.

**Robolectric Unit Tests** (Historical this was the second existing possibility for unit tests)

You can run tests at JVM but have access functional android code. You will have less issues with
android code compared to pure unit tests and faster then deploy an apk on device/emulator.

**Pure Java Unit Tests** (since android gradle plugin 1.1.0)

The fastest and most known style of unit testing,

**Its your choice**

I prefer a mix of pure unit tests and robolectric supported unit tests. Whenever possible write
unit test without robolectric this give you the fastest development cycle (TDD).

## Component Tests

Check that units work together in combination with database, network, android services, business logic, views, etc...

Some guys say Robolectric is just for unit testing but you can also do your main integration tests.
Test execution is up to 20x faster than on device/emulator with espresso.
But not all android features are supported or must first be configured to your needs.

## Integration Tests

At least check that your app does run on device.
Again you can choose between different approaches, see <http://testdroid.com/tech/top-5-android-testing-frameworks-with-examples>.
One concept may be to navigate through each page and feature and take a screenshot.
After each runs you can compare the layout and style to look same the effort. This can be improved with automatic image compare.


My favourite is Espresso because it is the tool with most stable and has fastest test execution.

---

[Back to Index](../index.md)