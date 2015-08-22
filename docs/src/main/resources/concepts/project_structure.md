# Project structure

### app - Application

contains the app with views, business logic, etc ...
Checks the details of each unit.
A unit is the smallest meaningful part of a app.


### appCt - Component Tests

Tests which will check the integration of different units.
Most high level logic is proved here.

### appDt - Device Tests

Runs the app on device/emulator to check that all work in a real environment.