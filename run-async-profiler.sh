#!/bin/bash

if [ $# -eq 0 ]; then
  echo "Usage: $0 /path/to/libasyncProfiler.dylib"
  exit 1
fi

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ASYNC_PROFILER_LIB=$1

if [ ! -f "$ASYNC_PROFILER_LIB" ]; then
  echo "Error: Async Profiler library not found at '$ASYNC_PROFILER_LIB'."
  exit 1
fi

export ASYNC_PROFILER_PATH="$ASYNC_PROFILER_LIB"
OUTPUT_DIR="$SCRIPT_DIR/outputs/ap"

if [ ! -d "$OUTPUT_DIR" ]; then
  mkdir -p "$OUTPUT_DIR"
fi

JAVA_SOURCE_DIR="$SCRIPT_DIR/ProfilingTestApp"
javac "$JAVA_SOURCE_DIR"/*.java

java -agentpath:$ASYNC_PROFILER_PATH=start,event=itimer,lock=1ms,alloc=1m,file=$OUTPUT_DIR/%t-%h.jfr,loop=60s,jfrsync=default -cp "$JAVA_SOURCE_DIR" ProfilingTestApp
