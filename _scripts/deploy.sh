#!/usr/bin/env bash

# Exit on error
set -e

# Build
mvn clean package

# Copy to server
scp target/cinema-1.0.jar root@viking.local:

# Restart server
ssh root@viking.local "systemctl restart cinema"
