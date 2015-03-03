#!/bin/bash

mkdir temp
cd temp

  #git clone https://github.com/novoda/gradle-android-test-plugin.git
  git clone https://github.com/nenick/gradle-android-test-plugin.git

  cd gradle-android-test-plugin
  
    git checkout d9d77a2d4814a92c301b53dc7212e312acad9a2f

    cp ../../Scripts/novoda-deploy.gradle gradle-android-test-plugin/novoda-deploy.gradle
    echo "apply from: file('novoda-deploy.gradle')" >> gradle-android-test-plugin/build.gradle
    echo "rootProject.name = 'gradle-android-test-plugin-parent'" > settings.gradle
    echo "include ':gradle-android-test-plugin'" >> settings.gradle

    ./gradlew :gradle-android-test-plugin:install

  cd ..
cd ..
