#!/bin/bash

# ========================================================
# Update README Versions Script
# ========================================================
# This script automatically extracts the latest library versions
# from the gradle/libs.versions.toml file and updates the
# badges in the README.md to reflect these versions.
#
# Run this script whenever you update dependencies to ensure
# the README stays in sync with your actual project versions.
# ========================================================

# Get the directory of the script
SCRIPT_DIR=$(dirname "$0")
cd "$SCRIPT_DIR" || exit 1

# Extract versions from the toml file
KOTLIN_VERSION=$(grep -m 1 "kotlin =" gradle/libs.versions.toml | sed 's/kotlin = "\(.*\)"/\1/')
DECOMPOSE_VERSION=$(grep -m 1 "decompose =" gradle/libs.versions.toml | sed 's/decompose = "\(.*\)"/\1/')
COMPOSE_BOM_VERSION=$(grep -m 1 "composeBom =" gradle/libs.versions.toml | sed 's/composeBom = "\(.*\)"/\1/')

echo "Found versions:"
echo "Kotlin: $KOTLIN_VERSION"
echo "Decompose: $DECOMPOSE_VERSION"
echo "Compose BOM: $COMPOSE_BOM_VERSION"

# Update README.md
sed -i.bak -E "s/kotlin-[0-9]+\.[0-9]+\.[0-9]+-blue/kotlin-$KOTLIN_VERSION-blue/" README.md
sed -i.bak -E "s/Decompose-[0-9]+\.[0-9]+\.[0-9]+-purple/Decompose-$DECOMPOSE_VERSION-purple/" README.md
sed -i.bak -E "s/Jetpack_Compose-[^-]+-green/Jetpack_Compose-$COMPOSE_BOM_VERSION-green/" README.md

# Clean up backup file
rm README.md.bak

echo "README.md has been updated with the latest versions!" 