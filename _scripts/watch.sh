#!/usr/bin/env bash
# Utility that watches for changes in view layer files and copies them to the
# currently running application
set -x

find . -name '*.html' -or -name '*.css' -or -name '*.gtl' |
 entr cp -r src/main/resources/* target/classes/
