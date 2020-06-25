#!/usr/bin/env bash

# Install AVD files
(echo "y" | $ANDROID_HOME/tools/bin/sdkmanager --install 'system-images;android-29;google_apis;x86' && touch installed) &