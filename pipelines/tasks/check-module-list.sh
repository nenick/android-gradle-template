#!/usr/bin/env bash

MODULES_LISTED=$(echo $1 | sed $'s/ /\\\n/g')

MODULES_EXISTS=$(cat settings.gradle | grep "include " | sed s/"include ':"//g | sed s/\:/\\//g | sed s/\'//g)
#MODULES_EXISTS=$(echo -e "buildSrc\\n$MODULES_EXISTS")

echo ""
echo "== MODULES_EXISTS =="
echo "$MODULES_EXISTS"
echo ""
echo "== MODULES_LISTED =="
echo "$MODULES_LISTED"
echo ""

if [ "$MODULES_EXISTS" != "$MODULES_LISTED" ]; then
  >&2 echo "Error: listed modules does not match settings.gradle"
  exit 1
fi