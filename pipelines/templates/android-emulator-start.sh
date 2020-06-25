#!/usr/bin/env bash

while [[ ! -f installed ]]; do sleep 1; done;

(
    $ANDROID_HOME/emulator/emulator -list-avds

    echo "Starting emulator"

    # Start emulator in background
    nohup $ANDROID_HOME/emulator/emulator -avd android-v29 -no-snapshot > /dev/null 2>&1
) &