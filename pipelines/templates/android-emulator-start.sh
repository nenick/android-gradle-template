#!/usr/bin/env bash

while [[ ! -f installed ]]; do sleep 1; done;

# Create emulator
echo "no" | $ANDROID_HOME/tools/bin/avdmanager create avd -n android-v29 -k 'system-images;android-29;google_apis;x86' --force

$ANDROID_HOME/emulator/emulator -list-avds

echo "Starting emulator"

# Start emulator in background
nohup $ANDROID_HOME/emulator/emulator -avd android-v29 -no-snapshot > /dev/null 2>&1 &