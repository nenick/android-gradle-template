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

Check that units work together in combination of database, network, android services, business logic, views.

For this kind of tests I love robolectric.
Test execution is around 20x faster than on device/emulator.
But not all android features are supported or must first be configured to your needs.

## Integration Tests

At least check that your app does run on device.
Again you can choose between different approaches, see <http://testdroid.com/tech/top-5-android-testing-frameworks-with-examples>.

My favourite is Espresso because it is the tool with most stable and has fastest test execution.