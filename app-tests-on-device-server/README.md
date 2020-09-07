# On Device Server Feature

This module exists only to prove that the on device server feature is working.

It could be extended when you use this feature often for demos and want to ensure specific
behaviours. Here you should really think about invest and return. That mock server stubs are
working is tested in different places already. But the default mock server setup has to be
tested here.

For Android Studio you have to select the the `onDeviceServer` buildType for the application
or test will fail with missing classes. On commandline just call `connectedAndroidTest`.