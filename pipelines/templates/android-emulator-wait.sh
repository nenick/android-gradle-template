#!/usr/bin/env bash

$ANDROID_HOME/platform-tools/adb wait-for-device shell 'while [[ -z $(getprop sys.boot_completed | tr -d '\r') ]]; do sleep 1; done; input keyevent 82'

$ANDROID_HOME/platform-tools/adb devices

# I call it magic time, give it the emulator
# and he will be lucky until the end of his short life time
sleep 20

echo "Emulator started"