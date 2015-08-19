#!/bin/bash

#
# Change easy the base package to your needs
#

function printUsage {
    echo ""
    echo "Failed: Start script from the project root folder with target package name."
    echo "> sh scripts/src/main/resources/rename-packages.sh \"my.target.name\" "
    echo ""
}

initial_base_package="com.example.project"

if [ -z "$1" ]; then
    echo "Error: Started without target base package."
    printUsage
    exit 1
fi

if !([ -d app ]); then
    echo "Error: Not started from project root because folder app not found."
    printUsage
    exit 1
fi

# rename folders

# change files content