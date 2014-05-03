#!/bin/bash

$(
    mkdir temp
    cd temp
    git clone https://github.com/nenick/android-gradle-template.git
    cd android-gradle-template
    ./gradlew install
)