#!/usr/bin/env bash
# Utility that allows the encoding of string same way as default Grain
# password encoder does.
set -e
set -x

javac _scripts/Encode.java
java -cp _scripts Encode $@
