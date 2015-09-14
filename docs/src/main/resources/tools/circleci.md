[Back to Index](../index.md)

# Circle CI

For my continues integration I prefer Circle CI because of nice separation of each build step and a way to collect build results.

## emulator

The emulator is not ready after it is started, it just need one minute more after the script  `circle-android wait-for-boot` report ready.
And its not guaranted that the lock screen is disabled `adb shell input keyevent 82`

## Max memory

preDex use mutiple dex instances which in cobnation fast exceed the 4G memory limit.

## collecting build artifacts

copy all what you like to the $CIRCLE_TEST_REPORTS location to see them later like the reason why a test has failed.

## proper test value

circle ci offers possibility to read and show the tests results on a nice screen when you provide
test result xml files into $CIRCLE_TEST_REPORTS/junit

---

[Back to Index](../index.md)