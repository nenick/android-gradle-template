#!/usr/bin/env bash

#===============================================================================
# Read script input

APP_PACKAGE="$1"
APP_NAME="$2"

#===============================================================================
# Some helper functions

function renameFolder {
    ORIGIN=$1
    TARGET=$2

    find . -type d -iname $ORIGIN -depth -exec bash -c "
        git mv \"\$1\" \"\${1//$ORIGIN/$TARGET}\"
    " -- {} \;
}

function renamePackage {
    ORIGIN=$1
    TARGET=$2

    for FILE in `grep -rl --exclude-dir={.git,.gradle,.idea,_setup-tools} "$ORIGIN" .`
    do
        sed -i -e "s/$ORIGIN/$TARGET/g" $FILE
    done
}

function renameApp() {
    renamePackage "$1" "$2"
}

#===============================================================================

# clean all build folders to avoid unecessary work and conflicts
./gradlew clean
rm -rf buildSrc/build

APP_PACKAGE_STRUCTURE=(${APP_PACKAGE//./ })

renameFolder "de" ${APP_PACKAGE_STRUCTURE[0]}
renameFolder "nenick" ${APP_PACKAGE_STRUCTURE[1]}
renameFolder "template" ${APP_PACKAGE_STRUCTURE[2]}

renamePackage "de.nenick.template" $APP_PACKAGE
renamePackage "de.nenick" "${APP_PACKAGE_STRUCTURE[0]}.${APP_PACKAGE_STRUCTURE[1]}"

renameApp "Nenick Template" "$APP_NAME"

#===============================================================================
# Check that project changes don't throw errors

./gradlew assembleDebug

if [ $? -eq 0 ]
then
    echo "
    ==============================
        Identity change done.
        - Package: $APP_PACKAGE
        - Name: $APP_NAME
    ==============================
    "
else
    echo "
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        Identity change failed,
        see output for details.
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    "
fi

