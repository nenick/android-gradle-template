#!/usr/bin/env bash

# Install AVD files
(
    echo "y" | $ANDROID_HOME/tools/bin/sdkmanager --install 'system-images;android-29;google_apis;x86'

    # Create emulator
    echo "no" | $ANDROID_HOME/tools/bin/avdmanager create avd -n android-v29 -k 'system-images;android-29;google_apis;x86' --force

    touch installed
) &