#!/bin/bash

# Exit on any error
set -e

echo "Starting build for Gramify Services..."

# Build the entire project
mvn clean install -DskipTests

echo "Build completed successfully!"
