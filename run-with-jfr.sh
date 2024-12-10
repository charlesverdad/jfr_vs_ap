#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
OUTPUT_DIR="$SCRIPT_DIR/OUTPUTS"
JFR_DIR="$OUTPUT_DIR/jfr"
JAVA_SOURCE_DIR="$SCRIPT_DIR/ProfilingTestApp"

# Ensure the JFR output directory exists
mkdir -p "$JFR_DIR"

# Compile Java source files in place
javac "$JAVA_SOURCE_DIR"/*.java

# Run the Java application with JFR settings
java -cp "$JAVA_SOURCE_DIR" \
    -XX:StartFlightRecording=name=ProfileRecording,filename=$JFR_DIR/recording.jfr,settings=profile,maxage=60s,dumponexit=true \
    -XX:FlightRecorderOptions=repository=$JFR_DIR,stackdepth=2048 \
    ProfilingTestApp
